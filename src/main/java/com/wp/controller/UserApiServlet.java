package com.wp.controller;

import com.wp.dao.UserDAO;
import com.wp.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * API Servlet for user data
 */
@WebServlet("/api/users")
public class UserApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            List<User> userList = userDAO.getAllUsers();
            JSONArray jsonArray = new JSONArray();
            
            for (User user : userList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", user.getId());
                jsonObject.put("username", user.getUsername());
                // 不返回密码字段以保护安全
                jsonObject.put("email", user.getEmail());
                jsonObject.put("fullName", user.getFullName());
                jsonObject.put("role", user.getRole());
                jsonObject.put("createdAt", user.getCreatedAt());
                
                jsonArray.put(jsonObject);
            }
            
            JSONObject response_obj = new JSONObject();
            response_obj.put("success", true);
            response_obj.put("data", jsonArray);
            
            out.print(response_obj.toString());
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取用户数据失败: " + e.getMessage());
            out.print(errorResponse.toString());
        }
        
        out.flush();
    }
}
