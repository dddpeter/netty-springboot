package com.example.nettyspringboot.server.netty;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class NettyServerWithMeta {
    public String getChannelCode() {
        return channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }

    private String channelCode;
    private String channelName;
    private NettyServer nettyServer;

    /**
     *
     * @param channelCode
     * @param channelName
     */
    public NettyServerWithMeta(String channelCode,String channelName){
        this.channelCode = channelCode;
        this.channelName = channelName;
        log.info("渠道代码:{},渠道名称:{}",channelCode,channelName);
        this.nettyServer  =new NettyServerImpl();
    }
}
