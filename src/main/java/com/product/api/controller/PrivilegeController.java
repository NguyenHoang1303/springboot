package com.product.api.controller;

import com.product.api.repository.PrivilegeRepository;
import com.product.api.responseApi.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/manager/privileges")
public class PrivilegeController {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll() {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(privilegeRepository.findAll())
                .build(), HttpStatus.OK);
    }

}
