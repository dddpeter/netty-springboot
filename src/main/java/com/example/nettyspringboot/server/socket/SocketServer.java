package com.example.nettyspringboot.server.socket;

import com.example.nettyspringboot.consant.GlobalConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class SocketServer extends  Thread{
    private ServerSocket serverSocket;
    // 核心线程池大小
    private int corePoolSize = GlobalConstants.SERVER_THREAD_COUNT;

    // 最大可创建的线程数
    private int maxPoolSize = GlobalConstants.SERVER_THREAD_COUNT;

    // 队列最大长度
    private int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 300;
    private  ThreadPoolTaskExecutor executor;
    public SocketServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        this.start();
    }

    @SneakyThrows
    @Override
    public void run() {
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8091));
        log.info("服务器启动开始监听端口: {}", 8091);
        while (true) {
            Socket socket = serverSocket.accept();
            executor.submit(() -> {
                try {
                    // 读取客户端数据
                    InputStream input = socket.getInputStream();
                    StringBuffer sb = new StringBuffer();
                    byte[] temp = new  byte[1024];
                    while(input.available()>0){
                        int i=  input.read(temp,0,1024);
                        if(i<0){
                            break;
                        }
                        sb.append(new String(temp,0,i));
                    }
                    String clientInputStr = sb.toString();
                    // 处理客户端数据
                    log.info("客户端发过来的内容:" + clientInputStr);
                    // 模拟的业务逻辑处理
                    Thread.sleep(600);
                    // 向客户端回复信息
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.write("hello");
                    out.flush();
                    out.close();
                    input.close();
                } catch (Exception e) {
                    log.error("服务器 run 异常: " ,e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (Exception e) {
                            log.error("服务端 finally 异常:" , e);
                        }
                    }
                }
            });
        }
    }
}
