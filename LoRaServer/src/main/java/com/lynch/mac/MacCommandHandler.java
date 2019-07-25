package com.lynch.mac;

import com.lynch.LoRaServer;
import com.lynch.util.ByteArrayandInt;
import com.lynch.util.ClassMod;
import com.lynch.util.DatetimePrint;
import com.lynch.util.JsonGetdata;

import java.util.ArrayList;

/**
 * mac命令的处理
 * Created by lynch on 2019/3/11. <br>
 **/
public class MacCommandHandler {
    public static ArrayList<Byte> macHandler(byte[] macContent) {

        ArrayList<Byte> FoptList = new ArrayList<>();

        for (int i = 0; i < macContent.length; i++) {
            switch (macContent[i]) {
                case LoRaServer.DeviceTimeReq:
                    System.out.println("DeviceTimeReq");
                    FoptList.add(LoRaServer.DeviceTimeReq);
                    System.out.println(System.currentTimeMillis());
                    int SecondSinceEpoch = (int) ((System.currentTimeMillis() + 18000) / 1000 - 315964800);
                    System.out.println("GPS time：" + SecondSinceEpoch + "秒");
                    int mode = SecondSinceEpoch % 128;
                    FoptList.add((byte) (SecondSinceEpoch & 0xFF));
                    FoptList.add((byte) ((SecondSinceEpoch >> 8) & 0xFF));
                    FoptList.add((byte) ((SecondSinceEpoch >> 16) & 0xFF));
                    FoptList.add((byte) ((SecondSinceEpoch >> 24) & 0xFF));
                    long steps = (System.currentTimeMillis() % 1000) * 256 / 1000;
                    System.out.println("steps:" + steps);
                    int awayNextbeacon = 128 - mode;
                    System.out.println("距离下一个beacon时间：" + awayNextbeacon + "秒");
                    FoptList.add((ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex((int) steps))));
                    byte[] timeFopt = new byte[FoptList.size()];
                    //Fopts
                    for (int j = 0; j < FoptList.size(); j++) {
                        timeFopt[j] = FoptList.get(j);
                    }
                    byte[] timebyte = new byte[4];
                    System.arraycopy(timeFopt, 1, timebyte, 0, 4);
                    long gpslong = ByteArrayandInt.byteArrayToInt(timebyte);
                    DatetimePrint.todateFormat((gpslong + 315964800 - 18) * 1000 + steps);
                    DatetimePrint.todateFormat(gpslong * 1000 + steps);
                    break;
                case LoRaServer.pingSlotInfoReq:
                    // 构造 pingSoltInfoAns
                    System.out.println("pingSlotInfoReq");
                    FoptList.add(LoRaServer.pingSlotInfoReq);
                    //[7:3] RFU [2:0] Periodicity
                    int Periodicity = (macContent[1] & 0x07);
                    LoRaServer.periodicity = Periodicity;
                    System.out.println("Periodicity:" + LoRaServer.periodicity);
                    break;
                case LoRaServer.PingSlotChannelReq:
                    break;
                case LoRaServer.DeviceModeInd:
                    System.out.println("DeviceModeInd");
                    FoptList.add(LoRaServer.DeviceModeInd);
                    FoptList.add(macContent[1]);
                    switch (macContent[1]) {
                        case 0x00:
                            LoRaServer.classMod = ClassMod.Class_A;
                            break;
                        case 0x02:
                            LoRaServer.classMod = ClassMod.Class_C;
                            break;
                        default:
                    }
                    break;
                case LoRaServer.LinkCheckReq:
                    System.out.println("LinkCheckReq");
                    FoptList.add(LoRaServer.LinkCheckReq);
                    int margin = (int) Math.round(JsonGetdata.lsnr - LoRaServer.sf_snr.get(JsonGetdata.datr));
                    if (margin < 0)
                        margin = 0;
                    System.out.println("margin:" + margin);
                    //Margin 0～254（0x00~0xfe)
                    FoptList.add((ByteArrayandInt.hexToByte(ByteArrayandInt.inttohex(margin))));
                    //GwCnt
                    FoptList.add((byte) 0x01);
                    break;
                case LoRaServer.LinkADRReq:
                    break;
                case LoRaServer.RXParamSetupReq:
                    break;
                case LoRaServer.DutyCycleReq:
                    System.out.println("DutyCycleReq");
                    FoptList.add(LoRaServer.DutyCycleReq);
                    byte MaxDCycle = macContent[0];
                    byte AggregatedDCycle = (byte) (1 << MaxDCycle);
                    FoptList.add((byte) (AggregatedDCycle & 0xFF));
                case LoRaServer.DevStatusReq:
                    System.out.println("DevStatusReq");
                    FoptList.add(LoRaServer.DevStatusReq);
                    switch (macContent[1]) {
                        case 0x00:
                            System.out.println("The end-device is connnected to an external power source.");
                            break;
                        case (byte) 0xff:
                            System.out.println("The end-device was not able to measure the batter level.");
                            break;
                        default:
                            System.out.println("The battery level is " + macContent[0]);
                            break;

                    }
                    //Status
                    // Margin是之前成功收到的DevStatusReq命令的解调信噪比进行四舍五入的值，它是一个有符号6bit整数，最小值为-32，最大值31.
                    // int margin = (int)(Math.random()*(31-(-32))+1); [7:6] RFU [5:0] margin
                    int Margin = macContent[2] & 0x3F;
                    System.out.println("The Margin is " + Margin);
                    break;
                default:
                    break;
//                        macunconfirmeddatadown.fctrl.FOptslen = 4;
//                        macunconfirmeddatadown.Fopts = new byte[macunconfirmeddatadown.fctrl.FOptslen];
//                        macunconfirmeddatadown.Fopts = macunconfirmeddataup.DevAddr;
//                        break;
            }
        }
        return FoptList;

    }
}
