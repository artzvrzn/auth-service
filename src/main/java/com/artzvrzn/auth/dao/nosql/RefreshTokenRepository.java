package com.artzvrzn.auth.dao.nosql;

import com.artzvrzn.auth.domain.RefreshToken;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, String> {

}
