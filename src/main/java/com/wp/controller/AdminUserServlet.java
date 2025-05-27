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
 * Admin Servlet for managing users
 */
@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check admin permission
        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }
        
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        
        try {
            switch (action) {
                case "add":
                    handleAddUser(request, jsonResponse);
                    break;
                case "update":
                    handleUpdateUser(request, jsonResponse);
                    break;
                case "delete":
                    handleDeleteUser(request, jsonResponse);
                    break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "无效的操作");
            }
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "操作失败: " + e.getMessage());
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
    
    private void handleAddUser(HttpServletRequest request, JSONObject jsonResponse) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");
        
        // 验证必填字段
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "请填写所有必填字段");
            return;
        }
        
        // 检查用户名是否已存在
        if (userDAO.usernameExists(username)) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "用户名已存在");
            return;
        }
        
        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password);
        user.setEmail(email.trim());
        user.setFullName(fullName.trim());
        user.setRole(role != null ? role : "user");
        
        boolean success = userDAO.addUser(user);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "用户添加成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "用户添加失败");
        }
    }
    
    private void handleUpdateUser(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String role = request.getParameter("role");
            
            // 验证必填字段
            if (username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "请填写所有必填字段");
                return;
            }
            
            // 检查用户名是否已被其他用户使用
            if (userDAO.usernameExists(username, id)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "用户名已被其他用户使用");
                return;
            }
            
            // 获取现有用户信息
            User existingUser = userDAO.getUserById(id);
            if (existingUser == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "用户不存在");
                return;
            }
            
            User user = new User();
            user.setId(id);
            user.setUsername(username.trim());
            // 如果密码为空，保持原密码
            user.setPassword(password != null && !password.trim().isEmpty() ? password : existingUser.getPassword());
            user.setEmail(email.trim());
            user.setFullName(fullName.trim());
            user.setRole(role != null ? role : "user");
            
            boolean success = userDAO.updateUser(user);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "用户更新成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "用户更新失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的用户ID");
        }
    }
    
    private void handleDeleteUser(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            // 防止删除当前登录的管理员
            HttpSession session = request.getSession(false);
            if (session != null) {
                User currentUser = (User) session.getAttribute("user");
                if (currentUser != null && currentUser.getId() == id) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "不能删除当前登录的用户");
                    return;
                }
            }
            
            boolean success = userDAO.deleteUser(id);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "用户删除成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "用户删除失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的用户ID");
        }
    }
    
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            return user != null && "admin".equals(user.getRole());
        }
        return false;
    }
}
