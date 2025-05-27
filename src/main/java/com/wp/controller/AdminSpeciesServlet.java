package com.wp.controller;

import com.wp.dao.EndangeredSpeciesDAO;
import com.wp.model.EndangeredSpecies;
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
 * Admin Servlet for managing endangered species
 */
@WebServlet("/admin/species")
public class AdminSpeciesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EndangeredSpeciesDAO speciesDAO;
    
    @Override
    public void init() {
        speciesDAO = new EndangeredSpeciesDAO();
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
                    handleAddSpecies(request, jsonResponse);
                    break;
                case "update":
                    handleUpdateSpecies(request, jsonResponse);
                    break;
                case "delete":
                    handleDeleteSpecies(request, jsonResponse);
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
    
    private void handleAddSpecies(HttpServletRequest request, JSONObject jsonResponse) {
        EndangeredSpecies species = new EndangeredSpecies();
        species.setName(request.getParameter("name"));
        species.setScientificName(request.getParameter("scientificName"));
        species.setImageUrl(request.getParameter("imageUrl"));
        species.setDescription(request.getParameter("description"));
        species.setConservationStatus(request.getParameter("conservationStatus"));
        species.setPopulationCount(request.getParameter("populationCount"));
        species.setHabitat(request.getParameter("habitat"));
        species.setThreats(request.getParameter("threats"));
        species.setConservationEfforts(request.getParameter("conservationEfforts"));
        
        boolean success = speciesDAO.addSpecies(species);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "物种添加成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "物种添加失败");
        }
    }
    
    private void handleUpdateSpecies(HttpServletRequest request, JSONObject jsonResponse) {
        EndangeredSpecies species = new EndangeredSpecies();
        species.setId(Integer.parseInt(request.getParameter("id")));
        species.setName(request.getParameter("name"));
        species.setScientificName(request.getParameter("scientificName"));
        species.setImageUrl(request.getParameter("imageUrl"));
        species.setDescription(request.getParameter("description"));
        species.setConservationStatus(request.getParameter("conservationStatus"));
        species.setPopulationCount(request.getParameter("populationCount"));
        species.setHabitat(request.getParameter("habitat"));
        species.setThreats(request.getParameter("threats"));
        species.setConservationEfforts(request.getParameter("conservationEfforts"));
        
        boolean success = speciesDAO.updateSpecies(species);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "物种更新成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "物种更新失败");
        }
    }
    
    private void handleDeleteSpecies(HttpServletRequest request, JSONObject jsonResponse) {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = speciesDAO.deleteSpecies(id);
        
        if (success) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "物种删除成功");
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "物种删除失败");
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
