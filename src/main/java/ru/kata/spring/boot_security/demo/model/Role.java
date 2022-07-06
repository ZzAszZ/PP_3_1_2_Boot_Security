package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    @Column
    private String name;


    public Role() {}

    @Override
    public String getAuthority() {
        return getName();
    }



    @Override
    public String toString() {
        return "name=" + name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role(String name) {this.name = name;}

    public Role(Integer id) {this.id = id;}

}

