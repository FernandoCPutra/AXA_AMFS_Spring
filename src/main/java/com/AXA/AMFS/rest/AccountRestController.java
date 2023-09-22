package com.AXA.AMFS.rest;

import com.AXA.AMFS.dto.AccountGridDTO;
import com.AXA.AMFS.dto.AccountRegistDTO;
import com.AXA.AMFS.dto.AccountUpdateDTO;
import com.AXA.AMFS.entity.Account;
import com.AXA.AMFS.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(AccountRestController.class);

    @GetMapping(value={
            ""
    })
    public ResponseEntity<Object> getAll(){
        try{
            List<AccountGridDTO> account = accountService.getAccountGrid();
            log.info("endpoint : getAllAccount/count/"+account.stream().count());
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (Exception exception){
            log.error("endpoint : failGetAllAcount/error/"+exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
            "/username={username}"
    })
    public ResponseEntity<Object> getOneAccount(@PathVariable(required = false) String username){
        try{
            AccountUpdateDTO account = accountService.getOneAccount(username);
            log.info("endpoint : /username/" + account.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } catch (Exception exception){
            log.error("endpoint : /error/" + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody AccountRegistDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                Account entity = accountService.saveAccount(dto);
                dto.setId(entity.getId());
                log.info("endpoint : registered/username/"+entity.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                log.error("endpoint : error/bindingResult/message/"+bindingResult.toString());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            log.error("endpoint : fail/register/"+exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody AccountUpdateDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                accountService.updateAccount(dto);
                log.info("endpoint : updated/account/"+dto.getUsername());
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                log.error("endpoint : fail/update/message/"+bindingResult.toString());
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            log.error("endpoint : fail/update/"+exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try{
            accountService.deleteAccount(id);
            log.info("endpoint : deleted/account/id"+id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            log.error("endpoint : delete-failed/"+exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
