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

@WebServlet(name = "EmpUpdateServlet",value = "/emp/update")
public class EmpUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int result =0;
        String empNo,ename,sal;
        EmpDao dao = new EmpDao();
        Emp emp = null;
        PrintWriter out = null;
        empNo = request.getParameter("empNo");
        ename = request.getParameter("ename");
        sal = request.getParameter("sal");
        emp = new Emp(Integer.valueOf(empNo),ename,Double.valueOf(sal));
        try {
            result = dao.update(emp);
        } catch (Exception e) {
            e.printStackTrace();
            result=-1;
        }
        response.setContentType("text/html;charset=utf-8");
        out = response.getWriter();
        if(result ==1){
            out.print("<font style='color:red;font-size:40px'>成功</font>");
        }else{
            out.print("<font style='color:red;font-size:40px'>失败</font>");
        }
    }
}
