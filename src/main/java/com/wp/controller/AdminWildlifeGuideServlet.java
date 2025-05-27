package com.wp.controller;

import com.wp.dao.WildlifeGuideDAO;
import com.wp.model.User;
import com.wp.model.WildlifeGuide;
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
 * Admin Servlet for managing wildlife guides
 */
@WebServlet("/admin/wildlife-guides")
public class AdminWildlifeGuideServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private WildlifeGuideDAO guideDAO;
    
    @Override
    public void init() {
        guideDAO = new WildlifeGuideDAO();
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
                    handleAddGuide(request, jsonResponse);
                    break;
                case "update":
                    handleUpdateGuide(request, jsonResponse);
                    break;
                case "delete":
                    handleDeleteGuide(request, jsonResponse);
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
    
    private void handleAddGuide(HttpServletRequest request, JSONObject jsonResponse) {
        WildlifeGuide guide = new WildlifeGuide();
        guide.setTitle(request.getParameter("title"));
        guide.setContent(request.getParameter("content"));
        guide.setCategory(request.getParameter("category"));
        guide.setIcon(request.getParameter("icon"));
        guide.setDisplayOrder(Integer.parseInt(request.getParameter("displayOrder")));
        guide.setActive(Boolean.parseBoolean(request.getParameter("isActive")));
        
        boolean success = guideDAO.addGuide(guide);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "观察指南添加成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "观察指南添加失败");
        }
    }
    
    private void handleUpdateGuide(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            WildlifeGuide guide = new WildlifeGuide();
            guide.setId(Integer.parseInt(request.getParameter("id")));
            guide.setTitle(request.getParameter("title"));
            guide.setContent(request.getParameter("content"));
            guide.setCategory(request.getParameter("category"));
            guide.setIcon(request.getParameter("icon"));
            guide.setDisplayOrder(Integer.parseInt(request.getParameter("displayOrder")));
            guide.setActive(Boolean.parseBoolean(request.getParameter("isActive")));
            
            boolean success = guideDAO.updateGuide(guide);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "观察指南更新成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "观察指南更新失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的指南ID");
        }
    }
    
    private void handleDeleteGuide(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = guideDAO.deleteGuide(id);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "观察指南删除成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "观察指南删除失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的指南ID");
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
