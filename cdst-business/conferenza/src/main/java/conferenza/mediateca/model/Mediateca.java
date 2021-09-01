package conferenza.mediateca.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "view_mediateca")
public class Mediateca    implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_conferenza")	
	Integer id_conferenza;
	@Column(name ="oggetto")
	String  oggetto;
	@Column(name ="id_documento")
	Integer id_documento; 
	@Column(name ="nome_documento")
	String  nome_documento;
	@Column(name ="id_registro")
	Integer id_registro;
	@Column(name ="path")
	String  path;
	@Column(name ="token")
	String  token;
	@Column(name ="md5")
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
