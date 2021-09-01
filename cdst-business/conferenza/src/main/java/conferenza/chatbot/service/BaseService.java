package conferenza.chatbot.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface BaseService <T extends Serializable> {

	List<T> findAll();

	Optional<T> findById(Integer id);

	T save(T object);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	List<T> findAllById(List<Integer> ids);

	List<T> findAll(Sort sort);

	
}