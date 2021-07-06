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
import java.util.List;

@WebServlet(name = "EmpSelectServlet", value =  "/emp/findAll")
public class EmpFindAllServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpDao dao  = new EmpDao();
        List<Emp> all = null;
        try {
            all = dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<table border='2' align='center'>");
        out.print("<tr>");
        out.print("<td>编号</td>");
        out.print("<td>名称</td>");
        out.print("<td>职位</td>");
        out.print("<td>上级编号</td>");
        out.print("<td>入职时间</td>");
        out.print("<td>工资</td>");
        out.print("<td>奖金</td>");
        out.print("<td>部门编号</td>");
        out.print("<td colspan=2 align=center>操作</td>");
        out.print("</tr>");
        if (all != null && all.size() > 0) {

            for (Emp emp : all) {

                out.print("<tr>");
                out.print("<td>" + emp.getEmpno() + "</td>");
                out.print("<td>" + emp.getEname()+ "</td>");
                out.print("<td>" + emp.getJob()+ "</td>");
                out.print("<td>" + emp.getMgr()+ "</td>");  
                out.print("<td>" + emp.getHiredate()+ "</td>");
                out.print("<td>" + emp.getSal()+ "</td>");
                out.print("<td>" + emp.getComm()+ "</td>");
                out.print("<td>" + emp.getDeptno()+ "</td>");

                out.print("<td><a href='/myWeb/emp/delete?empNo=" + emp.getEmpno()+ "'>删除部门</a></td>");
                out.print("<td><a href='/myWeb/emp/findById?empNo=" + emp.getEmpno() + "'>详细信息</a></td>");
                out.print("</tr>");
            }
        }
        out.print("</table>");
    }
}
