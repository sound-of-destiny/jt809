package jtframework.codec.checkSum.out;

import io.netty.buffer.ByteBuf;

public interface OutSign {
    /**
     * checksum
     */
    ByteBuf sign(ByteBuf buf);
}
