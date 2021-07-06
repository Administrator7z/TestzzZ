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

@WebServlet(name = "EmpFindByIdServlet",value =  "/emp/findById")
public class EmpFindByIdServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empNo = null;
        EmpDao dao = new EmpDao();
        Emp emp = null;
        PrintWriter out = null;

        empNo = request.getParameter("empNo");

        try {
            emp = dao.findById(empNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");
        out = response.getWriter();
        out.print("<form action='/myWeb/emp/update'>");
        out.print("<table border=2 align=center>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td><input type='text' readOnly name='empNo' value='" + emp.getEmpno() + "'></td>");
        out.print("</tr>");

        out.print("<tr>");
        out.print("<td>名称</td>");
        out.print("<td><input type='text' name='ename' value='" + emp.getEname() + "'></td>");


        out.print("</tr>");

        out.print("<tr>");
        out.print("<td>奖金</td>");
        out.print("<td><input type='text' name='sal' value='" + emp.getSal() + "'></td>");
        out.print("</tr>");

        out.print("<tr>");
        out.print("<td><input type='submit' value='更新部门'></td>");
        out.print("<td><input type='reset' value='重置'></td>");
        out.print("</tr>");
        out.print("</table>");
        out.print("</form>");
    }
}
