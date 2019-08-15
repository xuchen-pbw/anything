package main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.huawei.iota.login.LoginConfig;
import com.huawei.iota.login.LoginService;
import com.huawei.iota.util.IotaMessage;
import com.huawei.iota.util.MyObserver;

import bean.GatewayInfo;

public class AgentLiteLogin implements MyObserver {
	
	private static AgentLiteLogin instance = new AgentLiteLogin();
	
	
    public static AgentLiteLogin getInstance() {
        return instance;
    }
		
	public void loginAction() {
		System.out.println(" =============   start  login ============== ");
        configLoginPara();
        LoginService.login();		
	}
	
	public void logoutAction() {
		System.out.println(" =============   start  logout ============== ");
		LoginService.logout();
	}
	
	private static void configLoginPara() {
		
		if(AgentLiteUtil.isStringEmpty(GatewayInfo.getDeviceID())
				|| AgentLiteUtil.isStringEmpty(GatewayInfo.getAppID())
				|| AgentLiteUtil.isStringEmpty(GatewayInfo.getSecret())){
			
			String jsonStr = AgentLiteUtil.readToString("./workdir/gwbindinfo.json");
			JsonObject json = new Gson().fromJson(jsonStr, JsonObject.class);
			GatewayInfo.setDeviceID(json.get("deviceId").getAsString());
			GatewayInfo.setAppID(json.get("appId").getAsString());
			GatewayInfo.setSecret(json.get("deviceSecret").getAsString());
		}
		
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_DEVICEID,  GatewayInfo.getDeviceID());
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_APPID, GatewayInfo.getAppID());
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_SECRET, GatewayInfo.getSecret());
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_IOCM_ADDR, AgentLiteBase.PLATFORM_IP);
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_IOCM_PORT, AgentLiteBase.HTTPS_PORT);
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_MQTT_ADDR, AgentLiteBase.PLATFORM_IP);
        LoginConfig.setConfig(LoginConfig.LOGIN_CONFIG_MQTT_PORT, AgentLiteBase.MQTTS_PORT);
	}
	
	// 收到登陆通知后执行
	private void loginResultAction(IotaMessage iotaMsg) {
		System.out.println(" ============= login Success ============== ");
		GatewayInfo.setAccessToken(iotaMsg.getString(LoginService.LOGIN_IE_ACCTOKEN));
	}
	
	// 收到登出通知后执行
	private void logoutResultAction(IotaMessage iotaMsg) {
		System.out.println(" ============= logout ============== ");
		   int reason = iotaMsg.getUint(LoginService.LOGIN_IE_REASON, -1);
           System.out.println("reason is : " + reason);
	}
		
	@Override
	public void update(IotaMessage iotaMsg) {
		System.out.println("LoginManager收到通知:" + iotaMsg);
		int mMsgType = iotaMsg.getMsgType();
		switch(mMsgType) {
			case 1:
				loginResultAction(iotaMsg);
				break;
			case 2:
				logoutResultAction(iotaMsg);
				break;
			default:
				break;
		}
	}
}
