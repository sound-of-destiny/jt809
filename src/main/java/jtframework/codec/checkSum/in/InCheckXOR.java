package jtframework.codec.checkSum.in;

import jtframework.commons.codec.XOR;
import io.netty.buffer.ByteBuf;

public class InCheckXOR implements InCheck {

    @Override
    public boolean check(ByteBuf buf) {
        byte checkCode = buf.getByte(buf.readableBytes() - 1);
        buf = buf.slice(0, buf.readableBytes() - 1);
        byte calculatedCheckCode = XOR.xor(buf);

        return checkCode != calculatedCheckCode;
    }
}
