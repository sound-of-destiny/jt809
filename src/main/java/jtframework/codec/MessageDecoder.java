package jtframework.codec;

import jtframework.codec.checkSum.in.InCheck;
import jtframework.codec.codec.in.Decoder;
import jtframework.codec.transfer.in.UnTransfer;
import jtframework.exception.CheckSumErrorException;
import jtframework.message.AbstractHeader;
import jtframework.message.PackageData;
import io.netty.buffer.ByteBuf;

public abstract class MessageDecoder {

    protected InCheck inCheck;
    protected Decoder decoder;
    protected UnTransfer unTransfer;

    public MessageDecoder() {}

    /**
     * 获取消息类型
     */
    public abstract int getMsgId(ByteBuf buf);

    /**
     * 反转义
     */
    private ByteBuf unEscape(ByteBuf buf) {
        return unTransfer.unEscape(buf);
    }

    public void setUnTransfer(UnTransfer unTransfer) {
        this.unTransfer = unTransfer;
    }

    /**
     * 校验
     */
    private boolean check(ByteBuf buf) {
        return inCheck.check(buf);
    }

    public void setCheck(InCheck inCheck) {
        this.inCheck = inCheck;
    }

    /**
     * 解析
     */
    public <T extends PackageData> T decode(ByteBuf buf, Class<? extends AbstractHeader> headerClass, Class<T> bodyClass) {
        buf = unEscape(buf);
        if (check(buf)) throw new CheckSumErrorException("checksum error");
        return decoder.decode(buf, headerClass, bodyClass);
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }
}
