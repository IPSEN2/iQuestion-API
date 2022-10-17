package org.spine.iquestionapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Model {
    enum Role {
        SPINE_ADMIN, SPINE_USER, CAREGIVER
    }
    
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String password;
    private String organization;
    private Role role;

    public User() {
    }
    
    public User(String name, String email, String password, String organization, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.organization = organization;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
}
