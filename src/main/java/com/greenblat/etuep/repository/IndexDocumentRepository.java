package com.greenblat.etuep.repository;

import com.greenblat.etuep.model.IndexDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexDocumentRepository extends SolrCrudRepository<IndexDocument, Long> {

    @Query("text:*?0*")
    List<IndexDocument> findByTextContains(String text);
}
