package com.lynch.mac;



import com.lynch.domain.Device;
import com.lynch.repository.DeviceRepo;
import com.lynch.util.ObjectToString;
import com.lynch.util.SpringTool;
import com.lynch.util.aes.LoRaMacCrypto;
import com.lynch.util.base64.base64__;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;

public class OperateMacJoinAccept implements OperateMacPkt {


    DeviceRepo deviceRepo = (DeviceRepo) SpringTool.getBean("deviceRepo");

    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringTool.getBean("stringRedisTemplate");
    /**
     * 解析 mac 层数据
     */
    @Override
    public MacPktForm MacParseData(byte[] data, String gatawayId, String syscontent) {
        return null;
    }


    /**
     * 返回accept
     *
     * @param macpkt： 上行的 macpayload 对象
     * @return
     */
    @Override
    //accept 填的是默认的数据
    public MacPktForm MacConstructData(MacPktForm macpkt) {
        MacJoinRequestForm macJoinRequestForm = (MacJoinRequestForm) macpkt;
        byte[] appnonce = {0x01, 0x02, 0x03};//随机
        byte[] netid = {0x00, 0x00, 0x65}; //devaddr 高7位  = netid 低7位        netid高17位自己定  高17位为全0
        byte[] devaddr = macJoinRequestForm.getDevAddr();
        byte[] rxdelay = {0x01}; //默认1
        byte[] cflist = null; //
        byte[] dlsetting = {0x00};
        MacJoinAcceptForm macjoinaccept = new MacJoinAcceptForm();
        //CreateRandom createrandom = new CreateRandom();
        //macjoinaccept.AppNonce = createrandom.RandomArray(8);
        macjoinaccept.AppNonce = appnonce;
        macjoinaccept.NetId = netid;
        macjoinaccept.DevAddr = devaddr;

        macjoinaccept.dlset.RFU = 0;
        macjoinaccept.dlset.RX1DRoffset = 0x01;
        macjoinaccept.dlset.Rx2DataRate = 0x02;

        macjoinaccept.RxDelay = rxdelay;
        macjoinaccept.CfList = cflist;
        byte[] appSKey = LoRaMacCrypto.LoRaMacJoinComputeAppSKey(LoRaMacCrypto.APPKEY, appnonce, macJoinRequestForm.DevNonce, netid);
        byte[] nwkSKey = LoRaMacCrypto.LoRaMacJoinComputeNwkSKey(LoRaMacCrypto.APPKEY, appnonce, macJoinRequestForm.DevNonce, netid);
        String nodehex = base64__.BytetoHex(devaddr);
        //入网设备注册与密钥存储
        Device device = deviceRepo.findByDeviceAddr(nodehex);
        if (device == null) {
            Device newDevice = new Device();
            newDevice.setDeviceAddr(nodehex);
            newDevice.setCreateDate(new Date());
            deviceRepo.save(newDevice);
        }
        //密钥存储到redis
        stringRedisTemplate.opsForValue().set(nodehex + "-NwkSKey", ObjectToString.byteToStr(nwkSKey));
        stringRedisTemplate.opsForValue().set(nodehex + "-AppSKey", ObjectToString.byteToStr(appSKey));
        return macjoinaccept;
    }

    @Override
    public MacPktForm MacConstructData(JSONObject message) {
        return null;
    }

    // TODO 测试
    public static void main(String[] arg) {
        byte[] appnonce = {0x01, 0x02, 0x03};//随机
        byte[] netid = {0x00, 0x00, 0x60}; //devaddr 高7位  = netid 低7位        netid高17位自己定  高17位为全0
        byte[] devaddr = {(byte) 0xc0, (byte) 0xa8, 0x01, 0x01}; // 随机唯一 IP 192.168.1.1
        byte[] rxdelay = {0x01}; //默认1
        byte[] cflist = null; //
        MacJoinAcceptForm macjoinaccept = new MacJoinAcceptForm();
        //CreateRandom createrandom = new CreateRandom();
        //macjoinaccept.AppNonce = createrandom.RandomArray(8);
        macjoinaccept.AppNonce = appnonce;
        macjoinaccept.NetId = netid;
        macjoinaccept.DevAddr = devaddr;

        macjoinaccept.dlset.RFU = 0;
        macjoinaccept.dlset.RX1DRoffset = 0;//默认为0
        macjoinaccept.dlset.Rx2DataRate = 0;//默认0

        macjoinaccept.RxDelay = rxdelay;//
        macjoinaccept.CfList = cflist;
        for (byte b : macjoinaccept.MacPkt2Byte()) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print("0x" + hex + " ");
        }
    }

}
