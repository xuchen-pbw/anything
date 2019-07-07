package com.alibaba.dubbo.performance.demo.agent.ca.CAClient;



import com.alibaba.dubbo.performance.demo.agent.utils.Client;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public  class Clients {


    /**
     * 记录服务提供者
     */
    private static final ArrayList<Client> holder = new ArrayList<>(3);

    /**
     * 对应服务提供者 未返回结果的任务数量（作为负载均衡依据）
     */
    private static final ArrayList<AtomicInteger> remain = new ArrayList<>(3);

    private Clients() {
    }

    /**
     * 通知 该任务执行者完成了一个任务，任务计数减一
     *
     * @param client 任务执行者
     */
    public static void RequestComplete(Client client) {
        for (int i = 0; i < holder.size(); i++) {
            if (client.equals(holder.get(i))) {
                remain.get(i).getAndDecrement();
                return;
            }
        }
    }

    public static void addClient(Client client) {
        holder.add(client);
        remain.add(new AtomicInteger(0));
    }

    public static Client next() {
        int candidate = 0;
        assert holder.size() == 3;
        for (int i = 1; i < holder.size(); i++) {
            candidate = remain.get(i).get() < remain.get(candidate).get() ? i : candidate;
        }
        remain.get(candidate).incrementAndGet();
        return holder.get(candidate);
    }
}
