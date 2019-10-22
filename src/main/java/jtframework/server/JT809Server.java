package jtframework.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import jtframework.server.initializer.ProtoBufClientInitializer;
import jtframework.server.initializer.UpPlatformInitializer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class JT809Server {

    private void connectJT808() {
        EpollEventLoopGroup group = new EpollEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(EpollServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ProtoBufClientInitializer());
            ChannelFuture future = b.connect("127.0.0.1", 10007).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JT809上行链路启动失败");
        } finally {
            group.shutdownGracefully();
        }
    }

    private void bindUpPlatform() {
        EpollEventLoopGroup bossGroup = new EpollEventLoopGroup();
        EpollEventLoopGroup workerGroup = new EpollEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(EpollServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ProtoBufClientInitializer());
            ChannelFuture future = b.bind(10007).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JT809下行链路启动失败");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void connectUpPlatform() {
        EpollEventLoopGroup group = new EpollEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(EpollSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .handler(new UpPlatformInitializer());
            ChannelFuture future = b.connect("127.0.0.1", 10008).sync();
            future.channel().closeFuture().sync();
            /*future.awaitUninterruptibly();
            future.addListener((ChannelFutureListener) futureListener -> futureListener.channel().eventLoop().schedule(() -> {
                log.info("连接成功");
                if (futureListener.isSuccess()) {
                    JT809Client.getInstance().setChannel(futureListener.channel());
                    // sendLoginRequest("127.0.0.1", 10008, 1234, "12345678", futureListener.channel());
                    log.info("连接成功");
                    System.out.println("连接成功");
                } else {
                    log.info("连接失败");
                }
            }, 3, TimeUnit.SECONDS));*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
    private final ExecutorService executorService = new ThreadPoolExecutor(3, 3,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.DiscardPolicy());

    synchronized void startServer() {
        executorService.submit(this::connectJT808);
        executorService.submit(this::connectUpPlatform);
        executorService.submit(this::bindUpPlatform);
    }

    private synchronized void stopServer() {
        executorService.shutdown();
    }
}
