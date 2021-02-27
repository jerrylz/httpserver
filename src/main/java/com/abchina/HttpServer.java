package com.abchina;

import com.abchina.handler.HttpRequestHandler;
import com.abchina.handler.RequestDecoderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpResponseEncoder;

import javax.servlet.http.HttpServlet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author jerrylz
 * @date 2021/2/26
 */
public class HttpServer {

    private int port = 8090;
    private Map<String, HttpServlet> servletMapping = new HashMap<>();
    private Properties webxml = new Properties();

    private void init() {
        try {
            //初始化 读取配置
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");
            webxml.load(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        doStart();
    }

    public void start(int port) {
        this.port = port;
        doStart();
    }

    private void doStart() {
        init();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        try {
            server.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    //客户端连接时启动
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel client) throws Exception {
                            //响应编码器
                            client.pipeline().addLast(new HttpResponseEncoder());
                            //请求解码器
                            client.pipeline().addLast(new RequestDecoderHandler());
                            //自定义处理器
                            client.pipeline().addLast(new HttpRequestHandler());

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = server.bind(port).sync();
            System.out.println("BoTomcat 已启动!");
            //监听关闭状态启动
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //关闭线程池
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new HttpServer().start();
    }
}
