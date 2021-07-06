package com.xxx.controller;

import com.xxx.pojo.EmpDao;
import com.xxx.entify.Emp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EmpAddServlet",value = "/emp/add")
public class EmpAddServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int result = 0;
        String  empno,ename,job,mgr,hiredate,sal,comm,deptno;
        empno = request.getParameter("empno");
        ename = request.getParameter("ename");
        job = request.getParameter("job");
        mgr = request.getParameter("mgr");
        hiredate = request.getParameter("hiredate");
        sal = request.getParameter("sal");
        comm = request.getParameter("comm");
        deptno = request.getParameter("deptno");
        Emp emp= new Emp(Integer.valueOf(empno),ename,job,Integer.valueOf(mgr),hiredate,Double.valueOf(sal),Double.valueOf(comm),Integer.valueOf(deptno));
        EmpDao dao = new EmpDao();
        try {
            result = dao.empAdd(emp);
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        if (result == 1 ){
            writer.print("<font style='color:red;font-size:40px'>添加成功</font>");
        }else {
            writer.print("<font style='color:red;font-size:40px'>添加失败</font>");
        }

    }
}
