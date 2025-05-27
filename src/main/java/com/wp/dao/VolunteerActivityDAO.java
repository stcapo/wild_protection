package com.wp.dao;

import com.wp.model.VolunteerActivity;
import com.wp.model.VolunteerRegistration;
import com.wp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for VolunteerActivity entity
 */
public class VolunteerActivityDAO {
    private Connection connection;
    
    public VolunteerActivityDAO() {
        connection = DBUtil.getConnection();
    }
    
    /**
     * Get all volunteer activities
     * @return List of VolunteerActivity objects
     */
    public List<VolunteerActivity> getAllActivities() {
        List<VolunteerActivity> activities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM volunteer_activities ORDER BY activity_date");
            
            while (rs.next()) {
                VolunteerActivity activity = new VolunteerActivity();
                activity.setId(rs.getInt("id"));
                activity.setTitle(rs.getString("title"));
                activity.setDescription(rs.getString("description"));
                activity.setActivityDate(rs.getString("activity_date"));
                activity.setLocation(rs.getString("location"));
                activity.setMaxParticipants(rs.getInt("max_participants"));
                activity.setCurrentParticipants(rs.getInt("current_participants"));
                activity.setStatus(rs.getString("status"));
                activity.setRequirements(rs.getString("requirements"));
                activity.setContactInfo(rs.getString("contact_info"));
                activity.setCreatedAt(rs.getString("created_at"));
                activity.setUpdatedAt(rs.getString("updated_at"));
                
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
    
    /**
     * Get activity by ID
     * @param id Activity ID
     * @return VolunteerActivity object if found, null otherwise
     */
    public VolunteerActivity getActivityById(int id) {
        VolunteerActivity activity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM volunteer_activities WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                activity = new VolunteerActivity();
                activity.setId(rs.getInt("id"));
                activity.setTitle(rs.getString("title"));
                activity.setDescription(rs.getString("description"));
                activity.setActivityDate(rs.getString("activity_date"));
                activity.setLocation(rs.getString("location"));
                activity.setMaxParticipants(rs.getInt("max_participants"));
                activity.setCurrentParticipants(rs.getInt("current_participants"));
                activity.setStatus(rs.getString("status"));
                activity.setRequirements(rs.getString("requirements"));
                activity.setContactInfo(rs.getString("contact_info"));
                activity.setCreatedAt(rs.getString("created_at"));
                activity.setUpdatedAt(rs.getString("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }
    
    /**
     * Add new activity
     * @param activity VolunteerActivity object to add
     * @return true if successful, false otherwise
     */
    public boolean addActivity(VolunteerActivity activity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO volunteer_activities (title, description, activity_date, location, " +
                    "max_participants, requirements, contact_info, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setString(1, activity.getTitle());
            preparedStatement.setString(2, activity.getDescription());
            preparedStatement.setString(3, activity.getActivityDate());
            preparedStatement.setString(4, activity.getLocation());
            preparedStatement.setInt(5, activity.getMaxParticipants());
            preparedStatement.setString(6, activity.getRequirements());
            preparedStatement.setString(7, activity.getContactInfo());
            preparedStatement.setString(8, activity.getStatus() != null ? activity.getStatus() : "open");
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update activity
     * @param activity VolunteerActivity object to update
     * @return true if successful, false otherwise
     */
    public boolean updateActivity(VolunteerActivity activity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE volunteer_activities SET title = ?, description = ?, activity_date = ?, " +
                    "location = ?, max_participants = ?, requirements = ?, contact_info = ?, status = ? WHERE id = ?");
            
            preparedStatement.setString(1, activity.getTitle());
            preparedStatement.setString(2, activity.getDescription());
            preparedStatement.setString(3, activity.getActivityDate());
            preparedStatement.setString(4, activity.getLocation());
            preparedStatement.setInt(5, activity.getMaxParticipants());
            preparedStatement.setString(6, activity.getRequirements());
            preparedStatement.setString(7, activity.getContactInfo());
            preparedStatement.setString(8, activity.getStatus());
            preparedStatement.setInt(9, activity.getId());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete activity by ID
     * @param id Activity ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteActivity(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM volunteer_activities WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Register user for activity
     * @param activityId Activity ID
     * @param userId User ID
     * @param notes Optional notes
     * @return true if successful, false otherwise
     */
    public boolean registerForActivity(int activityId, int userId, String notes) {
        try {
            // Check if user is already registered
            PreparedStatement checkStmt = connection.prepareStatement(
                    "SELECT COUNT(*) FROM volunteer_registrations WHERE activity_id = ? AND user_id = ?");
            checkStmt.setInt(1, activityId);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Already registered
            }
            
            // Register user
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO volunteer_registrations (activity_id, user_id, notes) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, activityId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, notes);
            
            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                // Update current participants count
                PreparedStatement updateStmt = connection.prepareStatement(
                        "UPDATE volunteer_activities SET current_participants = current_participants + 1 WHERE id = ?");
                updateStmt.setInt(1, activityId);
                updateStmt.executeUpdate();
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all registrations with activity and user details
     * @return List of VolunteerRegistration objects with joined data
     */
    public List<VolunteerRegistration> getAllRegistrations() {
        List<VolunteerRegistration> registrations = new ArrayList<>();
        try {
            String sql = "SELECT vr.*, va.title as activity_title, va.activity_date, va.location as activity_location, " +
                        "u.username, u.email as user_email FROM volunteer_registrations vr " +
                        "JOIN volunteer_activities va ON vr.activity_id = va.id " +
                        "JOIN users u ON vr.user_id = u.id " +
                        "ORDER BY vr.registration_date DESC";
            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                VolunteerRegistration registration = new VolunteerRegistration();
                registration.setId(rs.getInt("id"));
                registration.setActivityId(rs.getInt("activity_id"));
                registration.setUserId(rs.getInt("user_id"));
                registration.setRegistrationDate(rs.getString("registration_date"));
                registration.setStatus(rs.getString("status"));
                registration.setNotes(rs.getString("notes"));
                registration.setActivityTitle(rs.getString("activity_title"));
                registration.setActivityDate(rs.getString("activity_date"));
                registration.setActivityLocation(rs.getString("activity_location"));
                registration.setUserName(rs.getString("username"));
                registration.setUserEmail(rs.getString("user_email"));
                
                registrations.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }
}
