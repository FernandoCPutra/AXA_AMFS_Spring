package com.AXA.AMFS;

import com.AXA.AMFS.dao.AccountRepository;
import com.AXA.AMFS.dto.AccountGridDTO;
import com.AXA.AMFS.dto.AccountRegistDTO;
import com.AXA.AMFS.dto.AccountUpdateDTO;
import com.AXA.AMFS.entity.Account;
import com.AXA.AMFS.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerAccount() {
        AccountRegistDTO accountRegistDTO = new AccountRegistDTO("testuser", "testpassword");
        Account account = new Account(accountRegistDTO.getUsername(), accountRegistDTO.getPassword());

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.registerAccount(accountRegistDTO);
    }

    @Test
    void checkExistingAccount() {
        String username = "testuser";

        when(accountRepository.count(username)).thenReturn(1L);

        boolean result = accountService.checkExistingAccount(username);

        assertEquals(true, result);
    }

    @Test
    void getAccountGrid() {
        List<AccountGridDTO> accountGridDTOList = new ArrayList<>();

        when(accountRepository.findAllGrid()).thenReturn(accountGridDTOList);

        List<AccountGridDTO> result = accountService.getAccountGrid();

        assertEquals(accountGridDTOList, result);
    }

    @Test
    void getOneAccount() {
        String username = "testuser";
        AccountUpdateDTO expectedAccountUpdateDTO = new AccountUpdateDTO(1L, username, "testpassword");

        when(accountRepository.findOneAccountByUsername(username)).thenReturn(expectedAccountUpdateDTO);

        AccountUpdateDTO result = accountService.getOneAccount(username);

        assertEquals(expectedAccountUpdateDTO, result);
    }

    @Test
    void saveAccount() {
        AccountRegistDTO accountRegistDTO = new AccountRegistDTO("testuser", "testpassword");
        Account expectedAccount = new Account(accountRegistDTO.getUsername(), accountRegistDTO.getPassword());

        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);

        Account result = accountService.saveAccount(accountRegistDTO);

        assertEquals(expectedAccount.getId(), result.getId());
        assertEquals(expectedAccount.getUsername(), result.getUsername());
    }

    @Test
    void updateAccount() {
        AccountUpdateDTO accountUpdateDTO = new AccountUpdateDTO(1L, "updateduser", "updatedpassword");
        Account existingAccount = new Account("testuser", "testpassword");
        existingAccount.setId(1L);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);

        Account result = accountService.updateAccount(accountUpdateDTO);

        assertEquals(accountUpdateDTO.getUsername(), result.getUsername());
        assertEquals(accountUpdateDTO.getPassword(), result.getPassword());
    }

    @Test
    void deleteAccount() {
        Long accountId = 1L;
        Account accountToDelete = new Account("testuser", "testpassword");
        accountToDelete.setId(accountId);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountToDelete));

        String result = accountService.deleteAccount(accountId);

        assertEquals(accountToDelete.getUsername(), result);
    }
}