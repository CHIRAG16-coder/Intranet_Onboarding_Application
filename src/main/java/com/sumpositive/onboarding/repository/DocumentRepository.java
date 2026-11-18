
package com.sumpositive.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumpositive.onboarding.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUserId(Long userId);

    List<Document> findByStatus(String status);
}
