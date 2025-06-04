package com.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}