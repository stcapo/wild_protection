package com.wp.controller;

import com.wp.dao.ConservationNewsDAO;
import com.wp.model.ConservationNews;
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
 * API Servlet for conservation news
 */
@WebServlet("/api/conservation-news")
public class ConservationNewsApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConservationNewsDAO newsDAO;
    
    @Override
    public void init() {
        newsDAO = new ConservationNewsDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String type = request.getParameter("type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            List<ConservationNews> newsList;
            
            if ("featured".equals(type)) {
                // Get featured news for homepage
                String limitStr = request.getParameter("limit");
                int limit = limitStr != null ? Integer.parseInt(limitStr) : 3;
                newsList = newsDAO.getFeaturedNews(limit);
            } else if ("admin".equals(type)) {
                // Get all news including unpublished for admin
                newsList = newsDAO.getAllNewsForAdmin();
            } else {
                // Get all published news
                newsList = newsDAO.getAllNews();
            }
            
            JSONArray jsonArray = new JSONArray();
            
            for (ConservationNews news : newsList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", news.getId());
                jsonObject.put("title", news.getTitle());
                jsonObject.put("content", news.getContent());
                jsonObject.put("summary", news.getSummary());
                jsonObject.put("category", news.getCategory());
                jsonObject.put("badgeColor", news.getBadgeColor());
                jsonObject.put("imageUrl", news.getImageUrl());
                jsonObject.put("author", news.getAuthor());
                jsonObject.put("publishDate", news.getPublishDate());
                jsonObject.put("isFeatured", news.isFeatured());
                jsonObject.put("isPublished", news.isPublished());
                jsonObject.put("viewCount", news.getViewCount());
                jsonObject.put("createdAt", news.getCreatedAt());
                jsonObject.put("updatedAt", news.getUpdatedAt());
                
                jsonArray.put(jsonObject);
            }
            
            JSONObject response_obj = new JSONObject();
            response_obj.put("success", true);
            response_obj.put("data", jsonArray);
            
            out.print(response_obj.toString());
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取保护动态数据失败: " + e.getMessage());
            out.print(errorResponse.toString());
        }
        
        out.flush();
    }
}
