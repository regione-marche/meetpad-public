package conferenza.security;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conferenza.model.Conferenza;
import conferenza.model.PermessoAzione;
import conferenza.service.PermessoService;

public class PermissionStrategy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionStrategy.class);

	public Boolean isAbilitato(PermessoAzione permessoAzione, Conferenza conferenza) {
		Boolean ret = false;
		Method method;
		String methodName = "isAbilitato_" + permessoAzione.getCodice();
		
		LOGGER.debug("PermissionStrategy - methodName: " + methodName);
		try {
			method = this.getClass().getMethod(methodName, Conferenza.class);
			LOGGER.debug("PermissionStrategy - method: " + method);
	        if (method != null) {
	            ret = (Boolean) method.invoke(this, conferenza);
	            LOGGER.debug("PermissionStrategy - ret: " + ret);
	        }  			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
