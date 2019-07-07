package com.alibaba.dubbo.performance.demo.agent.pa;

import com.alibaba.dubbo.performance.demo.agent.utils.Registry;
import com.alibaba.dubbo.performance.demo.agent.utils.IpHelper;
import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.Lease;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.options.PutOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ProviderAgentRegistry implements Registry {
    private Logger logger = LoggerFactory.getLogger(ProviderAgentRegistry.class);
    private final String rootPath = "dubbomesh";
    private Lease lease;
    private KV kv;
    private long leaseId;

    public ProviderAgentRegistry() {
        Client client = Client.builder().endpoints(System.getProperty("etcd.url")).build();
        this.lease = client.getLeaseClient();
        this.kv = client.getKVClient();
        try {
            this.leaseId = lease.grant(30).get().getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        keepAlive();

        try {
            int port = Integer.valueOf(System.getProperty("server.port"));
            register("com.alibaba.dubbo.performance.demo.provider.IHelloService", port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        //do nothing
    }

    @Override
    public void find() throws ExecutionException, InterruptedException {
        //do nothing
    }


    private void keepAlive() {
        Executors.newSingleThreadExecutor().submit(
                () -> {
                    try {
                        Lease.KeepAliveListener listener = lease.keepAlive(leaseId);
                        listener.listen();
//                        logger.info("KeepAlive lease:" + leaseId + "; Hex format:" + Long.toHexString(leaseId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void register(String serviceName, int port) throws Exception {
        // 服务注册的key为:    /dubbomesh/com.some.package.IHelloService/192.168.100.100:2000
        String strKey = MessageFormat.format("/{0}/{1}/{2}:{3}", rootPath, serviceName, IpHelper.getHostIp(), String.valueOf(port));
        ByteSequence key = ByteSequence.fromString(strKey);
        ByteSequence val = ByteSequence.fromString("");     // 目前只需要创建这个key,对应的value暂不使用,先留空
        kv.put(key, val, PutOption.newBuilder().withLeaseId(leaseId).build()).get();
        logger.info("Register a new service at:" + strKey);
    }
}
