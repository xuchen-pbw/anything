package main;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.huawei.iota.iodev.IodevService;
import com.huawei.iota.iodev.datatrans.DataTransService;
import com.huawei.iota.util.IotaMessage;
import com.huawei.iota.util.MyObserver;

import bean.GatewayInfo;

public class AgentLiteDataTrans implements MyObserver {

	private static final int MAX_NUM = 100;
	private static AgentLiteDataTrans instance = new AgentLiteDataTrans();
	private static final String MSG_TIMESTAMP_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
	
	private final static SimpleDateFormat DF_TZ = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
	
	private static String nowTZ() {
	    long getTime = System.currentTimeMillis() - (8 * 60 * 60 * 1000);
	    return DF_TZ.format(new Date(getTime));
    }

	public static AgentLiteDataTrans getInstance() {
	    instance.runTask();
	    return instance;
	}
	
	private static boolean sending = false;
	
	public synchronized void reportData() {
	    String deviceId = GatewayInfo.getDeviceID();
	    System.out.println("lData.size=" + lData.size() + "; deviceId=" + deviceId);
		if (lData.size() == 0) {
			return;
		}
		if (sending) {
			System.out.println("reportData ERROR!!");
			return;
		}
		try {
		    sending = true;
		    JsonArray allDevice = new JsonArray();
		    JsonObject device = new JsonObject();
		    device.addProperty("deviceId", deviceId);
		    JsonArray allService = new JsonArray();
		    for (Object item : lData.values()) {
		        allService.add((JsonObject) item);
		    }
		
		    device.add("services", allService);
		    allDevice.add(device);
		    JsonObject root = new JsonObject();
		    root.add("devices", allDevice);
		
		    int cookie;
		    Random random = new Random();
			cookie = random.nextInt(65535);
			String json = root.toString();
			DataTransService.batchDataReport(cookie, "NULL", json.getBytes());
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    lData.clear();
		    sending = false;
		}
	}
		
	public static boolean taskIsRunning = false;
	public void runTask() {
	    if (taskIsRunning) {
	    	return;
	    }
	    taskIsRunning = true;
	    new Timer("timerTask").schedule(new TimerTask() {
		    @Override
		    public void run() {
		    reportData();
		    }
	    }, 0 , 1000);
	}
	
	private static ConcurrentHashMap<Long, Object> lData = new ConcurrentHashMap<Long, Object>(2 * MAX_NUM);
	private static long idx = 0;
	
	public synchronized void gwDataReport(String helperId, String tagId) {
	    JsonObject data = new JsonObject();
	    data.addProperty("receiverId", AgentLiteBase.receiverId);
		data.addProperty("helperId", helperId);
		data.addProperty("tagId", tagId);
		
		JsonObject service = new JsonObject();
		service.addProperty("serviceId", "Tag");
		service.add("data", data);
		service.addProperty("eventTime", nowTZ());
		idx ++;
		lData.put(idx, service);
	    if (lData.size() >= MAX_NUM) {
	        reportData();
	    }
	}
	
	
	public void gwDataReportByMqttDataPub() {
	    System.out.println(" ============= gwDataReportByMqttDataPub! ============== ");
		int cookie;
		Random random = new Random();
		cookie = random.nextInt(65535);
		
		String deviceId = GatewayInfo.getDeviceID();
		
		JsonObject headerData = new JsonObject();
		headerData.addProperty("method", "PUT");
		String fromStr = "/device/" + deviceId + "/services/Storage";
		String toStr = "/data/v1.1.0/devices/" + deviceId + "/services/Storage";
		headerData.addProperty("from", fromStr);
		headerData.addProperty("to", toStr);
		
		headerData.addProperty("access_token", GatewayInfo.getAccessToken());
		
		SimpleDateFormat df = new SimpleDateFormat(MSG_TIMESTAMP_FORMAT);
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		String curTime = df.format(new Date(System.currentTimeMillis()));
		headerData.addProperty("timestamp", curTime);
		headerData.addProperty("eventTime", curTime);
		
		JsonObject bodyData = new JsonObject();
		bodyData.addProperty("storage", "10240");
		bodyData.addProperty("usedPercent", "18");
		
		JsonObject mqttMsg = new JsonObject();
		mqttMsg.add("header", headerData);
		mqttMsg.add("body", bodyData);
		
		DataTransService.mqttDataPub(cookie, "/cloud/signaltrans/v2/categories/data", 1, mqttMsg.toString().getBytes());
	}
	
	public void subDevDataReport() {
		System.out.println(" ============= subDevDataReport! ============== ");
		int cookie;
		Random random = new Random();
		cookie = random.nextInt(65535);
		
		String deviceId = GatewayInfo.getSensorId();
		
		DataTransService.dataReport(cookie, null, deviceId, "Battery", "{\"batteryLevel\":98}");
		DataTransService.dataReport(cookie, null, deviceId, "Motion", "{\"motion\":\"DETECTED\"}");
	}
		
	public void mqttSubTopic() {
		System.out.println(" ============= mqttSubTopic! ============== ");
		int cookie;
		Random random = new Random();
		cookie = random.nextInt(65535);
		
		String deviceId = GatewayInfo.getDeviceID();
		DataTransService.mqttSubTopic(cookie, "/gws/" + deviceId + "/signaltrans/v2/categories/#", 1);
	}
	
	private void getDataReportAnswer(IotaMessage iotaMsg) {
		String deviceId = iotaMsg.getString(DataTransService.DATATRANS_IE_DEVICEID);
		int retcode = iotaMsg.getUint(DataTransService.DATATRANS_IE_RESULT, 0);
		System.out.println("deviceId:" + deviceId + "data report, ret = " + retcode);
	    System.out.println("report gateway data success, cookie = " + iotaMsg.getUint(DataTransService.DATATRANS_IE_COOKIE, 0));
	    if (deviceId.equals(GatewayInfo.getSensorId())) {
	        System.out.println(" report sensor data success ");
	    }
	}
	
	private void getCmdReceive(IotaMessage iotaMsg) {
		System.out.println("=========receive iotCMD ============");
		String deviceId = iotaMsg.getString(DataTransService.DATATRANS_IE_DEVICEID);
		String requestId = iotaMsg.getString(DataTransService.DATATRANS_IE_REQUSTID);
		String serviceId = iotaMsg.getString(DataTransService.DATATRANS_IE_SERVICEID);
		String method = iotaMsg.getString(DataTransService.DATATRANS_IE_METHOD);
		String cmd = iotaMsg.getString(DataTransService.DATATRANS_IE_CMDCONTENT);
		if (method.equals("REMOVE_GATEWAY")) {
		    //rmvGateway(context);
		}
		
		System.out.println("Receive cmd :"
		+ "\ndeviceId = " + deviceId
		+ "\nrequestId = " + requestId
		+ "\nserviceId = " + serviceId
		+ "\nmethod = " + method
		+ "\ncmd = " + cmd);
	}
	
	@Override
	public void update(IotaMessage iotaMsg) {
	    int mMsgType = iotaMsg.getMsgType();
	    switch (mMsgType) {
			//数据上报应答
		case IodevService.IODEV_MSG_DATA_REPORT_RSP:
		    getDataReportAnswer(iotaMsg);
		    break;
		//被动接收命令
		case IodevService.IODEV_MSG_RECEIVE_CMD:
		    getCmdReceive(iotaMsg);
		    break;
		//MQTT消息推送
		case IodevService.IODEV_MSG_MQTT_PUB_RSP:
		    mqttPubRsp(iotaMsg);
		    break;
		//MQTT topic订阅
		case IodevService.IODEV_MSG_MQTT_SUB_ACK:
			mqttSubtopicRsp(iotaMsg);
			break;
		default:
			break;
	    }
	}
	
	private void mqttPubRsp(IotaMessage iotaMsg) {
		int retcode = iotaMsg.getUint(DataTransService.DATATRANS_IE_RESULT, 0);
		if (0 == retcode) {
		    System.out.println("mqtt Data Pub success");
	    } else {
	        System.out.println("mqtt Data Pub failed ,ret = " + retcode);
	    }
	}
	
	private void mqttSubtopicRsp(IotaMessage iotaMsg) {
		int retcode = iotaMsg.getUint(DataTransService.DATATRANS_IE_RESULT, 0);
		if (0 == retcode) {
		    System.out.println("mqtt sub topic success");
	    } else {
	        System.out.println("mqtt sub topic failed ,ret = " + retcode);
		}
	}
}