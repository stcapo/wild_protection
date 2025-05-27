package com.wp.dao;

import com.wp.model.ConservationNews;
import com.wp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for ConservationNews entity
 */
public class ConservationNewsDAO {
    private Connection connection;
    
    public ConservationNewsDAO() {
        connection = DBUtil.getConnection();
    }
    
    /**
     * Get all published conservation news
     * @return List of ConservationNews objects
     */
    public List<ConservationNews> getAllNews() {
        List<ConservationNews> newsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                "SELECT * FROM conservation_news WHERE is_published = TRUE ORDER BY publish_date DESC, id DESC");
            
            while (rs.next()) {
                ConservationNews news = new ConservationNews();
                news.setId(rs.getInt("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setSummary(rs.getString("summary"));
                news.setCategory(rs.getString("category"));
                news.setBadgeColor(rs.getString("badge_color"));
                news.setImageUrl(rs.getString("image_url"));
                news.setAuthor(rs.getString("author"));
                news.setPublishDate(rs.getString("publish_date"));
                news.setFeatured(rs.getBoolean("is_featured"));
                news.setPublished(rs.getBoolean("is_published"));
                news.setViewCount(rs.getInt("view_count"));
                news.setCreatedAt(rs.getString("created_at"));
                news.setUpdatedAt(rs.getString("updated_at"));
                
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }
    
    /**
     * Get all news including unpublished (for admin)
     * @return List of ConservationNews objects
     */
    public List<ConservationNews> getAllNewsForAdmin() {
        List<ConservationNews> newsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                "SELECT * FROM conservation_news ORDER BY publish_date DESC, id DESC");
            
            while (rs.next()) {
                ConservationNews news = new ConservationNews();
                news.setId(rs.getInt("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setSummary(rs.getString("summary"));
                news.setCategory(rs.getString("category"));
                news.setBadgeColor(rs.getString("badge_color"));
                news.setImageUrl(rs.getString("image_url"));
                news.setAuthor(rs.getString("author"));
                news.setPublishDate(rs.getString("publish_date"));
                news.setFeatured(rs.getBoolean("is_featured"));
                news.setPublished(rs.getBoolean("is_published"));
                news.setViewCount(rs.getInt("view_count"));
                news.setCreatedAt(rs.getString("created_at"));
                news.setUpdatedAt(rs.getString("updated_at"));
                
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }
    
    /**
     * Get news by ID
     * @param id News ID
     * @return ConservationNews object if found, null otherwise
     */
    public ConservationNews getNewsById(int id) {
        ConservationNews news = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM conservation_news WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                news = new ConservationNews();
                news.setId(rs.getInt("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setSummary(rs.getString("summary"));
                news.setCategory(rs.getString("category"));
                news.setBadgeColor(rs.getString("badge_color"));
                news.setImageUrl(rs.getString("image_url"));
                news.setAuthor(rs.getString("author"));
                news.setPublishDate(rs.getString("publish_date"));
                news.setFeatured(rs.getBoolean("is_featured"));
                news.setPublished(rs.getBoolean("is_published"));
                news.setViewCount(rs.getInt("view_count"));
                news.setCreatedAt(rs.getString("created_at"));
                news.setUpdatedAt(rs.getString("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }
    
    /**
     * Add new news
     * @param news ConservationNews object to add
     * @return true if successful, false otherwise
     */
    public boolean addNews(ConservationNews news) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO conservation_news (title, content, summary, category, badge_color, " +
                    "image_url, author, publish_date, is_featured, is_published) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setString(3, news.getSummary());
            preparedStatement.setString(4, news.getCategory());
            preparedStatement.setString(5, news.getBadgeColor());
            preparedStatement.setString(6, news.getImageUrl());
            preparedStatement.setString(7, news.getAuthor());
            preparedStatement.setString(8, news.getPublishDate());
            preparedStatement.setBoolean(9, news.isFeatured());
            preparedStatement.setBoolean(10, news.isPublished());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update news
     * @param news ConservationNews object to update
     * @return true if successful, false otherwise
     */
    public boolean updateNews(ConservationNews news) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE conservation_news SET title = ?, content = ?, summary = ?, category = ?, " +
                    "badge_color = ?, image_url = ?, author = ?, publish_date = ?, is_featured = ?, " +
                    "is_published = ? WHERE id = ?");
            
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getContent());
            preparedStatement.setString(3, news.getSummary());
            preparedStatement.setString(4, news.getCategory());
            preparedStatement.setString(5, news.getBadgeColor());
            preparedStatement.setString(6, news.getImageUrl());
            preparedStatement.setString(7, news.getAuthor());
            preparedStatement.setString(8, news.getPublishDate());
            preparedStatement.setBoolean(9, news.isFeatured());
            preparedStatement.setBoolean(10, news.isPublished());
            preparedStatement.setInt(11, news.getId());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete news by ID
     * @param id News ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteNews(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM conservation_news WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get featured news for homepage
     * @param limit Number of news to return
     * @return List of featured ConservationNews objects
     */
    public List<ConservationNews> getFeaturedNews(int limit) {
        List<ConservationNews> newsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM conservation_news WHERE is_published = TRUE AND is_featured = TRUE " +
                "ORDER BY publish_date DESC LIMIT ?");
            preparedStatement.setInt(1, limit);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                ConservationNews news = new ConservationNews();
                news.setId(rs.getInt("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setSummary(rs.getString("summary"));
                news.setCategory(rs.getString("category"));
                news.setBadgeColor(rs.getString("badge_color"));
                news.setImageUrl(rs.getString("image_url"));
                news.setAuthor(rs.getString("author"));
                news.setPublishDate(rs.getString("publish_date"));
                news.setFeatured(rs.getBoolean("is_featured"));
                news.setPublished(rs.getBoolean("is_published"));
                news.setViewCount(rs.getInt("view_count"));
                news.setCreatedAt(rs.getString("created_at"));
                news.setUpdatedAt(rs.getString("updated_at"));
                
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
