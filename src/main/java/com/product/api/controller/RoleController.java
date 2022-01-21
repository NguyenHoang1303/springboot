package com.product.api.controller;


import com.product.api.dto.RoleDto;
import com.product.api.entity.Privilege;
import com.product.api.entity.Role;
import com.product.api.exception.NotFoundException;
import com.product.api.repository.AccountRepository;
import com.product.api.repository.PrivilegeRepository;
import com.product.api.repository.RoleRepository;
import com.product.api.responseApi.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/manager/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<RoleDto> rolesDto = new ArrayList<>();
        roleRepository.findAll().forEach(role -> {
            rolesDto.add(new RoleDto(role));
        });
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(rolesDto)
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    @RequestMapping(method = RequestMethod.POST, path = "add")
    public ResponseEntity add(@RequestBody Role role) {
        Collection<Privilege> privileges = new HashSet<>();
        role.getPrivileges().forEach(privilege -> {
            Privilege p = privilegeRepository.findById(privilege.getId()).orElse(null);
            if (p == null) throw new NotFoundException("Permission not found");
            privileges.add(p);
        });
        role.setPrivileges(privileges);
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(roleRepository.save(role))
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @RequestMapping(method = RequestMethod.DELETE, path = "delete")
    public ResponseEntity delete(@RequestParam(name = "id") int id) {
        Role role = roleRepository.findById((long) id).orElse(null);
        if (role == null) throw new NotFoundException("Role is not found");
        role.getAccounts().forEach(account -> {
            account.removeRole(role);
        });
        roleRepository.deleteById((long) id);
        return new ResponseEntity<>(new RESTResponse.Success()
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    @RequestMapping(method = RequestMethod.PUT, path = "update")
    public ResponseEntity update(@RequestBody Role role) {
        Role existRole = roleRepository.findById(role.getId()).orElse(null);
        if (existRole == null) throw new NotFoundException("Role is not found");
        existRole.setName(role.getName());
        existRole.setPrivileges(role.getPrivileges());
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(new RoleDto(roleRepository.save(existRole)))
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @RequestMapping(method = RequestMethod.GET, path = "detail")
    public ResponseEntity getById(@RequestParam(name = "id") int id) {
        Role existRole = roleRepository.findById((long) id).orElse(null);
        if (existRole == null) throw new NotFoundException("Role is not found");
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(existRole)
                .build(), HttpStatus.OK);
    }

}
