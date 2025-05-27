package com.wp.model;

/**
 * VolunteerRegistration model class representing a volunteer registration in the system
 */
public class VolunteerRegistration {
    private int id;
    private int activityId;
    private int userId;
    private String registrationDate;
    private String status;
    private String notes;
    
    // Additional fields for joined queries
    private String activityTitle;
    private String activityDate;
    private String activityLocation;
    private String userName;
    private String userEmail;

    public VolunteerRegistration() {
    }

    public VolunteerRegistration(int id, int activityId, int userId, String registrationDate, 
                               String status, String notes) {
        this.id = id;
        this.activityId = activityId;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "VolunteerRegistration{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", userId=" + userId +
                ", registrationDate='" + registrationDate + '\'' +
                ", status='" + status + '\'' +
                ", activityTitle='" + activityTitle + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
