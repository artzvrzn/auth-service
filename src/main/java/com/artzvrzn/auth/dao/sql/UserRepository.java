package com.artzvrzn.auth.dao.sql;

import com.artzvrzn.auth.domain.User;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

}
