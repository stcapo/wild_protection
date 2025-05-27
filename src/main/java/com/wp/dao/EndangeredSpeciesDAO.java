package com.wp.dao;

import com.wp.model.EndangeredSpecies;
import com.wp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for EndangeredSpecies entity
 */
public class EndangeredSpeciesDAO {
    private Connection connection;
    
    public EndangeredSpeciesDAO() {
        connection = DBUtil.getConnection();
    }
    
    /**
     * Get all endangered species
     * @return List of EndangeredSpecies objects
     */
    public List<EndangeredSpecies> getAllSpecies() {
        List<EndangeredSpecies> speciesList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM endangered_species ORDER BY id");
            
            while (rs.next()) {
                EndangeredSpecies species = new EndangeredSpecies();
                species.setId(rs.getInt("id"));
                species.setName(rs.getString("name"));
                species.setScientificName(rs.getString("scientific_name"));
                species.setImageUrl(rs.getString("image_url"));
                species.setDescription(rs.getString("description"));
                species.setConservationStatus(rs.getString("conservation_status"));
                species.setPopulationCount(rs.getString("population_count"));
                species.setHabitat(rs.getString("habitat"));
                species.setThreats(rs.getString("threats"));
                species.setConservationEfforts(rs.getString("conservation_efforts"));
                species.setCreatedAt(rs.getString("created_at"));
                species.setUpdatedAt(rs.getString("updated_at"));
                
                speciesList.add(species);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return speciesList;
    }
    
    /**
     * Get species by ID
     * @param id Species ID
     * @return EndangeredSpecies object if found, null otherwise
     */
    public EndangeredSpecies getSpeciesById(int id) {
        EndangeredSpecies species = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM endangered_species WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                species = new EndangeredSpecies();
                species.setId(rs.getInt("id"));
                species.setName(rs.getString("name"));
                species.setScientificName(rs.getString("scientific_name"));
                species.setImageUrl(rs.getString("image_url"));
                species.setDescription(rs.getString("description"));
                species.setConservationStatus(rs.getString("conservation_status"));
                species.setPopulationCount(rs.getString("population_count"));
                species.setHabitat(rs.getString("habitat"));
                species.setThreats(rs.getString("threats"));
                species.setConservationEfforts(rs.getString("conservation_efforts"));
                species.setCreatedAt(rs.getString("created_at"));
                species.setUpdatedAt(rs.getString("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return species;
    }
    
    /**
     * Add new species
     * @param species EndangeredSpecies object to add
     * @return true if successful, false otherwise
     */
    public boolean addSpecies(EndangeredSpecies species) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO endangered_species (name, scientific_name, image_url, description, " +
                    "conservation_status, population_count, habitat, threats, conservation_efforts) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setString(1, species.getName());
            preparedStatement.setString(2, species.getScientificName());
            preparedStatement.setString(3, species.getImageUrl());
            preparedStatement.setString(4, species.getDescription());
            preparedStatement.setString(5, species.getConservationStatus());
            preparedStatement.setString(6, species.getPopulationCount());
            preparedStatement.setString(7, species.getHabitat());
            preparedStatement.setString(8, species.getThreats());
            preparedStatement.setString(9, species.getConservationEfforts());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update species
     * @param species EndangeredSpecies object to update
     * @return true if successful, false otherwise
     */
    public boolean updateSpecies(EndangeredSpecies species) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE endangered_species SET name = ?, scientific_name = ?, image_url = ?, " +
                    "description = ?, conservation_status = ?, population_count = ?, habitat = ?, " +
                    "threats = ?, conservation_efforts = ? WHERE id = ?");
            
            preparedStatement.setString(1, species.getName());
            preparedStatement.setString(2, species.getScientificName());
            preparedStatement.setString(3, species.getImageUrl());
            preparedStatement.setString(4, species.getDescription());
            preparedStatement.setString(5, species.getConservationStatus());
            preparedStatement.setString(6, species.getPopulationCount());
            preparedStatement.setString(7, species.getHabitat());
            preparedStatement.setString(8, species.getThreats());
            preparedStatement.setString(9, species.getConservationEfforts());
            preparedStatement.setInt(10, species.getId());
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete species by ID
     * @param id Species ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteSpecies(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM endangered_species WHERE id = ?");
            preparedStatement.setInt(1, id);
            
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
