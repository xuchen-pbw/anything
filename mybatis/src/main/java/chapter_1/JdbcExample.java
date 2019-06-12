package chapter_1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcExample {
    private Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mybatis?......";
            String user = "root";
            String password = "bowen";
            connection = DriverManager.getConnection(url,user,password);
        }catch (Exception ex){
            Logger.getLogger(JdbcExample.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
        return connection;
    }

    private void close(ResultSet rs, Statement stmt, Connection connection){
        try{
            if(rs != null /*&& !rs.isClosed()*/ ){
                rs.close();
            }
        }catch (SQLException ex){
            Logger.getLogger(JdbcExample.class.getName()).log(Level.SEVERE,null,ex);
        }

        try{
            if(stmt != null /*&& !rs.isClosed()*/ ){
                stmt.close();
            }
        }catch (SQLException ex){
            Logger.getLogger(JdbcExample.class.getName()).log(Level.SEVERE,null,ex);
        }

        try{
            if(connection != null /*&& !rs.isClosed()*/ ){
                connection.close();
            }
        }catch (SQLException ex){
            Logger.getLogger(JdbcExample.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


}
