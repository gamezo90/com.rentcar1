package com.noirix.repository.springdata;

import com.noirix.domain.Role;
import com.noirix.domain.hibernate.HibernateRole;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesSpringDataRepository extends JpaRepository<HibernateRole, Long> {

    @Cacheable("roles")
    //@Query(value = "select r from HibernateRole r")
    List<HibernateRole> findAll();

    @Query(value = "select r from HibernateRole  r" +
            " inner join HibernateUser u on r.id = u.id ")
    List<Role> findRolesByUserId(Long userId);
}
