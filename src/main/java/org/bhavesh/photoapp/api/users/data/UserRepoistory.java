package org.bhavesh.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoistory extends CrudRepository<UsersEntity, Long> {
	UsersEntity findByEmail(String email);
}
