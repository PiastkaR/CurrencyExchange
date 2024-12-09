package com.nn.domain.repository;

import com.nn.infra.repository.JpaAccountRepository;

/**
 * This repository is left for more complicated searching methods.
 * One could also divide the model for Account Repository and AccountRepositoryCustomImpl for clarity
 */
public interface AccountRepository extends JpaAccountRepository {
}
