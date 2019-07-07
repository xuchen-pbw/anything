package com.alibaba.dubbo.performance.demo.agent.pa.PAClient;

import com.alibaba.dubbo.performance.demo.agent.utils.Client;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class Clients {
    private static AtomicInteger count = new AtomicInteger(0);
    private static Client client;
    private static ArrayList<Channel> server = new ArrayList<>(8);

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Clients.client = client;
    }

    public static Channel getServer() {
        return server.get(count.getAndIncrement() & 7);
    }

    public synchronized static void setServer(Channel server) {
        Clients.server.add(server);
    }
}
