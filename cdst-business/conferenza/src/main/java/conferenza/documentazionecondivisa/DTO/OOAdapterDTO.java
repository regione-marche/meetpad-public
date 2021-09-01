package conferenza.documentazionecondivisa.DTO;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.documentazionecondivisa.OOConfiguration;
import conferenza.documentazionecondivisa.model.OOAdapter;


/**
 * 
 * @author guideluc
 *
 */
public class OOAdapterDTO {
	

	
	@JsonProperty("id")
	Integer  id;
	@JsonProperty("fk_registro")
	Integer  fk_registro;
	@JsonProperty("id_doc_oo")
	String   id_doc_oo;
	@JsonProperty("id_folder_oo")
	String   id_folder_oo;
	@JsonProperty("id_user_oo")
	String   id_user_oo;
	@JsonProperty("fk_documento")
	Integer  fkDocumento;
	

	
	public Integer getFkDocumento() {
		return fkDocumento;
	}
	public void setFkDocumento(Integer fkDocumento) {
		this.fkDocumento = fkDocumento;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFk_registro() {
		return fk_registro;
	}
	public void setFk_registro(Integer fk_registro) {
		this.fk_registro = fk_registro;
	}
	public String getId_doc_oo() {
		return id_doc_oo;
	}
	public void setId_doc_oo(String id_doc_oo) {
		this.id_doc_oo = id_doc_oo;
	}
	public String getId_folder_oo() {
		return id_folder_oo;
	}
	public void setId_folder_oo(String id_folder_oo) {
		this.id_folder_oo = id_folder_oo;
	}
	public String getId_user_oo() {
		return id_user_oo;
	}
	public void setId_user_oo(String id_user_oo) {
		this.id_user_oo = id_user_oo;
	}
	
	

	
	public static OOAdapter fillOOAdapter( OOAdapterDTO adapterDTO){
		OOAdapter adapter= new OOAdapter();
		adapter.setId(adapterDTO.getId());
		adapter.setFk_registro(adapterDTO.getFk_registro());
		adapter.setId_doc_oo(adapterDTO.getId_doc_oo());
		adapter.setId_folder_oo(adapterDTO.getId_folder_oo());
		adapter.setId_user_oo(adapterDTO.getId_user_oo());
		adapter.setFkDocumento(adapterDTO.getFkDocumento());
		return adapter;
	}
	
}
