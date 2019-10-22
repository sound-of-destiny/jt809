package jtframework.codec.transfer.out;

import io.netty.buffer.ByteBuf;

public interface Transfer {
    ByteBuf escape(ByteBuf buf);
}
