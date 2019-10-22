package jtframework.codec.checkSum.out;

import jtframework.commons.codec.CRC16;
import io.netty.buffer.ByteBuf;

public class OutSignCRC16 implements OutSign {

    @Override
    public ByteBuf sign(ByteBuf buf) {
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        short checkCode = CRC16.doCRC(data);
        buf.writeShort(checkCode);
        return buf;
    }
}
