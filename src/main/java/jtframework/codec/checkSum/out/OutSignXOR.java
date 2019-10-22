package jtframework.codec.checkSum.out;

import jtframework.commons.codec.XOR;
import io.netty.buffer.ByteBuf;

public class OutSignXOR implements OutSign {

    @Override
    public ByteBuf sign(ByteBuf buf) {
        byte checkCode = XOR.xor(buf);
        buf.writeByte(checkCode);
        return buf;
    }
}
