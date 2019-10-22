package cn.edu.sdu.jt809.protocol;

import jtframework.message.AbstractHeader;

import java.util.Arrays;

public class MsgHeader extends AbstractHeader {
    // 数据长度(包括头标识, 数据头, 数据体和尾标识)
    private int msgLength;
    // 报文序列号
    private int msgSN;
    // 业务数据类型
    private Integer type;
    // 下级平台接入码， 上级平台给下级平台分配的唯一标识号
    private int msgGNSSCenterId;
    // 协议版本号标识
    private byte[] versionFlag;
    // 报文加密标识位
    private byte encryptFlag;
    // 数据加密的秘钥
    private int encryptKey;

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }

    public int getMsgSN() {
        return msgSN;
    }

    public void setMsgSN(int msgSN) {
        this.msgSN = msgSN;
    }

    public int getMsgGNSSCenterId() {
        return msgGNSSCenterId;
    }

    public void setMsgGNSSCenterId(int msgGNSSCenterId) {
        this.msgGNSSCenterId = msgGNSSCenterId;
    }

    public byte[] getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }

    public byte getEncryptFlag() {
        return encryptFlag;
    }

    public void setEncryptFlag(byte encryptFlag) {
        this.encryptFlag = encryptFlag;
    }

    public int getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(int encryptKey) {
        this.encryptKey = encryptKey;
    }

    @Override
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getHeaderLength() {
        return null;
    }

    @Override
    public Integer getMsgBodyLength() {
        return null;
    }

    @Override
    public void setMsgBodyLength(Integer bodyLength) {

    }
}
