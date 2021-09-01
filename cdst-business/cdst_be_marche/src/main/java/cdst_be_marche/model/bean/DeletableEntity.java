package cdst_be_marche.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Where;

@MappedSuperclass
public class DeletableEntity extends AuditEntity {
	
	@Column(name="DATA_FINE")
	private Date dataFine;

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	

}
