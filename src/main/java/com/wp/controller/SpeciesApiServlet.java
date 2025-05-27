package com.wp.controller;

import com.wp.dao.EndangeredSpeciesDAO;
import com.wp.model.EndangeredSpecies;
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
 * API Servlet for endangered species data
 */
@WebServlet("/api/species")
public class SpeciesApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EndangeredSpeciesDAO speciesDAO;
    
    @Override
    public void init() {
        speciesDAO = new EndangeredSpeciesDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            List<EndangeredSpecies> speciesList = speciesDAO.getAllSpecies();
            JSONArray jsonArray = new JSONArray();
            
            for (EndangeredSpecies species : speciesList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", species.getId());
                jsonObject.put("name", species.getName());
                jsonObject.put("scientificName", species.getScientificName());
                jsonObject.put("imageUrl", species.getImageUrl());
                jsonObject.put("description", species.getDescription());
                jsonObject.put("conservationStatus", species.getConservationStatus());
                jsonObject.put("populationCount", species.getPopulationCount());
                jsonObject.put("habitat", species.getHabitat());
                jsonObject.put("threats", species.getThreats());
                jsonObject.put("conservationEfforts", species.getConservationEfforts());
                jsonObject.put("createdAt", species.getCreatedAt());
                jsonObject.put("updatedAt", species.getUpdatedAt());
                
                jsonArray.put(jsonObject);
            }
            
            JSONObject response_obj = new JSONObject();
            response_obj.put("success", true);
            response_obj.put("data", jsonArray);
            
            out.print(response_obj.toString());
        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "获取数据失败: " + e.getMessage());
            out.print(errorResponse.toString());
        }
        
        out.flush();
    }
}
