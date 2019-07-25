package com.lynch.domain;


import com.lynch.LoRaServer;
import com.lynch.util.DatetimePrint;
import com.lynch.util.base64.base64__;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * LoRa模式下class B 的各项 JSON 数据
 */
public class DownInfoB_LoRa implements DownInfoForm {


    private boolean imme;
    private double tmms;
    //    private double tmst;
    private boolean ncrc;
    private float freq;
    private int rfch;
    private int powe;
    private String modu;
    private String datr;
    private String codr;
    private boolean ipol;
    private int prea;
    private int port; //处理双通道
    private int size;
    private String data;

    @Override
    public DownInfoForm ConstructDownInfo(UpInfoForm info, byte[] data, int type) {
        List<Long> finaltime = ClassBNodeOpens.slotsStart();
        InfoLoraModEndForm infoLoraModEndForm = (InfoLoraModEndForm) info;
        DownInfoB_LoRa downInfoB_LoRa = new DownInfoB_LoRa();
        downInfoB_LoRa.setImme(false);
        double cha = (double) 315964800 * 1000;
        //可以和当前时间进行对比，从而抛弃已过时间点
        //将已执行的时间舍弃
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        for (int i = 0; i < finaltime.size(); i++) {
            // GPS时间戳转换成UTC时间
            String sd = sdf.format(new Date((long) (finaltime.get(i) + cha)));
//            System.out.println((finaltime.get(i) + cha) - System.currentTimeMillis());
            long SecondSinceEpoch = ((System.currentTimeMillis() + 18000) / 1000 - 315964800);
            System.out.println("Now GPS time：" + SecondSinceEpoch + "秒");
            System.out.print("Now GPSFormat time:");
            DatetimePrint.todateFormat(SecondSinceEpoch * 1000);
            if (((finaltime.get(i) + cha) - System.currentTimeMillis()) > 0) {
                if (((finaltime.get(i) + cha) - System.currentTimeMillis()) <= (128000 / LoRaServer.pingNb)) {
                    downInfoB_LoRa.setTmms(finaltime.get(i) + 1000);
//            downInfoB_LoRa.setTmst(infoLoraModEndForm.getTmst() + finaltime.get(i));
                    System.out.println("class B down,the node will open " + finaltime.size() + " slots,this time is slot" + i);
                    System.out.println("slot" + i + "  time:" + sd);
                    System.out.println("slot" + i + " mills:" + finaltime.get(i));
                    DatetimePrint.todateFormat(finaltime.get(i));
                    break;
                } else {
                    System.out.println("class B down,there isn't slot to use in this time,error!");
                    break;
                }
            }
        }
        downInfoB_LoRa.setNcrc(false);
        downInfoB_LoRa.setFreq((float) 434.665);
        downInfoB_LoRa.setRfch(0); //下行
        downInfoB_LoRa.setCodr(infoLoraModEndForm.getCodr());
        downInfoB_LoRa.setPowe(27);
        downInfoB_LoRa.setModu("LORA");
        downInfoB_LoRa.setDatr(infoLoraModEndForm.getDatr_lora());
        downInfoB_LoRa.setIpol(false);
        downInfoB_LoRa.setPrea(8);
        downInfoB_LoRa.setSize(data.length);
        downInfoB_LoRa.setPort(infoLoraModEndForm.getPort());//处理双通道
        // System.out.println(data.length);
        downInfoB_LoRa.setData(base64__.encode(data));
        return downInfoB_LoRa;
    }

    @Override
    public DownInfoForm ConstructDownInfo(byte[] data, int i) {
        return null;
    }


    public boolean isImme() {
        return imme;
    }

    public void setImme(boolean imme) {
        this.imme = imme;
    }

    public double getTmms() {
        return tmms;
    }

    public void setTmms(double tmms) {
        this.tmms = tmms;
    }

    public boolean isNcrc() {
        return ncrc;
    }

    public void setNcrc(boolean ncrc) {
        this.ncrc = ncrc;
    }

    public float getFreq() {
        return freq;
    }

    public void setFreq(float freq) {
        this.freq = freq;
    }

    public int getRfch() {
        return rfch;
    }

    public void setRfch(int rfch) {
        this.rfch = rfch;
    }

    public int getPowe() {
        return powe;
    }

    public void setPowe(int powe) {
        this.powe = powe;
    }

    public String getModu() {
        return modu;
    }

    public void setModu(String modu) {
        this.modu = modu;
    }

    public String getDatr() {
        return datr;
    }

    public void setDatr(String datr) {
        this.datr = datr;
    }

    public String getCodr() {
        return codr;
    }

    public void setCodr(String codr) {
        this.codr = codr;
    }

    public boolean isIpol() {
        return ipol;
    }

    public void setIpol(boolean ipol) {
        this.ipol = ipol;
    }

    public int getPrea() {
        return prea;
    }

    public void setPrea(int prea) {
        this.prea = prea;
    }

    public int getSize() {
        return size;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
