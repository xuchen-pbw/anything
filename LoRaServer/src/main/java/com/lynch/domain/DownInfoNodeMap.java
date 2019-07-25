package com.lynch.domain;

/**
 * Created by lynch on 2019-05-16. <br>
 * 终端设备地址与下行数据包的映射构造，方便下行数据发送时，
 * 从队列中取出的数据能知道发送给哪个终端，从而获取其绑定的网关
 **/
public class DownInfoNodeMap {
    private String nodeId;

    private DownInfoForm downInfoForm;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public DownInfoForm getDownInfoForm() {
        return downInfoForm;
    }

    public void setDownInfoForm(DownInfoForm downInfoForm) {
        this.downInfoForm = downInfoForm;
    }
}
