package jtframework.codec.codec.out;

import jtframework.message.AbstractHeader;
import jtframework.message.PackageData;
import io.netty.buffer.ByteBuf;

public interface Encoder<T extends AbstractHeader> {
    ByteBuf encode(PackageData<T> body);
}
