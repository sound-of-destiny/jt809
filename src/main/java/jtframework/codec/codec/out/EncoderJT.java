package jtframework.codec.codec.out;

import jtframework.annotation.Property;
import jtframework.codec.AbstractMessageCodec;
import jtframework.commons.BCD8421Operator;
import jtframework.commons.BeanUtils;
import jtframework.message.AbstractHeader;
import jtframework.message.PackageData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class EncoderJT extends AbstractMessageCodec implements Encoder {

    @Override
    public ByteBuf encode(PackageData body) {
        ByteBuf bodyBuf = doEncode(Unpooled.directBuffer(256), body);
        AbstractHeader header = body.getHeader();
        header.setMsgBodyLength(bodyBuf.readableBytes());
        ByteBuf headerBuf = doEncode(Unpooled.directBuffer(16), header);
        return Unpooled.wrappedBuffer(headerBuf, bodyBuf);
    }

    private ByteBuf doEncode(ByteBuf buf, Object body) {
        PropertyDescriptor[] pds = getPropertyDescriptor(body.getClass());
        Arrays.asList(pds).forEach(pd -> {
            Method readMethod = pd.getReadMethod();
            Object value = BeanUtils.getValue(body, readMethod);
            if (value != null) {
                Property prop = readMethod.getDeclaredAnnotation(Property.class);
                write(buf, prop, value);
            }
        });
        return buf;
    }

    private void write(ByteBuf buf, Property prop, Object value) {
        int length = prop.length();
        byte pad = prop.pad();

        switch (prop.type()) {
            case BYTE:
                buf.writeByte((int) value);
                break;
            case WORD:
                buf.writeShort((int) value);
                break;
            case DWORD:
                if (value instanceof Long)
                    buf.writeInt(((Long) value).intValue());
                else
                    buf.writeInt((int) value);
                break;
            case BYTES:
                buf.writeBytes((byte[]) value);
                break;
            case BCD8421:
                buf.writeBytes(BCD8421Operator.leftPad(BCD8421Operator.string2Bcd((String) value), length, pad));
                break;
            case STRING:
                byte[] strBytes = ((String) value).getBytes(Charset.forName("GBK"));
                if (length > 0)
                    strBytes = BCD8421Operator.leftPad(strBytes, length, pad);
                buf.writeBytes(strBytes);
                break;
            case OBJ:
                doEncode(buf, value);
                break;
            case LIST:
                List list = (List) value;
                for (Object o : list)
                    doEncode(buf, o);
                break;
        }
    }
}
