package com.wp.controller;

import com.wp.dao.WildlifeGuideDAO;
import com.wp.model.WildlifeGuide;
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
 * API Servlet for wildlife observation guides
 */
@WebServlet("/api/wildlife-guides")
public class WildlifeGuideApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private WildlifeGuideDAO guideDAO;
    
    @Override
    public void init() {
        guideDAO = new WildlifeGuideDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            List<WildlifeGuide> guides = guideDAO.getAllGuides();
            JSONArray jsonArray = new JSONArray();
            
            for (WildlifeGuide guide : guides) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", guide.getId());
                jsonObject.put("title", guide.getTitle());
                jsonObject.put("content", guide.getContent());
                jsonObject.put("category", guide.getCategory());
                jsonObject.put("icon", guide.getIcon());
                jsonObject.put("displayOrder", guide.getDisplayOrder());
                jsonObject.put("isActive", guide.isActive());
                jsonObject.put("createdAt", guide.getCreatedAt());
                jsonObject.put("updatedAt", guide.getUpdatedAt());
                
                jsonArray.put(jsonObject);
            }
            
            JSONObject response_obj = new JSONObject();
            response_obj.put("success", true);
            response_obj.put("data", jsonArray);
            
            out.print(response_obj.toString());
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取观察指南数据失败: " + e.getMessage());
            out.print(errorResponse.toString());
        }
        
        out.flush();
    }
}
