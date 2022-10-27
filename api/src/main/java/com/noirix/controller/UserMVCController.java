package com.noirix.controller;

import com.noirix.controller.requests.RoleRequest;
import com.noirix.controller.requests.UserCreateRequest;
import com.noirix.domain.HibernateRole;
import com.noirix.domain.HibernateUser;
import com.noirix.repository.RolesSpringDataRepository;
import com.noirix.repository.UserSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserMVCController {

    private final UserSpringDataRepository userService;

    private final ConversionService converter;

    private final UserSpringDataRepository repository;

    private final RolesSpringDataRepository rolesSpringDataRepository;

    @GetMapping
    public ModelAndView findAllUsers() {
        List<HibernateUser> users = userService.findAll();

        ModelAndView model = new ModelAndView();
        model.addObject("user", "Slava");
        model.addObject("users", users);

        model.setViewName("users");

        return model;
    }

//    @GetMapping("/search")
//    public ModelAndView findAllUsersWithParams(@ModelAttribute UserSearchRequest userSearchRequest) {
//
//        int verifiedLimit = Integer.parseInt(userSearchRequest.getLimit());
//        int verifiedOffset = Integer.parseInt(userSearchRequest.getOffset());
//
//        List<HibernateUser> users = userService.search(verifiedLimit, verifiedOffset);
//
//        ModelAndView model = new ModelAndView();
//        model.addObject("user", "Slava");
//        model.addObject("users", users);
//
//        model.setViewName("users");
//
//        return model;
//    }

    @GetMapping("/{id}")
    public ModelAndView findUserById(@PathVariable String id) {

        //We have added id parsing and number format checking
        long userId = Long.parseLong(id);
        Optional<HibernateUser> user = userService.findById(userId);

        ModelAndView model = new ModelAndView();
        model.addObject("userName", user.get().getUserName());
        model.addObject("user", user);

        model.setViewName("user");

        return model;
    }

    @PostMapping
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, timeout = 100, rollbackFor = Exception.class)
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest createRequest) {

        RoleRequest roleRequest = new RoleRequest();

        HibernateUser user = converter.convert(createRequest, HibernateUser.class);
        HibernateUser createdUser = repository.save(setRoles(user));

        HibernateRole convertTest = converter.convert(roleRequest, HibernateRole.class);
        //repository.createRoleRow(createdUser.getId(), roleRepository.findById(1L).getId());

        Map<String, Object> model = new HashMap<>();
        model.put("user", repository.findById(createdUser.getId()).get());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
    private HibernateUser setRoles(HibernateUser user) {
        Set<HibernateRole> roles = user.getRoles();

        Set<HibernateRole> updatedRoles = new HashSet<>();

        if (!CollectionUtils.isEmpty(roles)) {
            updatedRoles.addAll(roles);
        }
        updatedRoles.add(rolesSpringDataRepository.findById(1L).get());
        updatedRoles.add(rolesSpringDataRepository.findById(2L).get());

        user.setRoles(updatedRoles);

        return user;
    }


}
