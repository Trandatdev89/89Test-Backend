package com.project01.quiz.repository;

import com.project01.quiz.entity.InValidTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InvalidateTokenRepository extends JpaRepository<InValidTokenEntity,String> {

    @Transactional
    @Modifying
    @Query("DELETE from InValidTokenEntity t where t.expirytime<=CURRENT_TIMESTAMP")
    void deleteExpiryTime();
}
