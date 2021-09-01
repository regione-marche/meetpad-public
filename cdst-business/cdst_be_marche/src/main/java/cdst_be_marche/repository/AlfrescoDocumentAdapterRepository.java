package cdst_be_marche.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cdst_be_marche.model.AlfrescoDocumentAdapter;




public interface AlfrescoDocumentAdapterRepository extends JpaRepository<AlfrescoDocumentAdapter, Integer> {



}
