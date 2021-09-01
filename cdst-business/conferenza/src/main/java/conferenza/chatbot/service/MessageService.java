package conferenza.chatbot.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.chatbot.model.Message;
import conferenza.chatbot.repository.MessageRepository;

@Service("messageService")
@Transactional
public class MessageService implements BaseService<Message>{
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	DataSource datasource;

	public String findRisposta(String[] domanda) {
		
		String result = null;
		String query = "SELECT COUNT(id) AS NUM , id, risposta  FROM (";
		String filtro ="";
		for (String string : domanda) {
			filtro += "SELECT id, risposta FROM domande WHERE domanda LIKE '%" + string + "%' UNION ALL ";
		}		
		filtro = filtro.substring(0, filtro.length() - 10);
		filtro += ") AS FILTRO GROUP BY id, risposta ORDER BY NUM DESC";
		String totQuery = query.concat(filtro);
		System.out.println(totQuery);
		try {
			Connection con =  datasource.getConnection();
			Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(totQuery);
	        while (rs.next()) {
	           String t = rs.getString("RISPOSTA");
	           String[] arr = t.split(";");
	           int i = new Random().nextInt(arr.length);	     		
	    	   result = arr[i];	
	           break;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<Message> findAll() {
		List<Message> lista = messageRepository.findAll();
		return lista;
	}

	@Override
	public Optional<Message> findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message save(Message msg) {
		messageRepository.save(msg);
		return null;
	}

	@Override
	public void deleteById(Integer id) {
		messageRepository.deleteById(id);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public List<Message> findAllById(List<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}
}
