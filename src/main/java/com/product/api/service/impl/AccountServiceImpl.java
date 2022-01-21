package com.product.api.service.impl;

import com.product.api.dto.AccountDto;
import com.product.api.dto.FormAccount;
import com.product.api.entity.Account;
import com.product.api.entity.Privilege;
import com.product.api.entity.Role;
import com.product.api.exception.NotFoundException;
import com.product.api.exception.RequestValidException;
import com.product.api.repository.AccountRepository;
import com.product.api.repository.PrivilegeRepository;
import com.product.api.repository.RoleRepository;
import com.product.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AccountDto register(FormAccount form) {
        if (accountRepository.findByUsername(form.getUsername()) != null) {
            throw new RequestValidException("Username already exists! Please choose another username.");
        }

        Role role = roleRepository.findRoleById((long) form.getRoleId());

        if (role == null) throw new NotFoundException("Role is not found");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        Account account = new Account(form.getUsername(),
                bCryptPasswordEncoder.encode(form.getPassword()),
                roleSet);

        return new AccountDto(accountRepository.save(account));
    }

    @Override
    public Account getUserByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public AccountDto finByUserName(String name) {
        Account account = accountRepository.findByUsername(name);
        if (account == null) throw new NotFoundException("Account is not found");
        return new AccountDto(account);
    }

    @Override
    public Page<Account> findAll(int page, int pageSize) {
        if (page < 0 && pageSize < 0) {
            page = 1;
            pageSize = 10;
        }

        return accountRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    public Account updatePrivilege(int accountId, int privilegeId) {
        Privilege privilege = privilegeRepository.findById((long) privilegeId).orElse(null);
        Account acc = accountRepository.findById((long) accountId).orElse(null);
        if (privilege == null) throw new NotFoundException("Privilege is not found");
        if (acc == null) throw new NotFoundException("Account is not found");
        Set<Privilege> collection = acc.getPrivileges();
        boolean existPrivilege = false;
        for (Privilege p : collection) {
            if (p.getId().equals(privilege.getId())) {
                existPrivilege = true;
                break;
            }
        }
        if (existPrivilege) throw new RequestValidException("Permission is exist!");
        collection.add(privilege);
        acc.setPrivileges(collection);
        return accountRepository.save(acc);
    }

    @Override
    public Account delete(int accountId) {
        Account account = accountRepository.findById((long) accountId).orElse(null);
        if (account == null) throw new NotFoundException("Account is not found.");
        accountRepository.deleteById((long) accountId);
        return account;
    }

    @Override
    public Account findById(int id) {
        Account account = accountRepository.findById((long) id).orElse(null);
        if (account == null) throw new NotFoundException("Account is not found.");
        return account;
    }

}
