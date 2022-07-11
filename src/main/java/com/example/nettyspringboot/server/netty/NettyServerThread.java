package com.example.nettyspringboot.server.netty;

import java.net.InetSocketAddress;

public class NettyServerThread  extends Thread  {
    private NettyServerWithMeta nettyServerWithMeta;
    public NettyServerThread(NettyServerWithMeta nettyServerWithMeta){
        this.nettyServerWithMeta = nettyServerWithMeta;
        this.start();
    }

    @Override
    public void run() {
        NettyServer server = nettyServerWithMeta.getNettyServer();
        server.start(new InetSocketAddress(8090));
    }
}
