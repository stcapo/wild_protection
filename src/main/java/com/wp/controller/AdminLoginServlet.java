package com.wp.controller;

import com.wp.dao.UserDAO;
import com.wp.model.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet for handling admin login
 */
@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        
        User user = userDAO.validateUser(username, password);
        
        if (user != null && "admin".equals(user.getRole())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", true);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            
            jsonResponse.put("success", true);
            jsonResponse.put("message", "管理员登录成功");
            jsonResponse.put("redirect", "admin-dashboard.html");
        } else if (user != null && !"admin".equals(user.getRole())) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "您不是管理员，请使用普通用户登录");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "管理员账号或密码错误");
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}
