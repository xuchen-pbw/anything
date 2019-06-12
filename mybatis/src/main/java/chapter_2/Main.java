package chapter_2;

import chapter_2.mapper.RoleMapper;
import chapter_2.po.Role;
import chapter_2.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args)throws IOException {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionFactoryUtil.openSqlSession();
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setRoleName("testName");
            role.setNote("testNote");
            roleMapper.insertRole(role);
            roleMapper.deleteRole(1L);
            sqlSession.commit();
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            sqlSession.rollback();
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }
}
