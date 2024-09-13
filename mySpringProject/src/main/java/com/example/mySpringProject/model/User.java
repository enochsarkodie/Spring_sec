package com.example.mySpringProject.model;


import com.example.mySpringProject.model.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;


@Entity(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonIgnore
    private String password;



    @Column(name= "email", nullable = false, unique = true, length = 320)
    private String email;


    private String firstName;


    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Address address;

    private boolean accountLocked;

    private boolean enabled;
    @OneToOne(mappedBy = "user")
    private ResetPasswordToken resetPassword;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public  String fullName(){return firstName + " " + lastName; }

    @Override
    public String getName() {
        return fullName();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
