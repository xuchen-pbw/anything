package com.alibaba.dubbo.performance.demo.agent.ca;

import com.alibaba.dubbo.performance.demo.agent.ca.CAClient.ConsumerAgentClient;
import com.alibaba.dubbo.performance.demo.agent.utils.Registry;
import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.Lease;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.options.GetOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ConsumerAgentRegistry implements Registry {
    private Logger logger = LoggerFactory.getLogger(ConsumerAgentRegistry.class);
    private final String rootPath = "dubbomesh";
    private Lease lease;
    private KV kv;
    private long leaseId;

    public ConsumerAgentRegistry() {
        Client client = Client.builder().endpoints(System.getProperty("etcd.url")).build();
        this.lease = client.getLeaseClient();
        this.kv = client.getKVClient();
        try {
            this.leaseId = lease.grant(30).get().getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        keepAlive();
        logger.info("ConsumerAgentRegistry established");
    }

    @Override
    public void start() {
        try {
            find();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Consumer Agent 服务发现
     */
    @Override
    public void find() throws ExecutionException, InterruptedException {
        String strKey = MessageFormat.format("/{0}/{1}", rootPath, "com.alibaba.dubbo.performance.demo.provider.IHelloService");
        ByteSequence key = ByteSequence.fromString(strKey);
        GetResponse response = kv.get(key, GetOption.newBuilder().withPrefix(key).build()).get();


        for (com.coreos.jetcd.data.KeyValue kv : response.getKvs()) {
            String s = kv.getKey().toStringUtf8();
            int index = s.lastIndexOf("/");
            String endpointStr = s.substring(index + 1, s.length());

            String host = endpointStr.split(":")[0];
            int port = Integer.valueOf(endpointStr.split(":")[1]);

            new ConsumerAgentClient(host, port);
            logger.info("found provider : " + host + ":" + port);
        }

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
}
