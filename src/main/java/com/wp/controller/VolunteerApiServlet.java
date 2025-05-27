package com.wp.controller;

import com.wp.dao.VolunteerActivityDAO;
import com.wp.model.User;
import com.wp.model.VolunteerActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * API Servlet for volunteer activities
 */
@WebServlet("/api/volunteer")
public class VolunteerApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VolunteerActivityDAO volunteerDAO;
    
    @Override
    public void init() {
        volunteerDAO = new VolunteerActivityDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            List<VolunteerActivity> activities = volunteerDAO.getAllActivities();
            JSONArray jsonArray = new JSONArray();
            
            for (VolunteerActivity activity : activities) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", activity.getId());
                jsonObject.put("title", activity.getTitle());
                jsonObject.put("description", activity.getDescription());
                jsonObject.put("activityDate", activity.getActivityDate());
                jsonObject.put("location", activity.getLocation());
                jsonObject.put("maxParticipants", activity.getMaxParticipants());
                jsonObject.put("currentParticipants", activity.getCurrentParticipants());
                jsonObject.put("status", activity.getStatus());
                jsonObject.put("requirements", activity.getRequirements());
                jsonObject.put("contactInfo", activity.getContactInfo());
                jsonObject.put("createdAt", activity.getCreatedAt());
                jsonObject.put("updatedAt", activity.getUpdatedAt());
                
                jsonArray.put(jsonObject);
            }
            
            JSONObject response_obj = new JSONObject();
            response_obj.put("success", true);
            response_obj.put("data", jsonArray);
            
            out.print(response_obj.toString());
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取志愿活动数据失败: " + e.getMessage());
            out.print(errorResponse.toString());
        }
        
        out.flush();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();
        
        try {
            if ("register".equals(action)) {
                handleRegistration(request, jsonResponse);
            } else {
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
    
    private void handleRegistration(HttpServletRequest request, JSONObject jsonResponse) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "请先登录");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        try {
            int activityId = Integer.parseInt(request.getParameter("activityId"));
            String notes = request.getParameter("notes");
            
            boolean success = volunteerDAO.registerForActivity(activityId, user.getId(), notes);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "报名成功！");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "报名失败，可能您已经报名过该活动");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的活动ID");
        }
    }
}
