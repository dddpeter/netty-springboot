package com.example.nettyspringboot.config;

import com.example.nettyspringboot.server.netty.NettyServerThread;
import com.example.nettyspringboot.server.netty.NettyServerWithMeta;
import com.example.nettyspringboot.server.socket.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.ServerSocket;


@Configuration
@Slf4j
public class TCPServerConfig {
    @Bean
    public ServerSocket serverSocket() {
        ServerSocket server = null;
        try {
            ServerSocket serverSocket = new ServerSocket();
            server = serverSocket;
            new SocketServer(serverSocket);
        } catch (Exception e) {
            log.info("服务器异常: " + e.getMessage());
        }
        return server;
    }
    @Bean
    public NettyServerThread nettyServerThread() {
        NettyServerWithMeta nettyServerWithMeta = new NettyServerWithMeta("001", "XX银行");
        NettyServerThread thread = new NettyServerThread(nettyServerWithMeta);
        return thread;
    }


}
