package com.lynch.mac;

import com.lynch.LoRaServer;
import com.lynch.app.LightAppService;
import com.lynch.repository.DeviceRepo;
import com.lynch.service.LightService;
import com.lynch.util.*;
import com.lynch.util.aes.LoRaMacCrypto;
import com.lynch.util.base64.base64__;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;

import static com.lynch.domain.Light.WEB_CONTROL_YES;

public class OperateMacUnconfirmedDataDown implements OperateMacPkt {

    DeviceRepo deviceRepo = (DeviceRepo) SpringTool.getBean("deviceRepo");

    LightService lightService = (LightService) SpringTool.getBean("lightService");

    LightAppService lightAppService = (LightAppService) SpringTool.getBean("lightAppService");

    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringTool.getBean("stringRedisTemplate");


    @Override
    public MacPktForm MacParseData(byte[] data, String gatawayId, String syscontent) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 返回 MacUnconfirmedDataDownForm, 其中的 FRMPayload 已加密
     */
    @Override
    public MacPktForm MacConstructData(MacPktForm macpkt) {
        ArrayList<Byte> FoptList = new ArrayList<>();
        MacUnconfirmedDataUpForm macunconfirmeddataup = (MacUnconfirmedDataUpForm) macpkt;
        MacUnconfirmedDataDownForm macunconfirmeddatadown = new MacUnconfirmedDataDownForm();
        macunconfirmeddatadown.DevAddr = macunconfirmeddataup.DevAddr;
        String nodehex = base64__.BytetoHex(macunconfirmeddatadown.DevAddr);

        Integer downFcntInt = deviceRepo.findDownFcntByDeviceAddr(nodehex);
        Integer upFcntInt = deviceRepo.findUpFcntByDeviceAddr(nodehex);
        if (downFcntInt == upFcntInt) {
            return null;
        } else if (macunconfirmeddataup.Fopts.length != 0) {

            macunconfirmeddatadown.fctrl.ADR = 1;

            //RFU为0协议为Class A或C,1为Class B
            if (LoRaServer.classMod == ClassMod.Class_B)
                macunconfirmeddatadown.fctrl.RFU = 1;
            else {
                macunconfirmeddatadown.fctrl.RFU = 0;
            }

            macunconfirmeddatadown.fctrl.ACK = 0;
            macunconfirmeddatadown.fctrl.FPending = 0;
            //更新下行DownFcnt
            if ((downFcntInt == 0 && upFcntInt > 1) || (upFcntInt - downFcntInt > 1)) {
                deviceRepo.updateDownFcnt(upFcntInt, nodehex);
            } else {
                deviceRepo.updateDownFcnt(downFcntInt + 1, nodehex);
            }
            byte[] fcnt = ByteArrayandInt.intToByteArray(downFcntInt + 1);
            byte[] downFcnt = new byte[2];
            System.arraycopy(fcnt, 0, downFcnt, 0, 2);
            macunconfirmeddatadown.Fcnt = downFcnt;
            FoptList = MacCommandHandler.macHandler(macunconfirmeddataup.Fopts);
            //Foptslen
            macunconfirmeddatadown.fctrl.FOptslen = FoptList.size();
            byte[] DownFopt = new byte[FoptList.size()];
            //Fopts
            for (int i = 0; i < FoptList.size(); i++) {
                DownFopt[i] = FoptList.get(i);
            }
            macunconfirmeddatadown.Fopts = DownFopt;
            base64__.myprintHex(macunconfirmeddatadown.Fopts);
            FoptList.clear();
            macunconfirmeddatadown.Fport = macunconfirmeddataup.Fport;
            return macunconfirmeddatadown;


        } else {
            return null;
        }
    }

    @Override
    public MacPktForm MacConstructData(JSONObject message) {

        MacUnconfirmedDataDownForm macunconfirmeddatadown = new MacUnconfirmedDataDownForm();

        if (message != null) {
            int downCount = 0;
            byte[] frmPayload = new byte[6];
            String lightAddr = message.getString("nodeId");
            if (message.containsKey("nodeswitch")) {
                String lightSwitch = message.getString("nodeswitch");
//                String lightBrightness = message.getString("brightness");
//                String lightThreshold = message.getString("nodethreshold");
                System.out.println("前端请求灯状态或立即获取灯状态");
                //获取下行计数并更新

                String getDownCount = stringRedisTemplate.opsForValue().get(lightAddr + "-downCount");
                if (getDownCount == null || getDownCount.equals("256")) {
                    getDownCount = "0";
                    stringRedisTemplate.opsForValue().set(lightAddr + "-downCount", getDownCount);
                } else {
                    downCount = Integer.parseInt(getDownCount);
                }
                frmPayload[0] = (byte) Integer.parseInt(HextoString.convertStringToHex("l"), 16);
                frmPayload[1] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(6));
                frmPayload[2] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(downCount));
                //更新下行计数位
                stringRedisTemplate.opsForValue().set(lightAddr + "-downCount", String.valueOf(downCount + 1));
                frmPayload[3] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(80));
                frmPayload[4] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(0));
                frmPayload[5] = (byte) Integer.parseInt(HextoString.convertStringToHex(lightSwitch), 16);
                macunconfirmeddatadown.DevAddr = base64__.HextoByte(lightAddr);
                //持久化
                lightService.saveLight(lightAddr, lightSwitch, "0", "80", WEB_CONTROL_YES);
                if (DataModel.isSatisfyModel(frmPayload))
                    lightAppService.lightDataDown(frmPayload);
            } else {
                Integer hour = Integer.valueOf(message.getString("hour"));
                Integer minute = Integer.valueOf(message.getString("minute"));
                Integer second = Integer.valueOf(message.getString("second"));
                System.out.println("改变终端上行数据传输周期");
                //获取下行计数并更新
                String getDownCount = stringRedisTemplate.opsForValue().get(lightAddr + "-downCount");
                if (getDownCount == null || getDownCount.equals("256")) {
                    getDownCount = "0";
                    stringRedisTemplate.opsForValue().set(lightAddr + "-downCount", getDownCount);
                } else {
                    downCount = Integer.parseInt(getDownCount);
                }
                frmPayload[0] = (byte) Integer.parseInt(HextoString.convertStringToHex("r"), 16);
                frmPayload[1] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(6));
                frmPayload[2] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(downCount));
                //更新下行计数位
                stringRedisTemplate.opsForValue().set(lightAddr + "-downCount", String.valueOf(downCount + 1));
                frmPayload[3] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(hour));
                frmPayload[4] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(minute));
                frmPayload[5] = ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(second));
                macunconfirmeddatadown.DevAddr = base64__.HextoByte(lightAddr);
            }
            base64__.myprintHex(frmPayload);
            System.out.println();

            macunconfirmeddatadown.fctrl.ADR = 1;

            //RFU为0协议为Class A或C,1为Class B
            if (LoRaServer.classMod == ClassMod.Class_B)
                macunconfirmeddatadown.fctrl.RFU = 1;
            else {
                macunconfirmeddatadown.fctrl.RFU = 0;
            }

            macunconfirmeddatadown.fctrl.ACK = 0;
            macunconfirmeddatadown.fctrl.FPending = 0;
            //从redis中获取前端控制的fcnt
            byte[] fcnt = ByteArrayandInt.intToByteArray(downCount);
            byte[] downFcnt = new byte[2];
            System.arraycopy(fcnt, 0, downFcnt, 0, 2);
            macunconfirmeddatadown.Fcnt = downFcnt;


            macunconfirmeddatadown.Fport = new byte[]{0x03};
            byte[] appSKey = DatatypeConverter.parseHexBinary(stringRedisTemplate.opsForValue().get(lightAddr + "-AppSKey"));
            System.out.print("AppSKey:");
            base64__.myprintHex(appSKey);

            // 对 FRMPayload 进行加密
            macunconfirmeddatadown.FRMPayload = new byte[frmPayload.length];
            macunconfirmeddatadown.FRMPayload = LoRaMacCrypto.LoRaMacPayloadEncrypt(frmPayload, frmPayload.length, appSKey,
                    macunconfirmeddatadown.DevAddr, (byte) 0x01, fcnt); // TODO fcnt
            // 需要 4
            // 字节的，这里是 2
            // 字节的,
            // 加密秘钥也需要变动,
            // 应为
            // AppSKey
            //mac命令的处理
        }
        return macunconfirmeddatadown;

    }

}
