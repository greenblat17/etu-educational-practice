package com.greenblat.etuep.repository;

import com.greenblat.etuep.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findDocumentByDocumentName(String documentName);

    Optional<Document> findDocumentByDocumentNameAndDownloadDate(String documentName, LocalDate downloadDate);

}
