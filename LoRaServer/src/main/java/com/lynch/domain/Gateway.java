package com.lynch.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lynch on 2019-05-17. <br>
 * 网关相关数据信息
 **/
@Entity
@Table(name = "t_gateway")
public class Gateway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gateway_id")
    private Long gatewayId;

    private String gatewayAddr;

    private String gatewayHost;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createDate;

    public Long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(Long gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getGatewayAddr() {
        return gatewayAddr;
    }

    public void setGatewayAddr(String gatewayAddr) {
        this.gatewayAddr = gatewayAddr;
    }

    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
