package com.AXA.AMFS.service;

import com.AXA.AMFS.dao.AccountRepository;
import com.AXA.AMFS.dto.AccountGridDTO;
import com.AXA.AMFS.dto.AccountRegistDTO;
import com.AXA.AMFS.dto.AccountUpdateDTO;
import com.AXA.AMFS.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void registerAccount(AccountRegistDTO dto) {
        Account account = new Account(
                dto.getUsername(),
                dto.getPassword());
        accountRepository.save(account);
    }

    @Override
    public Boolean checkExistingAccount(String username) {
        Long totalUser = accountRepository.count(username);
        return (totalUser > 0) ? true : false;
    }

    @Override
    public List<AccountGridDTO> getAccountGrid() {
        return accountRepository.findAllGrid();
    }

    @Override
    public AccountUpdateDTO getOneAccount(String username) {
        return accountRepository.findOneAccountByUsername(username);
    }

    @Override
    public Account saveAccount(AccountRegistDTO dto) {
        Account entity = new Account(
                dto.getUsername(),
                dto.getPassword()
                );
       accountRepository.save(entity);
        return entity;
    }

    @Override
    public Account updateAccount(AccountUpdateDTO dto) {
        Optional<Account> entity = accountRepository.findById(dto.getId());
        Account updatedAcc = entity.get();
        updatedAcc.setId(dto.getId());
        updatedAcc.setUsername(dto.getUsername());
        updatedAcc.setPassword(dto.getPassword());
        accountRepository.save(updatedAcc);
        return updatedAcc;
    }

    @Override
    public String deleteAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        accountRepository.deleteById(id);
        return account.get().getUsername();
    }
}