package com.lynch.domain;


import com.lynch.util.base64.base64__;


/**
 * LoRa模式下class A 的各项 JSON 数据
 */
public class DownInfoA_LoRa implements DownInfoForm {

    private boolean imme;
    private double tmst;
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


    private static String lastData;

    @Override
    public DownInfoForm ConstructDownInfo(UpInfoForm info, byte[] data, int type) {
        InfoLoraModEndForm infoLoraModEndForm = (InfoLoraModEndForm) info;
        DownInfoA_LoRa downInfoA_LoRa = new DownInfoA_LoRa();
        downInfoA_LoRa.setImme(false);
        downInfoA_LoRa.setData(base64__.encode(data));
        if (type == 0) {
            System.out.println("\n\nJoinRequest\n\n");
            if (lastData == null || !data.equals(lastData)) {
                lastData = this.data;
                downInfoA_LoRa.setTmst(infoLoraModEndForm.getTmst() + 5000000);
                downInfoA_LoRa.setNcrc(false);
                downInfoA_LoRa.setFreq(infoLoraModEndForm.getFreq());
                downInfoA_LoRa.setRfch(0);
                downInfoA_LoRa.setPowe(27);
                downInfoA_LoRa.setModu("LORA");
                downInfoA_LoRa.setDatr(infoLoraModEndForm.getDatr_lora());
                downInfoA_LoRa.setCodr(infoLoraModEndForm.getCodr());
                downInfoA_LoRa.setPort(infoLoraModEndForm.getPort());//处理双通道
                downInfoA_LoRa.setIpol(false);
                downInfoA_LoRa.setPrea(8);
                downInfoA_LoRa.setSize(data.length);
                return downInfoA_LoRa;
            } else {
                System.out.println("\n\nJoinRequest!!2\n\n");
                downInfoA_LoRa.setTmst(infoLoraModEndForm.getTmst() + 6000000);
                downInfoA_LoRa.setNcrc(false);
//				downInfoA_LoRa.setFreq(infoLoraModEndForm.getFreq());
                downInfoA_LoRa.setFreq((float) 434.665);
                downInfoA_LoRa.setRfch(infoLoraModEndForm.getRfch());
                downInfoA_LoRa.setRfch(0);
                downInfoA_LoRa.setPowe(27);
                downInfoA_LoRa.setModu("LORA");
//				downInfoA_LoRa.setDatr(infoLoraModEndForm.getDatr_lora());
                downInfoA_LoRa.setDatr("SF12BW125");
                downInfoA_LoRa.setPort(infoLoraModEndForm.getPort());//处理双通道
                downInfoA_LoRa.setCodr(infoLoraModEndForm.getCodr());
                downInfoA_LoRa.setIpol(false);
                downInfoA_LoRa.setPrea(8);//		downInfoA_LoRa.setSize(infoLoraModEndForm.getSize());
                downInfoA_LoRa.setSize(data.length);
                return downInfoA_LoRa;
            }

        } else {
            downInfoA_LoRa.setTmst(infoLoraModEndForm.getTmst() + 1000000);
            downInfoA_LoRa.setNcrc(false);
            downInfoA_LoRa.setFreq(infoLoraModEndForm.getFreq());
            downInfoA_LoRa.setRfch(0);
            downInfoA_LoRa.setPowe(27);
            downInfoA_LoRa.setModu("LORA");
            downInfoA_LoRa.setDatr(infoLoraModEndForm.getDatr_lora());
            downInfoA_LoRa.setPort(infoLoraModEndForm.getPort());//处理双通道
            downInfoA_LoRa.setCodr(infoLoraModEndForm.getCodr());
            downInfoA_LoRa.setIpol(false);
            downInfoA_LoRa.setPrea(8);
            downInfoA_LoRa.setSize(data.length);
            System.out.println("class A down");
//            System.out.println("1秒后打开接收窗口");
//            long SecondSinceEpoch = ((System.currentTimeMillis() + 18000) / 1000 - 315964800);
//            System.out.println("Now GPS time：" + SecondSinceEpoch + "秒");
//            System.out.print("Now GPSFormat time:");
//            DatetimePrint.todateFormat(SecondSinceEpoch * 1000);
//            System.out.println("MOD:" + SecondSinceEpoch % 128);
            return downInfoA_LoRa;
        }
    }

    @Override
    public DownInfoForm ConstructDownInfo(byte[] data, int i) {
        return null;
    }

    public double getTmst() {
        return tmst;
    }


    public void setTmst(double tmst) {
        this.tmst = tmst;
    }


    public boolean isNcrc() {
        return ncrc;
    }


    public void setNcrc(boolean ncrc) {
        this.ncrc = ncrc;
    }


    public int getPrea() {
        return prea;
    }


    public void setPrea(int prea) {
        this.prea = prea;
    }


    public boolean isImme() {
        return imme;
    }

    public void setImme(boolean imme) {
        this.imme = imme;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
