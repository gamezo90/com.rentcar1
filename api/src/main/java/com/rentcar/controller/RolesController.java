package com.rentcar.controller;

import com.rentcar.repository.RolesRepository;
import com.rentcar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RolesController {

    private final RolesRepository repository;


//    @GetMapping
//    public ResponseEntity<Object> findAllRolesWithCache() {
//
//        return new ResponseEntity<>(
//                Collections.singletonMap("result", repository.findAll()),
//                HttpStatus.OK
//        );
//    }

    @GetMapping("/gsdfg")
    public ResponseEntity<Object> findAllRoles() {

        return new ResponseEntity<>(Collections.singletonMap("result",
                repository.findAll(PageRequest.of(0, 10))), HttpStatus.OK);
    }
}
