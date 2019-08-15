package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.google.gson.JsonObject;
import com.huawei.iota.bind.BindConfig;
import com.huawei.iota.bind.BindService;
import com.huawei.iota.iodev.IotaDeviceInfo;
import com.huawei.iota.login.LoginService;
import com.huawei.iota.util.IotaMessage;
import com.huawei.iota.util.MyObserver;

import bean.GatewayInfo;

public class AgentLiteBind implements MyObserver {
	
	public static IotaDeviceInfo deviceInfo = null;
	
	private static AgentLiteBind instance = new AgentLiteBind();
	
    public static AgentLiteBind getInstance() {
        return instance;
    }
		
	public void bindAction() {
		System.out.println(" =============   start  bind ============== ");
		//绑定配置
		configBindPara();
		deviceInfo = new IotaDeviceInfo(AgentLiteBase.nodeId, AgentLiteBase.manufactrueId, AgentLiteBase.deviceType, AgentLiteBase.model, AgentLiteBase.protocolType);
		
		//发起绑定请求
		BindService.bind(AgentLiteBase.verifyCode, deviceInfo);
	}
	
	
	//绑定配置
	private static void configBindPara() {
		boolean res = false;
		res = BindConfig.setConfig(BindConfig.BIND_CONFIG_ADDR, AgentLiteBase.PLATFORM_IP);
		res = BindConfig.setConfig(BindConfig.BIND_CONFIG_PORT, AgentLiteBase.HTTPS_PORT);

		if (false == res) {
			System.out.println(" ============ set BindConfig failed ============");
		}
		System.out.println("startBind platformIP =" + AgentLiteBase.PLATFORM_IP + ":" + AgentLiteBase.HTTPS_PORT);		
	}
	
    //保存绑定响应消息携带的参数
    private void saveBindParaAndGotoLogin(IotaMessage iotaMsg) {
        System.out.println(" ============ saveBindParaAndGotoLogin ============");
        String appId = iotaMsg.getString(BindService.BIND_IE_APPID);
        String deviceId = iotaMsg.getString(BindService.BIND_IE_DEVICEID);
        String secret = iotaMsg.getString(BindService.BIND_IE_DEVICESECRET);

        GatewayInfo.setAppID(appId);
        GatewayInfo.setDeviceID(deviceId);
        GatewayInfo.setSecret(secret);
        
        saveGatewayInfo();
		AgentLiteLogin agentLiteLogin = AgentLiteLogin.getInstance();
		LoginService loginService = LoginService.getInstance();
		loginService.registerObserver(agentLiteLogin);
		agentLiteLogin.loginAction();
    }
    
    private void saveGatewayInfo() {
         JsonObject data = new JsonObject();
         data.addProperty("deviceId", GatewayInfo.getDeviceID());
         data.addProperty("deviceSecret", GatewayInfo.getSecret());
         data.addProperty("appId", GatewayInfo.getAppID());
         
         File file = new File("./workdir/gwbindinfo.json");
         if(!file.exists()){
             try {
                 file.createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         
         BufferedWriter writer = null;
         try {
             writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
             writer.write(data.toString());
         } catch (IOException e) {
             e.printStackTrace();
         }finally {
             try {
                 if(writer != null){
                     writer.close();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }
    
	@Override
	public void update(IotaMessage arg0) {
		// TODO Auto-generated method stub
		System.out.println("BindManager收到绑定通知:" + arg0);
		int status = arg0.getUint(BindService.BIND_IE_RESULT, -1);
		System.out.println("status is :" + status);
		switch (status) {
			case 0:
                saveBindParaAndGotoLogin(arg0);
                break;
            default:
            	System.out.println(" =============  绑定失败   ============== ");
                bindAction();
                break;
		}
	}
    

}
