package com.noirix.repository;

import com.noirix.domain.Gender;
import com.noirix.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserSpringDataRepository extends CrudRepository<User, Long>, JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    User findByIdAndGender(Long id, Gender gender);

    User findByCredentialsLogin(String login);

    List<User> findByCredentialsLoginAndUserNameAndBirthday(String login, String name, Timestamp birthDate);

    List<User> findByCredentialsLoginAndUserNameOrBirthdayOrderByIdDescUserNameDesc(String login, String name, Timestamp birthDate);

    List<User> findByIsDeletedOrderByIdDesc(Boolean isDeleted);

    //select * from m_users where (login = ? and name = ?) or birth_date = ?

    @Query(value = "select u from User u")
    List<User> findByHQLQuery();

    @Query(value = "select * from rentcar.users", nativeQuery = true)
    List<User> findByHQLQueryNative();

    @Query(value = "select u from User u where u.credentials.login = :login and u.userName = :userName")
    List<User> findByHQLQuery(String login, @Param("userName") String name);

    @Query("select u.id, u.userName from User u")
    List<Object[]> getPartsOfUser();


    @Modifying
    @Query(value = "insert into rentcar.l_role_user(user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    int createRoleRow(@Param("user_id") Long userId, @Param("role_id") Long roleId);

    @Query(value = "select u from User u")
    Optional<User> findByLogin(String login);
}
