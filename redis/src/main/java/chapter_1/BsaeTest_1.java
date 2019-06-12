package chapter_1;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BsaeTest_1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);

        User u1 = new User(UUID.randomUUID().hashCode(),"jack1",21,"m");
        User u2 = new User(UUID.randomUUID().hashCode(),"jack2",22,"m");
        User u3 = new User(UUID.randomUUID().hashCode(),"jack3",23,"m");
        User u4 = new User(UUID.randomUUID().hashCode(),"jack4",24,"m");
        User u5 = new User(UUID.randomUUID().hashCode(),"jack5",25,"m");

        Map<String,String> map = new HashMap<String ,String>();
        map.put("u1",JSON.toJSONString(u1));
        map.put("u2",JSON.toJSONString(u2));
        map.put("u3",JSON.toJSONString(u3));
        map.put("u4",JSON.toJSONString(u4));
        map.put("u5",JSON.toJSONString(u5));

        jedis.hmset("t_user",map);

    }
}
