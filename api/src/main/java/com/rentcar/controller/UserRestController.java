package com.rentcar.controller;

import com.rentcar.controller.requests.RoleRequest;
import com.rentcar.controller.requests.UserCreateRequest;
import com.rentcar.domain.Role;
import com.rentcar.domain.User;
import com.rentcar.repository.RolesRepository;
import com.rentcar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserRepository userService;

    private final UserRepository userRepository;

    private final UserRepository hibernateUserInterface;

    private final ConversionService converter;

    private final UserRepository repository;

    private final RolesRepository rolesRepository;


    @GetMapping
    @RequestMapping("/hibernate")
    public ResponseEntity<Object> findAllHibernateUsers() {

        //return new ResponseEntity<>(Collections.singletonMap("result", userRepository.getUserStats()), HttpStatus.OK);

        //return new ResponseEntity<>(Collections.singletonMap("result", userRepository.findAll(1, 1)), HttpStatus.OK);
        return new ResponseEntity<>(Collections.singletonMap("result", hibernateUserInterface.findAll()), HttpStatus.OK);
    }

//    @GetMapping
//    @RequestMapping("/hibernate/criteria")
//    //@ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Object> findAllHibernateUsers(@ModelAttribute SearchCriteria criteria) {
//
//        return new ResponseEntity<>(Collections.singletonMap("result", userRepository.criteriaAPITest(criteria)), HttpStatus.OK);
//
//        //return Collections.singletonMap("result", userService.findAll());
//    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findAllUsers() {

        return new ResponseEntity<>(Collections.singletonMap("result", userService.findAll()), HttpStatus.OK);

        //return Collections.singletonMap("result", userService.findAll());
    }

//    @GetMapping("/search")
//    public ResponseEntity<Object> findAllUsersWithParams(@ModelAttribute UserSearchRequest userSearchRequest) {
//
//        int verifiedLimit = Integer.parseInt(userSearchRequest.getLimit());
//        int verifiedOffset = Integer.parseInt(userSearchRequest.getOffset());
//
//        List<HibernateUser> users = userService.search(verifiedLimit, verifiedOffset);
//
//        Map<String, Object> model = new HashMap<>();
//        model.put("user", "Slava");
//        model.put("users", users);
//
//        return new ResponseEntity<>(model, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findUserById(@PathVariable String id) {

        //We have added id parsing and number format checking
        long userId = Long.parseLong(id);

        return new ResponseEntity<>(Collections.singletonMap("user", userService.findById(userId)), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, timeout = 100, rollbackFor = Exception.class)
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest createRequest) {

        RoleRequest roleRequest = new RoleRequest();

        User user = converter.convert(createRequest, User.class);
        User createdUser = repository.save(setRoles(user));

        Role convertTest = converter.convert(roleRequest, Role.class);
        //repository.createRoleRow(createdUser.getId(), roleRepository.findById(1L).getId());

        Map<String, Object> model = new HashMap<>();
        model.put("user", repository.findById(createdUser.getId()).get());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
    private User setRoles(User user) {
        Set<Role> roles = user.getRoles();

        Set<Role> updatedRoles = new HashSet<>();

        if (!CollectionUtils.isEmpty(roles)) {
            updatedRoles.addAll(roles);
        }
        updatedRoles.add(rolesRepository.findById(1L).get());
        updatedRoles.add(rolesRepository.findById(2L).get());

        user.setRoles(updatedRoles);

        return user;
    }


}
