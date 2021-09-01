package conferenza.firma.adapter;

import org.springframework.core.io.Resource;

import conferenza.firma.DTO.FirmaDTO;

/**
 * tuute le classi che vengono usate per riflessione 
 * debbono implementare questa intercaccia
 * 
 *
 * 
 * @author guideluc
 *
 */
public interface IFirmaAdapter {

	public FirmaDTO firma(FirmaDTO par1);
	
	public Resource getFile(FirmaDTO par1);
	
}
