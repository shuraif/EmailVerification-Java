package com.ms.emailconfirmation.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ms.emailconfirmation.entity.ActivationToken;

@Repository
public interface ActivationTokenRepo extends CrudRepository<ActivationToken, Long> {

	ActivationToken findByTokenId(Long tokenId);

}
