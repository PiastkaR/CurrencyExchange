package com.nn.infra.repository;

import com.nn.domain.model.Account;
import com.nn.domain.model.NotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends CrudRepository<Account, Long> {

    default Account getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(("Account [" + id + "] not found.")));
    }

}
