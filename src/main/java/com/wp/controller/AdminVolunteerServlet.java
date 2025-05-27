package com.wp.controller;

import com.wp.dao.VolunteerActivityDAO;
import com.wp.model.User;
import com.wp.model.VolunteerActivity;
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
 * Admin Servlet for managing volunteer activities
 */
@WebServlet("/admin/volunteer")
public class AdminVolunteerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VolunteerActivityDAO volunteerDAO;
    
    @Override
    public void init() {
        volunteerDAO = new VolunteerActivityDAO();
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
                    handleAddActivity(request, jsonResponse);
                    break;
                case "update":
                    handleUpdateActivity(request, jsonResponse);
                    break;
                case "delete":
                    handleDeleteActivity(request, jsonResponse);
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
    
    private void handleAddActivity(HttpServletRequest request, JSONObject jsonResponse) {
        VolunteerActivity activity = new VolunteerActivity();
        activity.setTitle(request.getParameter("title"));
        activity.setDescription(request.getParameter("description"));
        activity.setActivityDate(request.getParameter("activityDate"));
        activity.setLocation(request.getParameter("location"));
        activity.setMaxParticipants(Integer.parseInt(request.getParameter("maxParticipants")));
        activity.setRequirements(request.getParameter("requirements"));
        activity.setContactInfo(request.getParameter("contactInfo"));
        activity.setStatus(request.getParameter("status"));
        
        boolean success = volunteerDAO.addActivity(activity);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "活动添加成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "活动添加失败");
        }
    }
    
    private void handleUpdateActivity(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            VolunteerActivity activity = new VolunteerActivity();
            activity.setId(Integer.parseInt(request.getParameter("id")));
            activity.setTitle(request.getParameter("title"));
            activity.setDescription(request.getParameter("description"));
            activity.setActivityDate(request.getParameter("activityDate"));
            activity.setLocation(request.getParameter("location"));
            activity.setMaxParticipants(Integer.parseInt(request.getParameter("maxParticipants")));
            activity.setRequirements(request.getParameter("requirements"));
            activity.setContactInfo(request.getParameter("contactInfo"));
            activity.setStatus(request.getParameter("status"));
            
            boolean success = volunteerDAO.updateActivity(activity);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "活动更新成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "活动更新失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的活动ID");
        }
    }
    
    private void handleDeleteActivity(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = volunteerDAO.deleteActivity(id);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "活动删除成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "活动删除失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的活动ID");
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
