package com.bowen.middleware.pa;

import com.bowen.middleware.utils.IpHelper;
import com.bowen.middleware.utils.Registry;
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
    private final String rooPath = "dubbomesh";
    private Lease lease;
    private KV kv;
    private long leaseId;

    public ProviderAgentRegistry(){
        Client client = Client.builder().endpoints(System.getProperty("etcd.url")).build();
        this.lease = client.getLeaseClient();
        this.kv = client.getKVClient();
        try{
            this.leaseId = lease.grant(30).get().getID();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void start() {

    }

    @Override
    public void find()throws ExecutionException,InterruptedException {

    }

    private void keepAlive(){
        Executors.newSingleThreadExecutor().submit(
                () ->{
                    try {
                        Lease.KeepAliveListener listener = lease.keepAlive(leaseId);
                        listener.listen();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );
    }

    private void register(String serviceName,int port)throws Exception{

        String strkey = MessageFormat.format("{0}/{1}/{2}:{3}",rooPath,serviceName,IpHelper.getHostIp(),String.valueOf(port));
        ByteSequence key = ByteSequence.fromString(strkey);
        ByteSequence val = ByteSequence.fromString("");
        kv.put(key,val,PutOption.newBuilder().withLeaseId(leaseId).build()).get();
        logger.info("Register a new service at:" + strkey);
    }
}
