package com.example.booking.repository;

import com.example.booking.models.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Integer> {


}
