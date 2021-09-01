package conferenza.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
/*
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
*/

import conferenza.controller.DocumentsController;
import conferenza.util.model.TokenJWT;
import conferenza.util.model.TokenJWT_min;
import conferenza.util.model.TokenWSO2JWT;
import conferenza.util.model.TokenWSO2JWT_SUAP;

@Service
@ConfigurationProperties(prefix = "jwt")
public class JWTsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsController.class);
	private static final String idToken = "App-Id-Token";
	public static final String CUSTOM_AUTHORIZATION = "x-jwt-assertion";

	
	/**
access_token = NGNmZmJjYjAtZmJkMi0zOGY4LTliZjUtYjJlYjU3NmQxYWI0OkRMQ0dEVTcxRDE2QzYzMlNAY2FyYm9uLnN1cGVy
ovvero..
4cffbcb0-fbd2-38f8-9bf5-b2eb576d1ab4:DLCGDU71D16C632S@carbon.super
	 * @param input
	 * @return
	 */
	private  boolean isJWT(String input) {
		boolean isjwt=false;
		String[] splitte = input.split("[.]");
		if (splitte != null && splitte.length > 0) {
			isjwt=true;
		}		
		return isjwt;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	public boolean isJWT(HttpServletRequest request) {	
		String token = request.getHeader(idToken);
		return isJWT(token);	
	}
	
	public String getJWT(HttpServletRequest request) {		
		String jwt=null;
		jwt=request.getHeader(idToken) != null ? request.getHeader(idToken) : request.getHeader(CUSTOM_AUTHORIZATION);
        if(jwt==null) {
        	jwt = request.getHeader("Authorization");
        	if(jwt!=null)
        	if(jwt.lastIndexOf("Bearer")>=0 )
        		jwt=jwt.replaceAll("Bearer ", "");
        	else if(jwt.lastIndexOf("BEARER")>=0)
        	    jwt=jwt.replaceAll("BEARER ", "");

        }	
		return jwt;
	}	
	/**
access_token = NGNmZmJjYjAtZmJkMi0zOGY4LTliZjUtYjJlYjU3NmQxYWI0OkRMQ0dEVTcxRDE2QzYzMlNAY2FyYm9uLnN1cGVy
ovvero..
4cffbcb0-fbd2-38f8-9bf5-b2eb576d1ab4:DLCGDU71D16C632S@carbon.super
	 * @param input
	 * @param inputToken
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getCodiceFiscalenotJWTToken(String inputToken) {
		String inputDecoded;
		try {
			inputDecoded = decodeB64(inputToken);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		String[] parsed1=null;
		String sub1=null;
		String sub2=null;
		String cf=null;
		if(inputDecoded.lastIndexOf("[:]")<=0)
			return cf;
		else {			
			parsed1=inputDecoded.split("[:]");
		}
		if (parsed1 != null && parsed1.length > 0) {
			sub1=parsed1[1];
		}
		if(sub1 == null)
			return cf;
				
		if(sub1 != null) {
			if(sub1.lastIndexOf("[@]")>0) {
				parsed1=inputDecoded.split("[@]");
				sub2=parsed1[0];				
				cf=sub2;
			}else {
				cf=sub1;
			}	
		}		
		return cf;
	}
	
	
	public String getCodiceFiscaleFromInput(String input) {
		String codice_fiscale = null;
		String[] splitte = input.split("[.]");
		String jwts = null;
		if (splitte != null && splitte.length > 0) {
			jwts = splitte[1];
		}
		try {
			codice_fiscale = decodeB64(jwts);
			String cf = getCodiceFiscale(codice_fiscale);
			codice_fiscale = cf;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codice_fiscale;
	}

	/**
	 * DLG..
	 * esigenza di fare il parse del token passato come stringa....
	 * @param token
	 * @return
	 */
	public String getCodiceFiscaleFromTokenString(String token) {
		String codiceFiscale=null;
		if (isJWT(token)) {
			codiceFiscale= getCodiceFiscaleFromAssertionInput(token, tokenWSO2JWTClass);
			if(codiceFiscale==null || codiceFiscale.length()>16)
				codiceFiscale= getCodiceFiscaleFromInput(token);
		}else
			codiceFiscale= getCodiceFiscalenotJWTToken(token);		
		return codiceFiscale;
	}
	
	/**
	 * DLG : 20190228
	 * gestione anomalia nella gestione dell'access token
	 * Se è presente l'header App-Id-Token il CF è estratto con il metodo getCodiceFiscaleFromInput
	 * altrimenti se è presente l'header customauthorization il CF è estratto con il metodo getCodiceFiscaleFromAssertionInput
	 * 
	 * questa logica garantisce il funzionamento anche quando non sarà più presente l'header App-Id-Token
	 * 
	 * @param request
	 * @return
	 */
	public String getCodiceFiscaleFromToken(HttpServletRequest request) {
		
		String token = request.getHeader(idToken);
		String codiceFiscale = null;
		if (token != null) {
			if (isJWT(token))
				codiceFiscale = getCodiceFiscaleFromInput(token);
			else
				codiceFiscale = getCodiceFiscalenotJWTToken(token);
		} else {
			token = request.getHeader(CUSTOM_AUTHORIZATION);
			if (token != null) {
				if (isJWT(token))
					codiceFiscale = getCodiceFiscaleFromAssertionInput(token, tokenWSO2JWTClass);
				else
					codiceFiscale = getCodiceFiscalenotJWTToken(token);
			}
		}
		if(codiceFiscale != null && codiceFiscale.startsWith("TINIT-")) {
			codiceFiscale = codiceFiscale.replaceFirst("TINIT-", "");
		}
		
		return codiceFiscale;				
	}
	
	@Value("${jwt.tokenWSO2JWTClass}")
	private String tokenWSO2JWTClass;

	private static String getCodiceFiscaleFromAssertionInput(String input, String tokenWSO2JWTClass2) {
		String codice_fiscale = null;
		String[] splitte = input.split("[.]");
		String jwts = null;
		if (splitte != null && splitte.length > 0) {
			jwts = splitte[1];
		}
		try {
			codice_fiscale = decodeB64(jwts);
			String cf = getCodiceFiscaleXJwtAssertion(codice_fiscale, tokenWSO2JWTClass2);
			codice_fiscale = cf;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return codice_fiscale;
	}
	
	public static void main(String[] args) {
		//String input = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6ImZpeGNfU25rTGItUVlwN1lIeFE1YVVwa19OOCJ9.eyJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3ZlcnNpb24iOiIxLjAiLCJpc3MiOiJ3c28yLm9yZ1wvcHJvZHVjdHNcL2FtIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvYXBwbGljYXRpb25uYW1lIjoiTWVldFBBRCIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2VuZHVzZXIiOiJSU05ORFI3NEUxMUw3MTlJQGNhcmJvbi5zdXBlciIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2VuZHVzZXJUZW5hbnRJZCI6Ii0xMjM0IiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvc3Vic2NyaWJlciI6ImFkbWluIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvdGllciI6IlVubGltaXRlZCIsImV4cCI6MTU0MzMzNDY1NiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvYXBwbGljYXRpb25pZCI6IjIiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC91c2VydHlwZSI6IkFQUExJQ0FUSU9OX1VTRVIiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcGljb250ZXh0IjoiXC9jZHN0X2JlX21hcmNoZVwvMS4wIn0=.bD1415jOAQ8H1AlF7JJeUp3tX2j2U5uQzruC9Gzb7dOuh9YgXh1ohJtr6AvX2jkt3DaHmNDIr7hUIXvyTy3a6Zl2hkjmQjoENBD7+pdCkAc3UPOyZucie4yTbgMRhtYKOcipqwWy7F6IU6SzHqd/UcEVeooEVBoKwPEYn6o0k0D4nG/yl8RAlJlvy79PqOGjWSK1lqbjzdfasnDUsK1JidXgEcgh2+jxGXmXi3KGku0jzlNFqIbcrUwY2y8At8hDZJQoQLfyoKJpEo3e37k+8iZ+OT/eCqK2IT3GFwQFrIZzSnhbjnhZrTGZD5oXbwwzyP/3Iq8HY2DJs9Nf3XhAsQ==";
		String input = "eyJ4NXQiOiJNVE0xTkRZd016TTRPREprTURReVpHSTVOV05rTldOa1pUQXdPRFV5TUdaaE5XTmxPR1ZtTnciLCJraWQiOiJNVE0xTkRZd016TTRPREprTURReVpHSTVOV05rTldOa1pUQXdPRFV5TUdaaE5XTmxPR1ZtTnciLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJCTkNNUkE4MEEwMUIzNTRDIiwiYXVkIjoiWUF1NEV5a085dTgxTHEzdVdMYUMzR0diVWhRYSIsImF6cCI6IllBdTRFeWtPOXU4MUxxM3VXTGFDM0dHYlVoUWEiLCJzY29wZSI6Im9wZW5pZCIsImlzcyI6Imh0dHBzOlwvXC93c28yLmxhY3ItZGV2LmVuZzo5NDQzXC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNTQ2OTcyMzM1LCJpYXQiOjE1NDY5Njg3MzUsImp0aSI6IjYwZGEzZDNjLWI3YmYtNGE2OC05ZjdiLWYzMmVlMWFiOGRhNyJ9.I145ajHE_WXzrajElxRWuOg_f-ytB-CgvODsNGiJvDYtjt-gyo_jv2OYMqAmQp-blvAAsh2q1Mx7BUhT2_J7xCqJz1xnof8vsbN-D5tfScs4OfIE6B--mkCdJvVI5iORzyKcXGADceCi8wIZ3SdK3l4rcAucTWDHoSDasDElDPqaiacewuxFcvHG8DCnDpkoVEOCyIa8b_tnuS6NMxUrt9dvN4hD1F5G9oYNsyOK07lkwThUmd8sKlhdxC6vFEFhTfe1LVulvakYOHgNJ80QcXTEsEnTRo1sBFJ76xrBkizj77JugBb2WX-G_VTsA1l5Qz7BvoATz5D1DFXxGBfeRg";
		String input2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IkUxUmdNNGd0QkMyNVhOWE40QWhTRDZYT2p2YyJ9.eyJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC91c2VyaWQiOiIyMmExMmE3Ny1mMTYxLTQ1ZDYtOGRkNC1kMzkxYzg0NzM5YjkiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC91c2VybmFtZSI6IkRMQ0dEVTcxRDE2QzYzMlMiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9yb2xlIjoiSW50ZXJuYWxcL2V2ZXJ5b25lIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvYXBwbGljYXRpb250aWVyIjoiVW5saW1pdGVkIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wva2V5dHlwZSI6IlBST0RVQ1RJT04iLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC92ZXJzaW9uIjoiMS4wIiwiaXNzIjoid3NvMi5vcmdcL3Byb2R1Y3RzXC9hbSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9ubmFtZSI6Ik1lZXRQYWRTdWFwIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvZW5kdXNlciI6IkRMQ0dEVTcxRDE2QzYzMlNAY2FyYm9uLnN1cGVyIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvZW5kdXNlclRlbmFudElkIjoiLTEyMzQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9yZXNvdXJjZVR5cGUiOiJVc2VyIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvY3JlYXRlZCI6IjIwMTgtMTItMThUMTg6MTQ6MDhaIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvbW9kaWZpZWQiOiIyMDE4LTEyLTE4VDE4OjE0OjA4WiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3N1YnNjcmliZXIiOiJhZG1pbiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3RpZXIiOiJVbmxpbWl0ZWQiLCJleHAiOjE1NDU0MDQ2ODMsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9uaWQiOiIzIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvYXBpY29udGV4dCI6IlwvY2RzdF9iZV9zdWFwXC8xLjAiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC91c2VycHJpbmNpcGFsIjoiRExDR0RVNzFEMTZDNjMyUyJ9.a+0GGeQrL/RZ8lXwSrL1104hJZw1xfGkQ5VyFyrAMpImNQjxvRbSz/3WNwQ+18mndsKBinf1JlDOYfl+NRnAo3qy6rOkQMNKOoazAucZI6SLNMNOxtyZOXwDZpSUBgrQk3qjTReBdb7wUXsQ2CoNOgHOVbNnNa4YPGxGEg0z2mfq/QfhkxCbRU+fmoXZVbwEVFt2m9Ev6aHsYm+AcbfG2lumpciNVER7Udv3fj2iMsKdoxTAbARM39WdKE9FletEEfrmBUrp70Fk3KoPkJg7wYqaiPJtHKUNsQoazzif5sJw3AFRsd4In9p2zPQQ8bOrAebqmEvknFd0tpSOtfWbOg==";
		String cf = getCodiceFiscaleFromAssertionInput(input, "TokenJWT_min");
		System.out.println(cf);
	}

	/**
	 * @param jwts
	 * @throws UnsupportedEncodingException
	 */
	private static String decodeB64(String jwts) throws UnsupportedEncodingException {
		byte[] cdecoded = Base64.getDecoder().decode(jwts);
		String out = new String(cdecoded, "UTF-8");
		LOGGER.debug("out: " + out);
		return out;
	}

	/**
	 * 
	 * @param sJson
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private String getCodiceFiscale(String sJson) throws JsonParseException, JsonMappingException, IOException {
		TokenJWT jwts = TokenJWT.getObj(sJson);
		return jwts.getSub();
	}
	
	private static String getCodiceFiscaleXJwtAssertion(String sJson, String tokenWSO2JWTClass)
			throws JsonParseException, JsonMappingException, IOException {
		if (tokenWSO2JWTClass.equals("TokenWSO2JWT")) {
			TokenWSO2JWT jwts = TokenWSO2JWT.getObj(sJson);
			String[] endUserSplit = jwts.getEnduser().split("@");
			return endUserSplit[0];
		}
		else if (tokenWSO2JWTClass.equals("TokenWSO2JWT_SUAP")) {
			TokenWSO2JWT_SUAP jwts = TokenWSO2JWT_SUAP.getObj(sJson);
			String[] endUserSplit = jwts.getEnduser().split("@");
			return endUserSplit[0];
		}
		else if (tokenWSO2JWTClass.equals("TokenJWT_min")) {
			TokenJWT_min jwts = TokenJWT_min.getObj(sJson);
			if (jwts.getSub() != null) {
				return jwts.getSub();
			}
			else if (jwts.getEnduser() != null) {
				String[] endUserSplit = jwts.getEnduser().split("@");
				return endUserSplit[0];	
			}
			else if (jwts.getUsername() != null) {
				return jwts.getUsername();
			}
			else return null;	
		}
		else return null;
	}

	RSAPublicKey publicKey = null; // Get the key instance
	RSAPrivateKey privateKey = null; // Get the key instance
	@Value("${jwt.urlpublickey}")
	private String PUBLIC_KEY_FILE_256;// "src/main/resources/certificates/PublicAOuthCertified.pem";
	


	String errorMsg = null;

	/**
	 * @param pJwtToken
	 * @return
	 * @throws Exception
	 */
	public boolean checkToken(String pJwtToken) throws Exception {
		return verifyToken(pJwtToken);
	}

	public boolean checkToken(HttpServletRequest request) throws Exception {
		return verifyToken(request.getHeader(idToken) != null ? request.getHeader(idToken) : request.getHeader(CUSTOM_AUTHORIZATION));
	}

	private PublicKey getPemPublicKey(String filename, String algorithm) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();

		String temp = new String(keyBytes);
		String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
		publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

		byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);

		X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		return kf.generatePublic(spec);

	}

	private static RSAPublicKey getPublicKeyFromFile(String filename) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		File f = new File(filename);
		InputStream is = new FileInputStream(f.getAbsolutePath());
		X509Certificate certificate = (X509Certificate) cf.generateCertificate(is);
		is.close();
		RSAPublicKey pubKey = (RSAPublicKey) certificate.getPublicKey();
		return pubKey;
	}

	/**
	 * 
	 * @param pJwtToken
	 */
	public void dumpJWT(String pJwtToken) {
		final DecodedJWT decodedJwt = JWT.decode(pJwtToken);
		LOGGER.debug("Header =  " + decodedJwt.getHeader());
		LOGGER.debug("Algorithm =  " + decodedJwt.getAlgorithm());
		LOGGER.debug("Audience =  " + decodedJwt.getAudience());
		decodedJwt.getClaims().forEach((k, v) -> {
			LOGGER.debug("Claim " + k + " = " + v.asString());
		});
		LOGGER.debug("ContentType =  " + decodedJwt.getContentType());
		LOGGER.debug("ExpiresAt =  " + decodedJwt.getExpiresAt());
		LOGGER.debug("Id =  " + decodedJwt.getId());
		LOGGER.debug("Issuer =  " + decodedJwt.getIssuer());
		LOGGER.debug("Subject =  " + decodedJwt.getSubject());
	}

	private boolean verifyToken(String pJwtToken) throws Exception {
		boolean test = false;
		if (pJwtToken != null) {
			RSAPublicKey generatePublic = (RSAPublicKey) JWTsService.getPublicKeyFromFile(PUBLIC_KEY_FILE_256);
			Algorithm algorithm = Algorithm.RSA256(generatePublic, null);
			JWTVerifier verifier = JWT.require(algorithm)
					// .withIssuer("auth0")
					.build(); // Reusable verifier instance

			dumpJWT(pJwtToken);

			DecodedJWT jwt = verifier.verify(pJwtToken);

			BigInteger modulus = generatePublic.getModulus();
			LOGGER.debug(modulus.toString());
			BigInteger exponent = generatePublic.getPublicExponent();
			LOGGER.debug(exponent.toString());
			test = true;
		}
		return test;
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(RSAPublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(RSAPrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPUBLIC_KEY_FILE_256() {
		return PUBLIC_KEY_FILE_256;
	}

	public void setPUBLIC_KEY_FILE_256(String pUBLIC_KEY_FILE_256) {
		PUBLIC_KEY_FILE_256 = pUBLIC_KEY_FILE_256;
	}
	
}
