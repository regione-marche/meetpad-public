package conferenza.mediateca.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.mediateca.model.Mediateca;

public class MediatecaDTO  {

	
	@JsonProperty("conferenceId")	
	Integer id_conferenza;
	@JsonProperty("conferenceSubject")
	String  oggetto;
	@JsonProperty("id")
	Integer id_documento; 
	@JsonProperty("name")
	String  nome_documento;
	@JsonProperty("registryId")
	Integer id_registro;
	@JsonProperty("path")
	String  path;
	//@JsonProperty("token")
	//String  token;
	@JsonProperty("md5")
	String  md5;
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public Integer getId_conferenza() {
		return id_conferenza;
	}
	public void setId_conferenza(Integer id_conferenza) {
		this.id_conferenza = id_conferenza;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Integer getId_documento() {
		return id_documento;
	}
	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}
	public String getNome_documento() {
		return nome_documento;
	}
	public void setNome_documento(String nome_documento) {
		this.nome_documento = nome_documento;
	}
	public Integer getId_registro() {
		return id_registro;
	}
	public void setId_registro(Integer id_registro) {
		this.id_registro = id_registro;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	//public String getToken() {
	//	return token;
	//}
	//public void setToken(String token) {
	//	this.token = token;
	//}	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public static MediatecaDTO fillMediatecaDTO(Mediateca model) {
		MediatecaDTO dto=new MediatecaDTO();
		dto.setId_conferenza(model.getId_conferenza());
		dto.setId_documento(model.getId_documento());
		dto.setId_registro(model.getId_registro());
		dto.setNome_documento(model.getNome_documento());
		dto.setOggetto(model.getOggetto());
		dto.setPath(model.getPath());
		//dto.setToken(model.getToken());
		dto.setMd5(model.getMd5());
		
		return dto;
	}

	public static List<MediatecaDTO> fillMediatecaDTO(List<Mediateca> model) {
		List<MediatecaDTO>  listadto=new ArrayList<MediatecaDTO>();
		for(Mediateca item: model) {
			MediatecaDTO dto=fillMediatecaDTO(item);
			listadto.add(dto);			
		}
		return listadto;
	}
	
}
