package com.xxx.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {

    /*
    *  功能：自动生成INSERT语句
    *
    *  分析：INSERT结构
    *
    *  INSERT INTO   表  (字段名1,字段名2,字段名3)  values(值1,值2)
    *  -----------  ---  -----------------   --------------
    *        1       2             3              4
    *
    *  面对问题:
    *         1)如何预测本次操作的表是哪一个
    *               实体类的类名
    *         2)如何预测本次操作的表字段信息
    *               实体类属性名
    *         3)如何得到本次操作关联的数据
    *             实体类实例对象
    *
    *  实体类与表之间关系
    *         1.实体类的类名应该与对应的表文件名相同  dept.frm     Dept.class
    *         2.实体类的属性名应该与表文件字段名相同  deptno int   private Integer deptNo
    *         3.一个实体类的实例对象封装表文件中一行数据
    *
    *  思路:
    */

    public static String  createInsert(Object instance)throws Exception{
        Class classManager;
        Field fieldArray[]=null;
        String tableName=null;
        StringBuffer columns = new StringBuffer(" (");
        StringBuffer values  = new StringBuffer(" VALUES(");
        StringBuffer sql = new StringBuffer("INSERT INTO ");

         classManager =instance.getClass();
        fieldArray =classManager.getDeclaredFields();
        //1.根据实体类的类名得到表名
        tableName =classManager.getSimpleName();
        //2.根据实体类属性名得到赋值的字段名
        //3.从实例对象中属性得到具体赋值内容
        for(Field fieldManager:fieldArray){
             fieldManager.setAccessible(true);
             String fieldName =fieldManager.getName();
             Object value =fieldManager.get(instance);
             if(value!=null){
                 if(!columns.toString().equals(" (")){
                     columns.append(",");
                     values.append(",");
                 }
                 columns.append(fieldName);
                 values.append("'");
                 values.append(value);
                 values.append("'");
             }
        }
        columns.append(")");
        values.append(")");

        //4.装配INSERT语句
        sql.append(tableName);
        sql.append(columns);
        sql.append(values);
        System.out.println("sql = "+sql.toString());
        return sql.toString();
    }

    /*
    *
    * 功能:动态生成一个更新语句
    *
    * 更新语句结构:
    *
    *  UPDATE  表  SET 非主键字段1=值,非主键字段2=值  WHERE 主键字段名=主键值
    *  ------  --  ----------------------------  ---------------------
    *     1    2                  3                     4
    *
    *  面对问题:
    *          1.本次操作表------------------实体类的类名
    *          2.本次需要更新的字段名和内容-----实体类的属性名和属性值
    *          3.主键字段名和主键值-----------手动执行
    *
    *          primaryKey  deptNo  DEPTNO    deptno
    *
    */
    public static String createUpdate(Object obj,String primaryKey)throws Exception{
           Class classManager = null;
           Field fieldArray[]=null;
           StringBuffer columns= new StringBuffer("");
           StringBuffer where = new StringBuffer(" WHERE ");
           StringBuffer sql = new StringBuffer("UPDATE ");
           String tableName = null;

           classManager=obj.getClass();
           fieldArray =classManager.getDeclaredFields();
         //1.获得本次操作的表名称
          tableName =classManager.getSimpleName();

         //2.获得本次需要更新的非主键字段名和更新值
          for(Field fieldManager:fieldArray){
              fieldManager.setAccessible(true);
              String fieldName = fieldManager.getName();
              Object value = fieldManager.get(obj);

              if(value!=null){
                  if(!fieldName.equalsIgnoreCase(primaryKey)){
                      if(!columns.toString().equals("")){
                          columns.append(",");
                      }
                      columns.append(fieldName);
                      columns.append("=");
                      columns.append("'");
                      columns.append(value);
                      columns.append("'");
                  }else{
                      //3.获得主键字段名和主键值
                      where.append(fieldName);
                      where.append("=");
                      where.append("'");
                      where.append(value);
                      where.append("'");
                  }
              }
          }



         //4.装配SQL
        sql.append(tableName);
        sql.append(" SET ") ;
        sql.append(columns);
        sql.append(where);
        System.out.println("sql = "+sql.toString());
        return sql.toString();
    }

    /*
    *  功能：动态将临时表数据行信息保存到对应的实体类的实例对象中
    *
    *
    *
    */
    public static List convertData(ResultSet table,Class classManager)throws Exception{
        ResultSetMetaData rsmd;
        Field fieldArray[]=null;
        List list = new ArrayList();
        //1.获得临时表字段信息
         rsmd = table.getMetaData();
        //2.获得对应的实体类属性信息
        fieldArray=classManager.getDeclaredFields();

        //3.遍历临时表数据行，将数据行字段的值读取出来保存到实体类同名属性
        while(table.next()){
         Object  instance = classManager.newInstance();
         for(int i=1;i<=rsmd.getColumnCount();i++){
                String columnName =rsmd.getColumnName(i);
                String value =table.getString(columnName); //null
                if(value!=null){
                    for(int j=0;j<fieldArray.length;j++){
                        Field fieldManager =   fieldArray[j];
                        String fieldName =fieldManager.getName();
                        String typeName =fieldManager.getType().getSimpleName();
                        if(fieldName.equalsIgnoreCase(columnName)){
                            fieldManager.setAccessible(true);
                            if("String".equalsIgnoreCase(typeName)){
                                fieldManager.set(instance, value);
                            }else if("Integer".equals(typeName)){
                                fieldManager.set(instance, Integer.valueOf(value));
                            }else if("Double".equals(typeName)){
                                fieldManager.set(instance, Double.valueOf(value));
                            }
                            break;
                        }
                    }
                }
         }
         //将赋值完成的实例对象添加到List
            list.add(instance);
        }

        return list;
    }








}
