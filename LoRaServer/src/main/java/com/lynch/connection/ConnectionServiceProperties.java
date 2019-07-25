package com.lynch.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by lynch on 2018/11/2. <br>
 **/
@ConfigurationProperties(prefix = "lora-server")
public class ConnectionServiceProperties {
    public static final String GATEWAY_HOST = "192.168.1.152";
    public static final int CONNECTION_UPPORT = 1781;
    public static final int CONNECTION_DOWNPORT = 1782;

    private String gateway_host = GATEWAY_HOST;
    private int up_port = CONNECTION_UPPORT;
    private int down_port = CONNECTION_DOWNPORT;

    public String getGateway_host() {
        return gateway_host;
    }

    public void setGateway_host(String gateway_host) {
        this.gateway_host = gateway_host;
    }

    public int getUp_port() {
        return up_port;
    }

    public void setUp_port(int up_port) {
        this.up_port = up_port;
    }

    public int getDown_port() {
        return down_port;
    }

    public void setDown_port(int down_port) {
        this.down_port = down_port;
    }

}
