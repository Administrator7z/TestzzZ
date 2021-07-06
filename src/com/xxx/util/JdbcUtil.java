package com.xxx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JdbcUtil {

    private PreparedStatement car = null;
    private   Connection con  =null;


    //由于tomcat使用自定义类加载加载网站中类文件。但是tomcat提供类加载器不支持SPI机制
    //所以由TOMCAT管理的web应用程序中所有原本由SPI机制自动加载的接口实现类，都需要由
    //开发人员手动加载
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("没有匹配mysql.jar");
        }

    }


    //连接通道创建
    public Connection getCon()throws Exception{

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/com.xxx.test", "root", "123");

        return con;
    }

    //交通工具创建

    public PreparedStatement getCar(String sql)throws Exception{

        car = getCon().prepareStatement(sql);
        return car;
    }

     //推送DML
    public int executeUpdate(String sql)throws Exception{

        return getCar(sql).executeUpdate();
    }

    //推送DQL
    public List executeQuery(String sql,Class classManager)throws Exception{
         ResultSet table = getCar(sql).executeQuery();
         List list =ReflectUtil.convertData(table, classManager);
        close(table);
         return list;
    }


    //资源销毁

    public void close()throws Exception{

         if(car!=null){
             car.close();
         }
         if(con!=null){
             con.close();
         }
    }

    public void close(ResultSet table)throws Exception{
           if(table!=null){
               table.close();
           }
           close();
    }


}
