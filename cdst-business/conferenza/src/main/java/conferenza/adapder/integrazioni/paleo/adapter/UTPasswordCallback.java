package conferenza.adapder.integrazioni.paleo.adapter;


import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class UTPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			WSPasswordCallback wpc = (WSPasswordCallback) callback;
			if (wpc.getIdentifier().equals("R_MARCHE\\INF.POINT")) {
				wpc.setPassword("paleo");
				return;
			}
		}

	}

}
