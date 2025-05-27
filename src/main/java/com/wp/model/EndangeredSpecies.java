package com.wp.model;

/**
 * EndangeredSpecies model class representing an endangered species in the system
 */
public class EndangeredSpecies {
    private int id;
    private String name;
    private String scientificName;
    private String imageUrl;
    private String description;
    private String conservationStatus;
    private String populationCount;
    private String habitat;
    private String threats;
    private String conservationEfforts;
    private String createdAt;
    private String updatedAt;

    public EndangeredSpecies() {
    }

    public EndangeredSpecies(int id, String name, String scientificName, String imageUrl, 
                           String description, String conservationStatus, String populationCount, 
                           String habitat, String threats, String conservationEfforts, 
                           String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.scientificName = scientificName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.conservationStatus = conservationStatus;
        this.populationCount = populationCount;
        this.habitat = habitat;
        this.threats = threats;
        this.conservationEfforts = conservationEfforts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public String getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(String populationCount) {
        this.populationCount = populationCount;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getThreats() {
        return threats;
    }

    public void setThreats(String threats) {
        this.threats = threats;
    }

    public String getConservationEfforts() {
        return conservationEfforts;
    }

    public void setConservationEfforts(String conservationEfforts) {
        this.conservationEfforts = conservationEfforts;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "EndangeredSpecies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scientificName='" + scientificName + '\'' +
                ", conservationStatus='" + conservationStatus + '\'' +
                ", populationCount='" + populationCount + '\'' +
                ", habitat='" + habitat + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
