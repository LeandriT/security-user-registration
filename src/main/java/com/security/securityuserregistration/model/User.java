package com.security.securityuserregistration.model;

import com.security.securityuserregistration.model.abstracts.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class User extends BaseModel implements Serializable {

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime lastLogin;

    private String token;
    private String additionalToken;

    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Phone> phones = new ArrayList<>();
}
