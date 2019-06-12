package chapter_2.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SqlSessionFactoryUtil {
    private static SqlSessionFactory sqlSessionFactory = null;
    private static final Class CLASS_LOCk = SqlSessionFactory.class;

    private SqlSessionFactoryUtil(){}

    public static SqlSessionFactory initSqlSessionFactory(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        }catch (IOException ex){
            Logger.getLogger(SqlSessionFactory.class.getName()).log(Level.SEVERE,null, ex);
        }

        synchronized (CLASS_LOCk){
            if(sqlSessionFactory == null){
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession openSqlSession(){
        if(sqlSessionFactory == null){
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
