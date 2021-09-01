package conferenza.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
public class DocumenAop {

	@AfterReturning("execution(* *..*..DocumentFacade.uploadDocumento(..)) && args(idDocumento, file, idResponsabileCST)")
	public void invioMail(String idDocumento, MultipartFile file, String idResponsabileCST){
		
		//implementare l'invio della mail come destinatario il responsabile CST ricavable dal suo id
		
		System.out.println("inviao mail");
	}
}
