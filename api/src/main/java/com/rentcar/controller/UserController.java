package com.rentcar.controller;

import com.rentcar.controller.requests.RoleRequest;
import com.rentcar.controller.requests.UserCreateRequest;
import com.rentcar.domain.Gender;
import com.rentcar.domain.Role;
import com.rentcar.domain.User;
import com.rentcar.repository.RoleRepository;
import com.rentcar.repository.UserRepository;
import com.rentcar.security.util.PrincipalUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final ConversionService converter;

    @GetMapping("/findAllUser")
    public ResponseEntity<Object> findAllUser() {

        return new ResponseEntity<>(Collections.singletonMap("result",
                repository.findAll(PageRequest.of(0, 10))), HttpStatus.OK);
    }

    @GetMapping("/findAllUser1")
    public ResponseEntity<Object> finddasdas() {

        return new ResponseEntity<>(Collections.singletonMap("result",
                repository.findById(1L)), HttpStatus.OK);
    }

    @ApiOperation(value = "Finding all users with Page Info response")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
            @ApiImplicitParam(name = "query", defaultValue = "query", required = false, paramType = "query", dataType = "string")

//            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
//                    value = "Results page you want to retrieve (0..N)"),
//            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
//                    value = "Number of records per page."),
//            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
//                    value = "Sorting criteria in the format: property(,asc|desc). " +
//                            "Default sort order is ascending. " +
//                            "Multiple sort criteria are supported.")
    })

//
//    @GetMapping
//    public ResponseEntity<Object> findAllUsers(@ParameterObject Pageable pageable) {
//        Page<UserResponse> users = userService.findAll(pageable).map(userMapper::toResponse);
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }


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
        updatedRoles.add(roleRepository.findById(1L).get());
        updatedRoles.add(roleRepository.findById(2L).get());

        user.setRoles(updatedRoles);

        return user;
    }
}
