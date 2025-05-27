package com.wp.dao;

import com.wp.model.WildlifeGuide;
import com.wp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for WildlifeGuide entity
 */
public class WildlifeGuideDAO {
    private Connection connection;
    
    public WildlifeGuideDAO() {
        connection = DBUtil.getConnection();
    }
    
    /**
     * Get all wildlife guides
     * @return List of WildlifeGuide objects
     */
    public List<WildlifeGuide> getAllGuides() {
        List<WildlifeGuide> guides = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM wildlife_guides WHERE is_active = TRUE ORDER BY display_order, id");
            
            while (rs.next()) {
                WildlifeGuide guide = new WildlifeGuide();
                guide.setId(rs.getInt("id"));
                guide.setTitle(rs.getString("title"));
                guide.setContent(rs.getString("content"));
                guide.setCategory(rs.getString("category"));
                guide.setIcon(rs.getString("icon"));
                guide.setDisplayOrder(rs.getInt("display_order"));
                guide.setActive(rs.getBoolean("is_active"));
                guide.setCreatedAt(rs.getString("created_at"));
                guide.setUpdatedAt(rs.getString("updated_at"));
                
                guides.add(guide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guides;
    }
    
    /**
     * Get guide by ID
     * @param id Guide ID
     * @return WildlifeGuide object if found, null otherwise
     */
    public WildlifeGuide getGuideById(int id) {
        WildlifeGuide guide = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM wildlife_guides WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                guide = new WildlifeGuide();
                guide.setId(rs.getInt("id"));
                guide.setTitle(rs.getString("title"));
                guide.setContent(rs.getString("content"));
                guide.setCategory(rs.getString("category"));
                guide.setIcon(rs.getString("icon"));
                guide.setDisplayOrder(rs.getInt("display_order"));
                guide.setActive(rs.getBoolean("is_active"));
                guide.setCreatedAt(rs.getString("created_at"));
                guide.setUpdatedAt(rs.getString("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guide;
    }
    
    /**
     * Add new guide
     * @param guide WildlifeGuide object to add
     * @return true if successful, false otherwise
     */
    public boolean addGuide(WildlifeGuide guide) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO wildlife_guides (title, content, category, icon, display_order, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setString(1, guide.getTitle());
            preparedStatement.setString(2, guide.getContent());
            preparedStatement.setString(3, guide.getCategory());
            preparedStatement.setString(4, guide.getIcon());
            preparedStatement.setInt(5, guide.getDisplayOrder());
            preparedStatement.setBoolean(6, guide.isActive());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update guide
     * @param guide WildlifeGuide object to update
     * @return true if successful, false otherwise
     */
    public boolean updateGuide(WildlifeGuide guide) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE wildlife_guides SET title = ?, content = ?, category = ?, icon = ?, " +
                    "display_order = ?, is_active = ? WHERE id = ?");
            
            preparedStatement.setString(1, guide.getTitle());
            preparedStatement.setString(2, guide.getContent());
            preparedStatement.setString(3, guide.getCategory());
            preparedStatement.setString(4, guide.getIcon());
            preparedStatement.setInt(5, guide.getDisplayOrder());
            preparedStatement.setBoolean(6, guide.isActive());
            preparedStatement.setInt(7, guide.getId());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete guide by ID
     * @param id Guide ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteGuide(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM wildlife_guides WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
