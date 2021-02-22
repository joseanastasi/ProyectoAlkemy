package com.alkemy.ot9.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "metrics")
public class MetricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metric_id")
    private Long id;

    private String members;

    private String patients;

    private String usersActivities;

    private Long countMembers;

    private Long countPatients;

    private Long countUsersActivities;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the members
     */
    public String getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(String members) {
        this.members = members;
    }

    /**
     * @return the patients
     */
    public String getPatients() {
        return patients;
    }

    /**
     * @param patients the patients to set
     */
    public void setPatients(String patients) {
        this.patients = patients;
    }

    /**
     * @return the usersActivities
     */
    public String getUsersActivities() {
        return usersActivities;
    }

    /**
     * @param usersActivities the usersActivities to set
     */
    public void setUsersActivities(String usersActivities) {
        this.usersActivities = usersActivities;
    }

    /**
     * @return the countMembers
     */
    public Long getCountMembers() {
        return countMembers;
    }

    /**
     * @param countMembers the countMembers to set
     */
    public void setCountMembers(Long countMembers) {
        this.countMembers = countMembers;
    }

    /**
     * @return the countPatients
     */
    public Long getCountPatients() {
        return countPatients;
    }

    /**
     * @param countPatients the countPatients to set
     */
    public void setCountPatients(Long countPatients) {
        this.countPatients = countPatients;
    }

    /**
     * @return the countUsersActivities
     */
    public Long getCountUsersActivities() {
        return countUsersActivities;
    }

    /**
     * @param countUsersActivities the countUsersActivities to set
     */
    public void setCountUsersActivities(Long countUsersActivities) {
        this.countUsersActivities = countUsersActivities;
    }

}