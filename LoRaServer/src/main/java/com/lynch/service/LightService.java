package com.lynch.service;

import com.lynch.LoRaServer;
import com.lynch.domain.*;
import com.lynch.mac.MacPktForm;
import com.lynch.mac.OperateMacUnconfirmedDataDown;
import com.lynch.repository.DeviceRepo;
import com.lynch.repository.LightRepo;
import com.lynch.util.DatetimePrint;
import com.lynch.util.base64.base64__;
import com.lynch.util.phy.PhyConstruct;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by lynch on 2019/1/7. <br>
 **/
@Service
public class LightService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LightService.class);

    @Autowired
    private LightRepo lightRepo;
    @Autowired
    private DeviceRepo deviceRepo;

    /**
     * web下发的控制命令，处理从agent获取的路灯数据信息
     */
    public void handlerLightFromAgent(String body) {
        LOGGER.info("The message from the web to control the node is ={}", body);
        JSONObject messageJson = JSONObject.fromObject(body);
        String type = messageJson.getString("type");
        System.out.println(messageJson);
        if (type.equals("write.req")) {
            String controlDeviceId = messageJson.getString("device_id");
            if (messageJson.containsKey("mark")) {
                String command = messageJson.getString("mark");
                if (command.equals("acquireStatus")) {
                    //立即获取灯的状态
                    LOGGER.info("acquire the nodeId = {} status", controlDeviceId);
                    JSONObject acquireStatus = new JSONObject();
                    acquireStatus.put("nodeId", controlDeviceId);
                    acquireStatus.put("nodeswitch", "s");
                    downControl(controlDeviceId, acquireStatus);
                } else if (command.substring(2, 12).equals("changeRate")) {
                    //改变上行速率
                    JSONObject commandJson = JSONObject.fromObject(command.replace("\"", ""));
                    JSONObject dataUpRate = commandJson.getJSONObject("changeRate");
                    LOGGER.info("change the nodeId = {} which data up rate = {} ", controlDeviceId, dataUpRate);
                    dataUpRate.put("nodeId", controlDeviceId);
                    downChangeRate(controlDeviceId, dataUpRate);
                } else {
                    System.out.println("unknown command");
                }
            } else {
                String command = messageJson.getString("key");
                String value = messageJson.getString("value");
                switch (command) {
                    //开关灯
                    case "0.general_onoff":
                        if (value.equals("1"))
                            value = "n";
                        else if (value.equals("0")) {
                            value = "f";
                        }
                        LOGGER.info("change the nodeId = {} light status and value = {} ", controlDeviceId, value);
                        JSONObject downMessage = new JSONObject();
                        downMessage.put("nodeId", controlDeviceId);
                        downMessage.put("nodeswitch", value);
                        downControl(controlDeviceId, downMessage);
                        break;
                    default:
                        System.out.println("unknown command");
                        break;
                }
            }
        } else if (type.equals("read.req")) {
            System.out.println("获取终端信息");

        } else {
            System.out.println("can't handle type");
        }

    }

    /**
     * 发送下行控制到队列
     * 包含灯的开关和立即获取灯现在的状态
     *
     * @param nodeId
     * @param downMessage
     */
    public void downControl(String nodeId, net.sf.json.JSONObject downMessage) {
        try {
            OperateMacUnconfirmedDataDown operateMacUnconfirmedDataDown = new OperateMacUnconfirmedDataDown();
            MacPktForm macPktFormDown = operateMacUnconfirmedDataDown.MacConstructData(downMessage);
            byte[] downMacData = PhyConstruct.PhyPkt2Byte(macPktFormDown, 3);
            DownInfoForm DownInfo = new DownInfoC_LoRa();
            synchronized (LoRaServer.queueDown) {
                DownInfoNodeMap downInfoNodeMap = new DownInfoNodeMap();
                downInfoNodeMap.setNodeId(nodeId);
                downInfoNodeMap.setDownInfoForm(DownInfo.ConstructDownInfo(downMacData, 3));
                LoRaServer.queueDown.add(downInfoNodeMap);
//                LoRaServer.queueDown.add(DownInfo.ConstructDownInfo(downMacData, 3));
                System.out.print("send to End-Node: " + DatetimePrint.timeprint() + " ");
                base64__.myprintHex(downMacData);
//                LOGGER.info("push into the queue and the queue size = {}", LoRaServer.queueDown.size());
                System.out.println("PUSH-------------Scucsacas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送下行控制到队列
     * 控制终端上报速率
     *
     * @param nodeId
     * @param downMessage
     */
    public void downChangeRate(String nodeId, net.sf.json.JSONObject downMessage) {
        try {
            OperateMacUnconfirmedDataDown operateMacUnconfirmedDataDown = new OperateMacUnconfirmedDataDown();
            MacPktForm macPktFormDown = operateMacUnconfirmedDataDown.MacConstructData(downMessage);
            byte[] downMacData = PhyConstruct.PhyPkt2Byte(macPktFormDown, 3);
            DownInfoForm DownInfo = new DownInfoC_LoRa();
            synchronized (LoRaServer.queueDown) {
                DownInfoNodeMap downInfoNodeMap = new DownInfoNodeMap();
                downInfoNodeMap.setNodeId(nodeId);
                downInfoNodeMap.setDownInfoForm(DownInfo.ConstructDownInfo(downMacData, 3));
                LoRaServer.queueDown.add(downInfoNodeMap);
//                LoRaServer.queueDown.add(DownInfo.ConstructDownInfo(downMacData, 3));
                System.out.print("send to End-Node: " + DatetimePrint.timeprint() + " ");
                base64__.myprintHex(downMacData);
//                LOGGER.info("push into the queue and the queue size = {}", LoRaServer.queueDown.size());
                System.out.println("PUSH-------------Scucsacas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 灯状态持久化
     *
     * @param deviceAddr
     * @param nodeSwitch
     * @param nodeBrightness
     * @param nodeThreshold
     */
    public void saveLight(String deviceAddr, String nodeSwitch, String nodeBrightness, String nodeThreshold, String webControlFlag) {
        Device device = deviceRepo.findByDeviceAddr(deviceAddr);
        if (device != null) {
            List<Light> lightList = new ArrayList<>();
            Light light = new Light();
            light.setTimestamp(new Date());
            light.setLightSwitch(nodeSwitch);
            light.setBrightness(nodeBrightness);
            light.setThreshold(nodeThreshold);
            light.setWebContrlFlag(webControlFlag);
            light.setDeviceAddr(deviceAddr);
            lightList.add(light);
            device.setLightList(lightList);
            lightRepo.save(light);
            deviceRepo.save(device);
        }
    }
}
