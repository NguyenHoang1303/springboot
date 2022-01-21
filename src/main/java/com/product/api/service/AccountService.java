package com.product.api.service;

import com.product.api.dto.AccountDto;
import com.product.api.dto.FormAccount;
import com.product.api.entity.Account;
import org.springframework.data.domain.Page;

public interface AccountService {

    AccountDto register(FormAccount formAccount);

    Account getUserByUsername(String username);

    AccountDto finByUserName(String name);

    Page<Account> findAll(int page, int pageSize);

    Account updatePrivilege(int accId, int privilegeId);

    Account delete(int accountId);

    Account findById(int id);
}
