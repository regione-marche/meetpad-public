package cdst_be_marche.model;

import javax.persistence.Entity;

import cdst_be_marche.model.bean.Typological;

/**
 * Tipologia di Profilo da cui dipendono anche le abilitazioni. 
 * Le tipologie previste sono le seguenti: 
 * CREATORE CDS, RESPONSABILE CDS, AMMINISTRATORE DI SISTEMA, PARTECIPANTE
 * 
 * @author arosina
 *
 */
@Entity
public class TipoProfilo extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235557326924241749L;

}
