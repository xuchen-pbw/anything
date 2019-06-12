package chapter_1;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BaseTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.set("key","hello-world");
        System.out.println(jedis.get("key"));

        jedis.lpush("list-key","item");
        jedis.lpush("list-key","item");
        jedis.lpush("list-key","item0");
        jedis.lpush("list-key","item0");
        List<String> list = jedis.lrange("list-key",0,-1);

        for(String s : list){
            System.out.println(s);
        }
/*        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }*/
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }


    }
}
