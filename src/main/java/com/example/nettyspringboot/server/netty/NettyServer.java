package com.example.nettyspringboot.server.netty;

import java.net.InetSocketAddress;

public interface NettyServer {
    void start(InetSocketAddress socketAddress);
}
