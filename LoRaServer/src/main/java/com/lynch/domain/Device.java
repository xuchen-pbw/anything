package com.lynch.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lynch on 2019/3/11. <br>
 * 终端相关信息
 **/
@Entity
@Table(name = "t_device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long deviceId;

    private String deviceAddr;

    private String gateway;

    private String gatewayHost;

    private String DevEui;

    private String latitude;//纬度

    private String longitude;//经度

    private Integer upFcnt; //上行

    private Integer downFcnt; //下行


    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createDate;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getDevEui() {
        return DevEui;
    }

    public void setDevEui(String devEui) {
        DevEui = devEui;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public Integer getUpFcnt() {
        return upFcnt;
    }

    public void setUpFcnt(Integer upFcnt) {
        this.upFcnt = upFcnt;
    }

    public Integer getDownFcnt() {
        return downFcnt;
    }

    public void setDownFcnt(Integer downFcnt) {
        this.downFcnt = downFcnt;
    }

    //灯的最新数据
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_device_light", joinColumns = {@JoinColumn(name = "device_id")}, inverseJoinColumns = {@JoinColumn(name = "light_id")})
    private List<Light> lightList;


    public List<Light> getLightList() {
        return lightList;
    }

    public void setLightList(List<Light> lightList) {
        this.lightList = lightList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
