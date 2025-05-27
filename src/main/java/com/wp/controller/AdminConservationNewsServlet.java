package com.wp.controller;

import com.wp.dao.ConservationNewsDAO;
import com.wp.model.ConservationNews;
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
 * Admin Servlet for managing conservation news
 */
@WebServlet("/admin/conservation-news")
public class AdminConservationNewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConservationNewsDAO newsDAO;
    
    @Override
    public void init() {
        newsDAO = new ConservationNewsDAO();
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
                    handleAddNews(request, jsonResponse);
                    break;
                case "update":
                    handleUpdateNews(request, jsonResponse);
                    break;
                case "delete":
                    handleDeleteNews(request, jsonResponse);
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
    
    private void handleAddNews(HttpServletRequest request, JSONObject jsonResponse) {
        ConservationNews news = new ConservationNews();
        news.setTitle(request.getParameter("title"));
        news.setContent(request.getParameter("content"));
        news.setSummary(request.getParameter("summary"));
        news.setCategory(request.getParameter("category"));
        news.setBadgeColor(request.getParameter("badgeColor"));
        news.setImageUrl(request.getParameter("imageUrl"));
        news.setAuthor(request.getParameter("author"));
        news.setPublishDate(request.getParameter("publishDate"));
        news.setFeatured(Boolean.parseBoolean(request.getParameter("isFeatured")));
        news.setPublished(Boolean.parseBoolean(request.getParameter("isPublished")));
        
        boolean success = newsDAO.addNews(news);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "保护动态添加成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "保护动态添加失败");
        }
    }
    
    private void handleUpdateNews(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            ConservationNews news = new ConservationNews();
            news.setId(Integer.parseInt(request.getParameter("id")));
            news.setTitle(request.getParameter("title"));
            news.setContent(request.getParameter("content"));
            news.setSummary(request.getParameter("summary"));
            news.setCategory(request.getParameter("category"));
            news.setBadgeColor(request.getParameter("badgeColor"));
            news.setImageUrl(request.getParameter("imageUrl"));
            news.setAuthor(request.getParameter("author"));
            news.setPublishDate(request.getParameter("publishDate"));
            news.setFeatured(Boolean.parseBoolean(request.getParameter("isFeatured")));
            news.setPublished(Boolean.parseBoolean(request.getParameter("isPublished")));
            
            boolean success = newsDAO.updateNews(news);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "保护动态更新成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "保护动态更新失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的新闻ID");
        }
    }
    
    private void handleDeleteNews(HttpServletRequest request, JSONObject jsonResponse) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean success = newsDAO.deleteNews(id);
            
            if (success) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "保护动态删除成功");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "保护动态删除失败");
            }
        } catch (NumberFormatException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "无效的新闻ID");
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
