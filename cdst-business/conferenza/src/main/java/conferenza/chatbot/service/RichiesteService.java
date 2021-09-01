package conferenza.chatbot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.chatbot.model.Message;
import conferenza.chatbot.model.Richieste;
import conferenza.chatbot.repository.MessageRepository;
import conferenza.chatbot.repository.RichiesteRepository;



@Service("richiesteService")
@Transactional
public class RichiesteService implements BaseService<Richieste>{
	
	@Autowired
	private RichiesteRepository richiesteRepository;

	@Override
	public List<Richieste> findAll() {
		List<Richieste> lista = richiesteRepository.findAll();
		return lista;
	}

	@Override
	public Optional<Richieste> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Richieste save(Richieste richiesta) {
		richiesteRepository.save(richiesta);
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		richiesteRepository.deleteById(id);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Richieste> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Richieste> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

}
