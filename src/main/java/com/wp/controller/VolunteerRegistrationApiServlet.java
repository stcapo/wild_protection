package com.wp.controller;

import com.wp.dao.VolunteerActivityDAO;
import com.wp.model.VolunteerRegistration;
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
 * API Servlet for volunteer registrations
 */
@WebServlet("/api/volunteer-registrations")
public class VolunteerRegistrationApiServlet extends HttpServlet {
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
            List<VolunteerRegistration> registrations = volunteerDAO.getAllRegistrations();
            JSONArray jsonArray = new JSONArray();
            
            for (VolunteerRegistration registration : registrations) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", registration.getId());
                jsonObject.put("activityId", registration.getActivityId());
                jsonObject.put("userId", registration.getUserId());
                jsonObject.put("registrationDate", registration.getRegistrationDate());
                jsonObject.put("status", registration.getStatus());
                jsonObject.put("notes", registration.getNotes());
                jsonObject.put("activityTitle", registration.getActivityTitle());
                jsonObject.put("activityDate", registration.getActivityDate());
                jsonObject.put("activityLocation", registration.getActivityLocation());
                jsonObject.put("userName", registration.getUserName());
                jsonObject.put("userEmail", registration.getUserEmail());
                
                jsonArray.put(jsonObject);
            }
            
            JSONObject response_obj = new JSONObject();
            response_obj.put("success", true);
            response_obj.put("data", jsonArray);
            
            out.print(response_obj.toString());
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取报名数据失败: " + e.getMessage());
            out.print(errorResponse.toString());
        }
        
        out.flush();
    }
}
