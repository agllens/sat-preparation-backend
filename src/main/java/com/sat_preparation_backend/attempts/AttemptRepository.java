package com.sat_preparation_backend.attempts;

import com.sat_preparation_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    List<Attempt> findByUser(User user);

    List<Attempt> findByUserOrderByCreatedAtDesc(User user);
}