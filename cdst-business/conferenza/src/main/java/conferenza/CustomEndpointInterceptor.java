package conferenza;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

@Component
public class CustomEndpointInterceptor implements ClientInterceptor  {

    //private static final Log LOG = LogFactory.getLog(CustomEndpointInterceptor.class);

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		//HttpLoggingUtils.logMessage("Client Request Message", messageContext.getRequest());
		System.out.println("Client Request Message: " + messageContext.getRequest());
		
		return false;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
		// TODO Auto-generated method stub
		
	}

}