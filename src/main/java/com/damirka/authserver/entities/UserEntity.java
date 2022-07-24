package com.damirka.authserver.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;

    private String email;

    private String firstname;
    private String lastname;
    private String gender;
    private String phone;

    private Date dateOfBirth;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date updated;
    private Boolean deleted;
    private Boolean blocked;
    private Boolean activated;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles",
//            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<RoleEntity> roles;

    public UserEntity() {

    }

    public boolean isEnabled() {
        return !deleted && !blocked && activated;
    }
}
