package com.product.api.security;

import com.product.api.entity.Account;
import com.product.api.entity.Privilege;
import com.product.api.entity.Role;
import com.product.api.exception.NotFoundException;
import com.product.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class MyAccountDetailService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) throw new NotFoundException("Account not found");
        return new User(account.getUsername(), account.getPassword(), getAuthorities(account.getRoles(), account.getPrivileges()));
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Collection<Role> roles, Set<Privilege> permissions) {

        return getGrantedAuthorities(getPrivileges(roles, permissions));
    }

    private Set<String> getPrivileges(Collection<Role> roles, Set<Privilege> permissions) {

        Set<String> privileges = new HashSet<>();
        Set<Privilege> collection = new HashSet<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        for (Privilege item : permissions) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private Set<SimpleGrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        System.out.println("1.authorities: " + authorities);
        return authorities;
    }
}
