package jtframework.codec.codec.in;

import jtframework.message.AbstractHeader;
import jtframework.message.PackageData;
import io.netty.buffer.ByteBuf;

public interface Decoder {
    <T extends PackageData> T decode(ByteBuf buf, Class<? extends AbstractHeader> headerClass, Class<T> bodyClass);
}
