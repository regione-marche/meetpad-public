package conferenza.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReportParameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Integer id;
	@Column(name = "FK_REPORT")
	Integer fkReport;
	@Column(name = "PARAMETRONOME")
	String nome;
	@Column(name = "PARAMETROTIPO")
	String tipo;
	@Column(name = "PARAMETRODEFAULT")
	String parametrodefault;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFkReport() {
		return fkReport;
	}
	public void setFkReport(Integer fkReport) {
		this.fkReport = fkReport;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getParametrodefault() {
		return parametrodefault;
	}
	public void setParametrodefault(String parametrodefault) {
		this.parametrodefault = parametrodefault;
	}		
	
	
}
