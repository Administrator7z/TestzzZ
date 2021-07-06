package com.xxx.pojo;


import com.xxx.entify.Emp;
import com.xxx.util.ReflectUtil;
import com.xxx.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class EmpDao {
    JdbcUtil util = new JdbcUtil();

    public int empAdd(Emp emp) throws Exception {
        String sql = null;
        int result = 0;
        sql = ReflectUtil.createInsert(emp);
        try {
            result = util.executeUpdate(sql);
        } finally {
            util.close();
        }

        return result;
    }
    public List<Emp> findAll() throws Exception{
        String sql = "select * from emp";
        return util.executeQuery(sql, Emp.class);

    }
    public int empDelete(String deptno) throws Exception{
        String sql = "delete from emp where empno = ?";
        PreparedStatement car =null;
        int result = 0;
        try {
            car = util.getCar(sql);
            car.setString(1,deptno);
            result = car.executeUpdate();
        } finally {
            util.close();
        }
        return result;
    }
    public Emp findById(String empno) throws Exception{
        String sql ="select * from emp where empno=?";
        PreparedStatement car = null;
        ResultSet table = null;
        List<Emp> deptList;
        try {
            car = util.getCar(sql);
            car.setString(1, empno);
            table = car.executeQuery();
            deptList = ReflectUtil.convertData(table, Emp.class);
        } finally {
            util.close(table);
        }

        return deptList.get(0);
    }
    public int update (Emp emp) throws Exception{

            String sql = null;
            int result = 0;
            sql = ReflectUtil.createUpdate(emp, "empNo");
            try {
                result = util.executeUpdate(sql);
            } finally {
                util.close();
            }
            return result;

    }
}
