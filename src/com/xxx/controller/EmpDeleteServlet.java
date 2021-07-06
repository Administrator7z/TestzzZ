package com.xxx.controller;

import com.xxx.pojo.EmpDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EmpDeleteServlet",value = "/emp/delete")
public class EmpDeleteServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String empNo = null;
        EmpDao dao = new EmpDao();
        PrintWriter out = null;
        int result = 0;
        empNo = request.getParameter("empNo");
        try {
            result = dao.empDelete(empNo);
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        response.setContentType("text/html;charset=utf-8");
        out = response.getWriter();
        if (result == 1) {
            out.print("<font style='color:red;font-size:40px'>删除成功</font>");
        } else {
            out.print("<font style='color:red;font-size:40px'>删除失败</font>");
        }

    }
}
