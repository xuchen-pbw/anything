package com.lynch.domain;

/**
 * 下行数据构造接口
 */
public interface DownInfoForm {
    public DownInfoForm ConstructDownInfo(UpInfoForm info, byte[] data, int i);

    public DownInfoForm ConstructDownInfo(byte[] data, int i);
}
