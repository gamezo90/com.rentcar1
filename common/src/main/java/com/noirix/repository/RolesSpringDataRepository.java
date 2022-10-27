package com.noirix.repository;


import com.noirix.domain.HibernateRole;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesSpringDataRepository extends JpaRepository<HibernateRole, Long> {

    @Cacheable("roles")
    //@Query(value = "select r from HibernateRole r")
    List<HibernateRole> findAll();

    @Query(value = "select r from HibernateRole  r" +
            " inner join HibernateUser u on r.id = u.id ")
    List<HibernateRole> findRolesByUserId(Long userId);
}
