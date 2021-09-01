package conferenza.security;

import org.springframework.stereotype.Component;

import conferenza.model.Conferenza;

@Component("creatore_cds_permission_strategy")
public class CreatoreCDSStrategy  extends  DefaultPermissionStrategy{

		
	@Override
	public Boolean isAbilitato_INSERT_EVENTO_DETERMINAZIONE_FINALE(Conferenza conferenza) {	
		return true;
	}
	
}
