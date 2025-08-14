package com.example.smartPos.repositories.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role_permissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;
    private String feature;
    private boolean enabled;

    // Constructors
    public RolePermission() {}

    public RolePermission(Integer id, String role, String feature, boolean enabled) {
        this.id = id;
        this.role = role;
        this.feature = feature;
        this.enabled = enabled;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFeature() { return feature; }
    public void setFeature(String feature) { this.feature = feature; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
