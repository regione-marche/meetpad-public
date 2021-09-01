package conferenza.adapder.integrazioni.indicepa.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class IndicePaClientAdapter {

	private static final Logger logger = LogManager.getLogger(IndicePaClientAdapter.class.getName());
	
	
	private static String USER_AUTH="AUTH_ID=BNAKWRLI";
	private static final String USER_AGENT = "Mozilla/5.0";
	private static  String GET_URL = null;
	private String POST_URL = null;
	private String POST_URL_ROOT = "https://www.indicepa.gov.it/public-ws/";
	private String POST_IS_FindInIndicePA="WS16_DES_AMM.php"; 
	private String POST_IS_SelectAmmInIndicePA="WS05_AMM.php"; 
	private String POST_PARAMS = "userName=Pankaj";
	private String POST_PARAMS_FindInIndicePA = "DESCR";
	private String POST_PARAMS_SelectAmmInIndicePA = "COD_AMM";
	
	
	
	public void init(){
				
	}
	
	
	public void  fillPOSTParametrFindInIndicePA(String par1){
		POST_PARAMS=USER_AUTH;		
		POST_PARAMS+="&"+POST_PARAMS_FindInIndicePA+"="+par1;		
	}
	
	private void fillURLPOSTFindInIndicePA(){
		this.POST_URL=POST_URL_ROOT+POST_IS_FindInIndicePA;
	}
	

	
	/**
	 * Data la stringa di ricerca ottiene la lista delle amministrazioni filtrate.. 
	 * @param par1
	 * @throws IOException
	 */
	public  String sendPOSTFindInIndicePA(String par1) throws IOException {
		//inizializza l'URL per l'inidice PA
		fillURLPOSTFindInIndicePA();
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		
		//------------------------------------------------
		// For POST only - START
		fillPOSTParametrFindInIndicePA(par1);
		//------------------------------------------------		
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		logger.debug("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			logger.debug(response.toString());
			
			return response.toString();
			
		} else {
			logger.debug("POST request not worked");
			return  "POST request not worked";						
		}
	}
	
	
	
	/**
	 * 
	 * @param par1
	 */
	public void  fillPOSTSelectAmmInIndicePA(String par1){
		POST_PARAMS=USER_AUTH;		
		POST_PARAMS+="&"+POST_PARAMS_SelectAmmInIndicePA+"="+par1;		
	}
	
	private void fillURLPOSTSelectAmmInIndicePA(){
		this.POST_URL=POST_URL_ROOT+POST_IS_SelectAmmInIndicePA;
	}	
	
	/**
	 * Data la stringa di ricerca ottiene la lista delle amministrazioni filtrate.. 
	 * @param par1
	 * @throws IOException
	 */
	public  String sendPOSTSelectAmmInIndicePA(String par1) throws IOException {
		//inizializza l'URL per l'inidice PA
		fillURLPOSTSelectAmmInIndicePA();
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		
		//------------------------------------------------
		// For POST only - START
		fillPOSTSelectAmmInIndicePA(par1);
		//------------------------------------------------		
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		logger.debug("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			logger.debug(response.toString());
			
			return response.toString();
			
		} else {
			logger.debug("POST request not worked");
			return  "POST request not worked";						
		}
	}
	
}
