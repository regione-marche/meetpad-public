package cdst_be_marche.adapder.alfresco;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.context.annotation.Lazy
@Component
@Transactional
public class AlfrescoHttpClient {
	private static final Logger logger = LogManager.getLogger(AlfrescoHttpClient.class.getName());

	public static final String UTF_8_ENCODING = "UTF-8";

	public static final String MIME_TYPE_JSON = "application/json";

	public static final String ALFRESCO_API_PATH = "alfresco/service/api/";

	public static final String ALFRESCO_API_VERSION = "-default-/public/alfresco/versions/1/";

	private static final String ERROR_WHILE_EXECUTING_REQUEST = "Error while executing request";

	private CloseableHttpClient client;

	private String apiUrl;

	@Value("${alfresco.superadmin.baseurl}")
	private String baseUrl;

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getApiVersionUrl() {
		String versionApi = getApiUrl().replace("/service", StringUtils.EMPTY);
		return versionApi + ALFRESCO_API_VERSION;
	}

	/**
	 * Generates an Alfresco Authentication Ticket based on the user name and
	 * password passed in.
	 *
	 * @param username
	 *            user identifier
	 * @param password
	 *            user password
	 * @throws ParseException
	 *             if error
	 * @throws IOException
	 *             if error
	 * @return Sting authentication ticket
	 */
	public String getAlfTicket(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("Username and password are required");
		}
		String targetUrl = getApiUrl() + "login?u=" + username + "&pw=" + password + "&format=json";
		try {
			URL url = new URL(getApiUrl() + "login?u=" + username + "&pw=" + password + "&format=json");
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? UTF_8_ENCODING : encoding;
			String json = IOUtils.toString(in, encoding);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			JSONObject data = (JSONObject) obj.get("data");
			return (String) data.get("ticket");
		} catch (IOException | ParseException e) {
			if (e != null) {
				logger.error(e);
			}
			logger.error(String.format("Unable to generate ticket, url: %s", targetUrl), e);
		}
		throw new RuntimeException("Unable to get ticket");
	}

	/**
	 * Get the alfresco version
	 *
	 * @return String version of alfresco
	 * @throws Exception
	 *             if error
	 */
	public String getAlfrescoVersion() throws Exception {
		String url = getApiUrl() + "server";
		HttpGet get = new HttpGet(url);
		HttpResponse response = getClient().execute(get);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			try {
				String jsonString = EntityUtils.toString(response.getEntity());
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(jsonString);
				JSONObject data = (JSONObject) obj.get("data");
				return (String) data.get("version");
			} catch (IOException | ParseException e) {
				if (e != null) {
					logger.error(e);
				}
				return StringUtils.EMPTY;
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Populate HTTP message call with given content.
	 *
	 * @param json
	 *            {@link JSONObject} content
	 * @return {@link StringEntity} content.
	 * @throws UnsupportedEncodingException
	 *             if unsupported
	 */
	public StringEntity setMessageBody(final JSONObject json) throws UnsupportedEncodingException {
		if (json == null || json.toString().isEmpty()) {
			throw new IllegalArgumentException("JSON Content is required.");
		}
		StringEntity se = new StringEntity(json.toString(), UTF_8_ENCODING);
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, MIME_TYPE_JSON));
		logger.debug("Json string value: " + se);
		return se;
	}

	/**
	 * Execute HttpClient request.
	 *
	 * @param request
	 *            to send
	 * @return {@link HttpResponse} response
	 * @throws Exception
	 *             if error
	 */
	public HttpResponse executeRequest(HttpRequestBase request) {
		HttpResponse response = null;
		try {
			response = getClient().execute(request);
			if (logger.isTraceEnabled()) {
				logger.trace("Status Received:" + response.getStatusLine());
			}
			return response;
		} catch (Exception e) {
			logger.error(response);
			throw new RuntimeException(ERROR_WHILE_EXECUTING_REQUEST, e);
		}
	}

	/**
	 * Execute HttpClient request.
	 *
	 * @param client
	 *            AlfrescoHttpClient client
	 * @param userName
	 *            String user name
	 * @param password
	 *            String password
	 * @param url
	 *            String api url
	 * @param request
	 *            HttpRequestBase the request
	 * @return {@link HttpResponse} response
	 * @throws Exception
	 *             if error
	 */
	public HttpResponse executeRequest(AlfrescoHttpClient client, final String userName, final String password,
			HttpRequestBase request) throws Exception {
		HttpResponse response = null;
		HttpClient clientWithAuth = client.getHttpClientWithBasicAuth(userName, password);
		return getResponse(client, request, response, clientWithAuth);
	}

	/**
	 * Execute HttpClient POST OR PUT
	 *
	 * @param client
	 *            AlfrescoHttpClient client
	 * @param userName
	 *            String user name
	 * @param password
	 *            String password
	 * @param url
	 *            String api url
	 * @param body
	 *            JSONObject body of the request
	 * @param request
	 *            HttpEntityEnclosingRequestBase the request
	 * @return {@link HttpResponse} response
	 * @throws Exception
	 *             if error
	 */
	public HttpResponse executeRequestWithEntity(AlfrescoHttpClient client, final String userName,
			final String password, final JSONObject body, HttpEntityEnclosingRequestBase request) throws Exception {
		HttpResponse response = null;
		HttpClient clientWithAuth = client.getHttpClientWithBasicAuth(userName, password);
		return getResponseWithEntity(client, request, response, clientWithAuth, body);
	}

	private HttpResponse getResponse(AlfrescoHttpClient client, HttpRequestBase request, HttpResponse response,
			HttpClient clientWithAuth) {
		try {
			response = clientWithAuth.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new RuntimeException("Invalid user name or password");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error(response);
			throw new RuntimeException(ERROR_WHILE_EXECUTING_REQUEST, e);
		} finally {
			request.releaseConnection();
			client.close();
		}
		return response;
	}

	private HttpResponse getResponseWithEntity(AlfrescoHttpClient client, HttpEntityEnclosingRequestBase request,
			HttpResponse response, HttpClient clientWithAuth, JSONObject body) {
		try {
			if (body != null) {
				try {
					request.setEntity(setMessageBody(body));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("Body content error: ", e);
				}
			}

			response = clientWithAuth.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				throw new RuntimeException("Invalid user name or password");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error(response);
			throw new RuntimeException(ERROR_WHILE_EXECUTING_REQUEST, e);
		} finally {
			request.releaseConnection();
			client.close();
		}
		return response;
	}

	/**
	 * Get basic http client with basic credential.
	 *
	 * @param username
	 *            String username
	 * @param password
	 *            String password
	 * @return {@link HttpClient} client
	 */
	public HttpClient getHttpClientWithBasicAuth(String username, String password) {
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
		return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}

	/**
	 * Parses http response stream into a {@link JSONObject}.
	 *
	 * @param entity
	 *            Http response entity
	 * @return {@link JSONObject} response
	 */
	public JSONObject readStream(final HttpEntity entity) {
		String rsp = null;
		try {
			rsp = EntityUtils.toString(entity, UTF_8_ENCODING);
		} catch (Exception ex) {
			throw new RuntimeException("Failed to read HTTP entity stream.", ex);
		} finally {
			EntityUtils.consumeQuietly(entity);
		}
		try {
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(rsp);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert response to JSON: \n" + "   Response: \r\n" + rsp, e);
		}
	}

	/**
	 * Method to get parameters from JSON
	 *
	 * @param result
	 *            json message
	 * @param param
	 *            key identifier
	 * @return String value of key
	 * @throws ParseException
	 *             if error parsing
	 */
	public String getParameterFromJSON(String result, String param) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(result);
		return (String) obj.get(param);
	}

	/**
	 * Get the elements from an JSONArray
	 *
	 * @param response
	 *            HttpResponse response
	 * @param array
	 *            String the name of the array
	 * @param elementFromArray
	 *            element from array
	 * @return List<String> elements found in the array
	 * @throws ParseException
	 * @throws IOException
	 */
	public List<String> getElementsFromJsonArray(HttpResponse response, String array, String elementFromArray)
			throws ParseException, IOException {

		List<String> elements = new ArrayList<>();
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, UTF_8_ENCODING);
		Object obj = JSONValue.parse(responseString);
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray jArray = (JSONArray) jsonObject.get(array);
		for (Object item : jArray) {
			JSONObject jobject = (JSONObject) item;
			elements.add(jobject.get(elementFromArray).toString());
		}

		return elements;
	}

	/**
	 * Closes the HttpClient.
	 *
	 * @throws IOException
	 *             if error
	 */
	public void close() {
		try {
			getClient().close();
		} catch (IOException e) {
			logger.error("Unable to close http client", e);
		}
	}

	public CloseableHttpClient getClient() {
		if (client == null) {
			client = HttpClientBuilder.create().build();
			final SSLContext sslcontext = SSLContexts.createSystemDefault();
			final HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();

			final Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslcontext, hostnameVerifier)).build();

			final PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			manager.setMaxTotal(50);

			client = HttpClients.custom().setConnectionManager(manager).setConnectionManagerShared(true).build();
		}

		return client;
	}

	public String getApiUrl() {

		if (apiUrl == null) {
			apiUrl = String.format("%s%s", baseUrl, ALFRESCO_API_PATH);
		}

		return apiUrl;
	}

}
