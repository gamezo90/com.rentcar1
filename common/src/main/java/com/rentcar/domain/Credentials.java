package com.rentcar.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;

@Data
@Entity
//@EqualsAndHashCode(exclude = { "users" })
//@ToString(exclude = { "users"})
@Table(name = "Credentials")
@Cacheable("Credentials")
@javax.persistence.Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Credentials {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "user_login")
   private String login;

   @Column(name = "user_password")
   private String password;

   @Column(name = "user_email")
   private String email;

    @Column(name = "user_id", insertable = false, updatable = false)
   private Long userId;

   @OneToOne
   @JoinColumn(name = "user_id")
   @JsonBackReference
   private User user;
}
