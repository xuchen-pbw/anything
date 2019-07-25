package com.lynch.mac;


import com.lynch.domain.Device;
import com.lynch.repository.DeviceRepo;
import com.lynch.util.ObjectToString;
import com.lynch.util.SpringTool;
import com.lynch.util.aes.LoRaMacCrypto;
import com.lynch.util.base64.base64__;
import net.sf.json.JSONObject;

import java.util.Arrays;

public class OperateMacJoinRequest implements OperateMacPkt {

    DeviceRepo deviceRepo = (DeviceRepo) SpringTool.getBean("deviceRepo");

    public MacPktForm MacParseData(byte[] data, String gatawayId, String syscontent) {
        byte[] DevtoAddr = new byte[4];
        byte[] datain = Arrays.copyOfRange(data, 0, 19);
        byte[] micComputed = LoRaMacCrypto.LoRaMacJionRequestComputeMic(datain, datain.length, LoRaMacCrypto.APPKEY);
        byte[] micReceived = new byte[4];
        System.arraycopy(data, 19, micReceived, 0, 4);
        base64__.myprintHex(micComputed);
        base64__.myprintHex(micReceived);
        if (!ObjectToString.byteToStr(micComputed).equals(ObjectToString.byteToStr(micReceived)))
            return null;
        MacJoinRequestForm macjoinrequest = new MacJoinRequestForm();
        System.arraycopy(data, 1, macjoinrequest.AppEui, 0, 8);
        System.arraycopy(data, 9, macjoinrequest.DevEui, 0, 8);
        System.arraycopy(data, 17, macjoinrequest.DevNonce, 0, 2);
        System.out.print("DevEui: ");
        base64__.myprintHex(macjoinrequest.DevEui);
        System.out.println(ObjectToString.byteToStr(macjoinrequest.DevEui));
        System.arraycopy(macjoinrequest.DevEui, 0, DevtoAddr, 0, 4);
        Device device = deviceRepo.findByDeviceAddr(base64__.BytetoHex(DevtoAddr));
        if (device == null) {
            device.setDeviceAddr(base64__.BytetoHex(DevtoAddr));
            deviceRepo.save(device);
        }
        return macjoinrequest;
    }

    /**
     * 返回 Accept 帧
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
