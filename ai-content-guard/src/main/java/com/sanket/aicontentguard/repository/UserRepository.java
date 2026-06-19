package com.sanket.aicontentguard.repository;

import com.sanket.aicontentguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
