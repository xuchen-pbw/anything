package com.lynch.domain;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by lynch on 2019/1/7. <br>
 * 路灯相关信息
 **/
@Entity
@Table(name = "t_light")
public class Light {

    public static final String WEB_CONTROL_YES = "yes";
    public static final String WEB_CONTROL_NO = "no";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "light_id")
    private Long lightId;

    private String lightSwitch;

    private String brightness;

    private String threshold;

    private String webContrlFlag;

    private String deviceAddr;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date timestamp;

    public Long getLightId() {
        return lightId;
    }

    public void setLightId(Long lightId) {
        this.lightId = lightId;
    }

    public String getLightSwitch() {
        return lightSwitch;
    }

    public void setLightSwitch(String lightSwitch) {
        this.lightSwitch = lightSwitch;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getWebContrlFlag() {
        return webContrlFlag;
    }

    public void setWebContrlFlag(String webContrlFlag) {
        this.webContrlFlag = webContrlFlag;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    @Override
    public String toString() {
        return "Light{" +
                ", lightId='" + lightId + '\'' +
                ", lightSwitch='" + lightSwitch + '\'' +
                ", brightness='" + brightness + '\'' +
                ", threshold='" + threshold + '\'' +
                ", webContrlFlag='" + webContrlFlag + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
