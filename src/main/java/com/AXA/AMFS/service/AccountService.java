package com.AXA.AMFS.service;

import com.AXA.AMFS.dto.AccountGridDTO;
import com.AXA.AMFS.dto.AccountRegistDTO;
import com.AXA.AMFS.dto.AccountUpdateDTO;
import com.AXA.AMFS.entity.Account;

import java.util.List;

public interface AccountService {
    public void registerAccount(AccountRegistDTO dto);
    public Boolean checkExistingAccount(String username);
    public List<AccountGridDTO> getAccountGrid();
    public AccountUpdateDTO getOneAccount(String username);
    public Account saveAccount(AccountRegistDTO dto);
    public Account updateAccount(AccountUpdateDTO dto);
    public String deleteAccount(Long id);
}
