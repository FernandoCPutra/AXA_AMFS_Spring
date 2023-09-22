package com.AXA.AMFS.dao;

import com.AXA.AMFS.dto.AccountGridDTO;
import com.AXA.AMFS.dto.AccountUpdateDTO;
import com.AXA.AMFS.entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query("""
			SELECT new com.AXA.AMFS.dto.AccountGridDTO(acc.id, acc.username) 
			FROM Account AS acc
			WHERE acc.username LIKE %:name%	
			""")
	public List<AccountGridDTO> findAll(@Param("name") String name);
    @Query("""
			SELECT COUNT(*) 
			FROM Account AS acc
			WHERE acc.username = :username	
			""")
    public Long count(@Param("username") String username);

	@Query("""
			SELECT new com.AXA.AMFS.dto.AccountGridDTO(acc.id, acc.username)
			FROM Account AS acc
			""")
	public List<AccountGridDTO> findAllGrid();

	@Query("""
			SELECT new com.AXA.AMFS.dto.AccountUpdateDTO(acc.id, acc.username, acc.password)
			FROM Account AS acc
			WHERE acc.username = :username
			""")
	public AccountUpdateDTO findOneAccountByUsername(@Param("username") String username);
}
