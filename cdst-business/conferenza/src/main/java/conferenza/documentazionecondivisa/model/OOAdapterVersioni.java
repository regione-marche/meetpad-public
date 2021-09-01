package conferenza.documentazionecondivisa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oo_adapter_versioni")
public class OOAdapterVersioni   implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Integer id;
	
	@Column(name ="TOKEN")
	private String TOKEN;
	
	@Column(name ="URL_OO")
	private String urlOO;

	@Column(name ="ID_USER_OO")
	private String idUserOO;
	
	@Column(name ="FK_ADAPTER")
	private Integer fkAdapter;
	
	@Column(name ="JWT")
	private String jwt;
	
	@Column(name ="ID_OO_FILE")
	private Integer idOOFile;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}

	public String getUrlOO() {
		return urlOO;
	}

	public void setUrlOO(String urlOO) {
		this.urlOO = urlOO;
	}

	public String getIdUserOO() {
		return idUserOO;
	}

	public void setIdUserOO(String idUserOO) {
		this.idUserOO = idUserOO;
	}

	public Integer getFkAdapter() {
		return fkAdapter;
	}

	public void setFkAdapter(Integer fkAdapter) {
		this.fkAdapter = fkAdapter;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Integer getIdOOFile() {
		return idOOFile;
	}

	public void setIdOOFile(Integer idOOFile) {
		this.idOOFile = idOOFile;
	}



	
	
	
}
