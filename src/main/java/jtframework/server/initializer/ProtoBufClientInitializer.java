package jtframework.server.initializer;

import cn.edu.sdu.jt809.service.handler.JT809ToJT808Handler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ProtoBufClientInitializer extends ChannelInitializer<SocketChannel> implements ClientInitializer {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("ProtobufVarint32FrameDecoder", new ProtobufVarint32FrameDecoder());
        // pipeline.addLast("protobufDecoder", new ProtobufDecoder(ClientData.Protocol.getDefaultInstance()));
        pipeline.addLast("ProtobufVarint32LengthFieldPrepender", new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());

        pipeline.addLast("clientHandler", new JT809ToJT808Handler());
    }
}
