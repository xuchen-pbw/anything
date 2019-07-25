package com.lynch.mac;


import com.lynch.LoRaServer;
import com.lynch.app.LightAppService;
import com.lynch.domain.Device;
import com.lynch.repository.DeviceRepo;
import com.lynch.service.LightService;
import com.lynch.util.*;
import com.lynch.util.aes.LoRaMacCrypto;
import com.lynch.util.base64.base64__;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;

import static com.lynch.domain.Light.WEB_CONTROL_NO;

public class OperateMacConfirmedDataUp implements OperateMacPkt {

    DeviceRepo deviceRepo = (DeviceRepo) SpringTool.getBean("deviceRepo");

    LightService lightService = (LightService) SpringTool.getBean("lightService");

    LightAppService lightAppService = (LightAppService) SpringTool.getBean("lightAppService");

    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringTool.getBean("stringRedisTemplate");


    @Override
    public MacPktForm MacParseData(byte[] data, String gatawayId, String syscontent) {

        System.out.print("received from End-Node: " + DatetimePrint.timeprint() + " ");
        base64__.myprintHex(data);
        MacConfirmedDataUpForm macconfirmeddataup = new MacConfirmedDataUpForm();

        System.arraycopy(data, 1, macconfirmeddataup.DevAddr, 0, 4);
        System.out.print("devAddr:");
        base64__.myprintHex(macconfirmeddataup.DevAddr);

        String nodehex = base64__.BytetoHex(macconfirmeddataup.DevAddr);


        Device device = deviceRepo.findByDeviceAddr(nodehex);
        //注册到百亚云并持久化到本地与密钥存储
        if (device == null) {
            Device newDevice = new Device();
            newDevice.setDeviceAddr(nodehex);
            newDevice.setCreateDate(new Date());
            //终端地理位置映射
            HashMap<String, String> nodeLocation = NodeLocation.getnodeLocation();
            String deviceLati = nodeLocation.get("latitude");
            String deviceLong = nodeLocation.get("longtude");
            newDevice.setLatitude(deviceLati);
            newDevice.setLongitude(deviceLong);
            newDevice.setGateway(gatawayId);
            newDevice.setUpFcnt(0);
            newDevice.setDownFcnt(0);
            newDevice.setGatewayHost(stringRedisTemplate.opsForValue().get("gateway:" + gatawayId));

            deviceRepo.save(newDevice);
            //redis中初始化下行计数位
            stringRedisTemplate.opsForValue().set(nodehex + "-downCount", "0");
        }
        //更新网关地址
        deviceRepo.updateGatewayHost(stringRedisTemplate.opsForValue().get("gateway:" + gatawayId), nodehex);

        //密钥存储到redis
        stringRedisTemplate.opsForValue().set(nodehex + "-NwkSKey", ObjectToString.byteToStr(LoRaMacCrypto.APPKEY));
        stringRedisTemplate.opsForValue().set(nodehex + "-AppSKey", ObjectToString.byteToStr(LoRaMacCrypto.APPKEY));

        macconfirmeddataup.fctrl.setFctrl(data[5]);
        System.out.print("fctrl:");
        System.out.println(data[5]);
        //模式判断
        System.out.println("RFU"+macconfirmeddataup.fctrl.RFU);
        if (macconfirmeddataup.fctrl.RFU == 1) {
            LoRaServer.classMod = ClassMod.Class_B;
        } else if (macconfirmeddataup.fctrl.RFU == 0) {
            LoRaServer.classMod = ClassMod.Class_A;
        }

        System.arraycopy(data, 6, macconfirmeddataup.Fcnt, 0, 2);
        System.out.print("Fcnt:");
        base64__.myprintHex(macconfirmeddataup.Fcnt);

        int foptlen = macconfirmeddataup.fctrl.FOptslen;
        macconfirmeddataup.Fopts = new byte[foptlen];
        System.arraycopy(data, 8, macconfirmeddataup.Fopts, 0, foptlen);
        System.out.print("Fopts:");
        base64__.myprintHex(macconfirmeddataup.Fopts);

        System.arraycopy(data, 8 + foptlen, macconfirmeddataup.Fport, 0, 1);
        System.out.print("Fport:");
        base64__.myprintHex(macconfirmeddataup.Fport);
        int framelen;
        framelen = data.length - (13 + foptlen);
        macconfirmeddataup.FRMPayload = new byte[framelen];
        System.arraycopy(data, 8 + foptlen + 1, macconfirmeddataup.FRMPayload, 0, framelen);
        // TODO 测试 MIC
        // byte[] datain = Arrays.copyOfRange(data, 0, 8);
        // byte[] micComputed = LoRaMacCrypto.LoRaMacComputeMic(
        // datain, datain.length, LoRaMacCrypto.APPKEY,
        // macconfirmeddataup.DevAddr, (byte)0x00, macconfirmeddataup.Fcnt);
        // byte[] micReceived = new byte[4];
        // System.arraycopy(data, 8, micReceived, 0, 4);
        // if(!ObjectToString.byteToStr(micComputed).equals(ObjectToString.byteToStr(micReceived)))
        // return null;
        //fcnt
        byte[] fcntByte = new byte[4];
        fcntByte[2] = 0x00;
        fcntByte[3] = 0x00;
        System.arraycopy(macconfirmeddataup.Fcnt, 0, fcntByte, 0, 2);
        Integer fcnt = ByteArrayandInt.byteArrayToInt(fcntByte);
        deviceRepo.updateUpFcnt(fcnt, nodehex);
        //用于标志是否已发下行,如果已经相等证明已经回复
        Integer downFcnt = deviceRepo.findUpFcntByDeviceAddr(nodehex);
        if (fcnt != downFcnt && fcnt != 0) {
            deviceRepo.updateDownFcnt(fcnt - 1, nodehex);
        }
        // 对于 FRMPayload 要进行解密
        System.out.println(ObjectToString.byteToStrWith(macconfirmeddataup.getDevAddr()) + "-AppSKey");
        byte[] appSKey = DatatypeConverter.parseHexBinary(stringRedisTemplate.opsForValue().get(ObjectToString.byteToStrWith(macconfirmeddataup.getDevAddr()) + "-AppSKey"));
        macconfirmeddataup.FRMPayload = LoRaMacCrypto.LoRaMacPayloadDecrypt(macconfirmeddataup.FRMPayload, framelen,
                appSKey, macconfirmeddataup.DevAddr, (byte) 0x00, fcntByte);// TODO
//         fcnt需要4字节的，这里是 2 字节的, 加密秘钥也需要变动, 应为 AppSKey

        String nodeSwitch = null;
        String nodeBrightness = null;
        String nodeThreshold = null;

        System.out.print("FRMPayload:");
        base64__.myprintHex(macconfirmeddataup.FRMPayload);

        //满足数据模型
        if (DataModel.isSatisfyModel(macconfirmeddataup.FRMPayload)) {
            HashMap<String, String> lightStatus = lightAppService.lightUpHandler(nodehex, macconfirmeddataup.FRMPayload);
            nodeSwitch = lightStatus.get("nodeSwitch");
            nodeBrightness = lightStatus.get("nodeBrightness");
            nodeThreshold = lightStatus.get("nodeThreshold");
            //本地持久化
            lightService.saveLight(nodehex, nodeSwitch, nodeBrightness, nodeThreshold, WEB_CONTROL_NO);
        }


        // Timer timer = new Timer();
        // timer.schedule(new printpingoffset(), new Date(), 128000);
        if (LoRaServer.classMod == ClassMod.Class_B) {
            System.out.println("Class B模式");
            /*
             * 计算pingoffset
             * */
            //beacon receive
            CalPingoffset calPingoffset = new CalPingoffset();
            calPingoffset.showbeacontime();
            calPingoffset.calpingoffset(macconfirmeddataup.DevAddr);
        } else if (LoRaServer.classMod == ClassMod.Class_A) {
            System.out.println("Class A模式");
        }

//        String nodeid = base64__.BytetoHex(macconfirmeddataup.DevAddr);
//
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
//        String dateString = formatter.format(currentTime);
//
//        Device getDevice = deviceRepo.findByDeviceAddr(nodehex);
//        String nodelati = getDevice.getLatitude();
//        String nodelong = getDevice.getLongitude();
//
//        //用户数据
//        String userInfo = "{" +
//                "\"lati\":" + "\"" + nodelati + "\"," +
//                "\"long\":" + "\"" + nodelong + "\"," +
//                "\"alti\":" + "\"531\"," +
//                "\"lightswitch\":" + "\"" + nodeSwitch + "\"," +
//                "\"brightness\":" + "\"" + nodeBrightness + "\"," +
//                "\"threshold\":" + "\"" + nodeThreshold + "\"," +
//                "\"gatewayID\":" + "\"" + gatawayId + "\"" + "}";
//


        return macconfirmeddataup;
    }


    /**
     * 返回 MacUnconfirmedDataDownForm, 其中的 FRMPayload 已加密
     */
    @Override
    public MacPktForm MacConstructData(MacPktForm macpkt) {
        return null;
    }

    @Override
    public MacPktForm MacConstructData(JSONObject message) {
        return null;
    }

}
