package conferenza.adapder.integrazioni.domus.model;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.apache.ws.commons.util.NamespaceContextImpl;


public class DomusXmlIstanza implements DomusPratica{

	private static final Logger logger = LoggerFactory.getLogger(DomusXmlIstanza.class.getName());
	
	private static DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	private static XPathFactory xpathfactory = XPathFactory.newInstance();

//	ID pratica
	private static final String IdPratica_xPath = "//*[local-name()='ModelloRCRCIL0']/@idIstanza";

	
//Oggetto Pratica!	
	private static final String  oggettoDescPratica_xPath ="//*[local-name()='ModelloRCRCIL0']//DatiGenerali/Pratica/SpeciePratica/DescrizioneSpeciePratica";
	private static final String  oggettoNumeroMude_xPath = "//*[local-name()='ModelloRCRCIL0']/@numeroMude";
	//
	private  static String  oggettoNumeroMude;
	private  static String  oggettoDescPratica;	
	private  String  oggetto;
	
	
//	Dati Comune
		
	private static final String IstatComune_xPath = "//*[local-name()='ModelloRCRCIL0']/Comune/IstatComune/text()";
	private static final String DenominazioneComune_xPath = "//*[local-name()='ModelloRCRCIL0']/Comune/DenominazioneComune/text()";	
	
//	Dati Ubicazione Intervento

	private static final String Particella_Toponomastica_xPath = "//*[local-name()='LocalizzazioneIntervento']/UbicazioneIntervento[Principale='S'][1]/Sedime/text()";
	private static final String Nome_Luogo_xPath = "//*[local-name()='LocalizzazioneIntervento']/UbicazioneIntervento[Principale='S'][1]/Via/text()";
	private static final String Civico_xPath = "//*[local-name()='LocalizzazioneIntervento']/UbicazioneIntervento[Principale='S'][1]/Civico/text()";
	private static final String Cap_xPath = "//*[local-name()='LocalizzazioneIntervento']/UbicazioneIntervento[Principale='S'][1]/Cap/text()";
	private static final String foglio_xPath = "//*[local-name()='LocalizzazioneIntervento']/RiferimentoCatastale//Foglio/text()";
	private static final String mappale_xPath = "//*[local-name()='LocalizzazioneIntervento']/RiferimentoCatastale//Mappale/text()";

//	Dati Intestatario

	private static final String Nome_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]/DatiAnagrafici//Nome/text()";
	private static final String Cognome_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]/DatiAnagrafici//Cognome/text()";
	private static final String CF_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]/DatiAnagrafici//CodiceFiscale/text()";
	private static final String pec_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]//EmailCertificata/text()";
	private static final String Comune_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]//DescrizioneComune/text()";
	private static final String Via_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]//Via/text()";
	private static final String Sedime_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]//Sedime/text()";
	private static final String Civico_Intestatario_xPath = "//*[local-name()='SoggettoCoinvolto'][RuoloSoggetto[IdRuolo='1']][1]//Civico/text()";
	

//	Dati Richiedente ..non è detto che sia un professionista!?!?
	private static final String Nome_Richiedente_xPath = "//*[local-name()='SoggettoCoinvolto'][@isProfessionista='S'][1]/DatiAnagrafici//Nome/text()";
	private static final String Cognome_Richiedente_xPath = "//*[local-name()='SoggettoCoinvolto'][@isProfessionista='S'][1]/DatiAnagrafici//Cognome/text()";
	private static final String CF_Richiedente_xPath = "//*[local-name()='SoggettoCoinvolto'][@isProfessionista='S'][1]/DatiAnagrafici//CodiceFiscale/text()";
	private static final String pec_Richiedente_xPath ="//*[local-name()='SoggettoCoinvolto'][@isProfessionista='S'][1]//EmailCertificata/text()";
	
	private String idPratica;
	private DomusSoggetto intestatario;
	private DomusSoggetto richiedente;
	private DomusUbicazioneIntervento ubicazioneIntervento;

	private DomusXmlIstanza() {
		super();
	}

	
	private DomusXmlIstanza(String idPratica, DomusSoggetto intestatario, DomusSoggetto richiedente,
			DomusUbicazioneIntervento ubicazioneIntervento,String oggetto) {
		super();
		this.idPratica = idPratica;
		this.intestatario = intestatario;
		this.richiedente = richiedente;
		this.ubicazioneIntervento = ubicazioneIntervento;
		this.oggetto=oggetto;
		dump();
	}	

	public static DomusXmlIstanza fromString(String xml)
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {

		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		Document document = builder.parse(is);

		
	    NamespaceContextImpl context = new NamespaceContextImpl();
	    context.startPrefixMapping("ns1", "http://www.mude.piemonte.it/mude.xsd");
	    context.startPrefixMapping("ns2", "http://www.mude.piemonte.it/mude-quadri-comuni.xsd");
	    context.startPrefixMapping("xfa", "http://www.xfa.org/schema/xfa-data/1.0/");
	    
	    //-----------------------------------------------------------------------	    
	    XPathExpression expr;
	    
	    //-----------------------------------------------------------------------
		XPath xpath = xpathfactory.newXPath();
	    //-----------------------------------------------------------------------
		//
		xpath.setNamespaceContext(context);
		
		
		
		
		expr = xpath.compile(IdPratica_xPath);
		String idPratica = (String) expr.evaluate(document, XPathConstants.STRING);
		
		//Oggetto
		expr = xpath.compile(oggettoNumeroMude_xPath);
		oggettoNumeroMude = (String) expr.evaluate(document, XPathConstants.STRING);
		expr = xpath.compile(oggettoDescPratica_xPath);
		oggettoDescPratica = (String) expr.evaluate(document, XPathConstants.STRING);
		String oggetto = oggettoNumeroMude+" "+oggettoDescPratica;
		
		expr = xpath.compile(IstatComune_xPath);
		String istatComune = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(DenominazioneComune_xPath);
		String denominazioneComune = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(Particella_Toponomastica_xPath);
		String particellaTop = (String) expr.evaluate(document, XPathConstants.STRING);

		String nomeLuogo =null;
		expr = xpath.compile(Nome_Luogo_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			nomeLuogo = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(Civico_xPath);
		int civico = -1;
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			civico = Integer.parseInt((String) expr.evaluate(document, XPathConstants.STRING));

		expr = xpath.compile(Cap_xPath);
		String cap = null;
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			cap = (String) expr.evaluate(document, XPathConstants.STRING);
		
		String foglio = null;
		String mappale = null;
		expr = xpath.compile(foglio_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			foglio = (String) expr.evaluate(document, XPathConstants.STRING);
		expr = xpath.compile(mappale_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			mappale = (String) expr.evaluate(document, XPathConstants.STRING);
		
		//---------------------------------------------------------------------------------
		//Dati Richiedente
		//---------------------------------------------------------------------------------		
		expr = xpath.compile(Nome_Richiedente_xPath);
		String nomeRichiedente = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(Cognome_Richiedente_xPath);
		String cognomeRichiedente = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(CF_Richiedente_xPath);
		String cfRichiedente = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(pec_Richiedente_xPath);
		String pecRichiedente = (String) expr.evaluate(document, XPathConstants.STRING);		
		
		//---------------------------------------------------------------------------------
		//Dati Intestatario
		//---------------------------------------------------------------------------------				
		expr = xpath.compile(Nome_Intestatario_xPath);
		String nomeIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);
		
		expr = xpath.compile(Cognome_Intestatario_xPath);
		String cognomeIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(CF_Intestatario_xPath);
		String cfIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);

		expr = xpath.compile(pec_Intestatario_xPath);
		String pecIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);


		String denominazioneComuneIntestatario = null;
		String sedimeIntestatario ="";
		String viaIntestatario ="";
		String civicoIntestatario = "";
		String indirizzoIntestatario="";
				
		expr = xpath.compile(Comune_Intestatario_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
		denominazioneComuneIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);
		
		expr = xpath.compile(Via_Intestatario_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
		viaIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);
		
		expr = xpath.compile(Sedime_Intestatario_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			sedimeIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);
		
		expr = xpath.compile(Civico_Intestatario_xPath);
		if(expr.evaluate(document, XPathConstants.STRING)!=null && !"".equals(expr.evaluate(document, XPathConstants.STRING)))
			civicoIntestatario = (String) expr.evaluate(document, XPathConstants.STRING);

		indirizzoIntestatario=sedimeIntestatario+" "+viaIntestatario+", "+civicoIntestatario;
		
				
		DomusSoggetto intestatario = new DomusSoggetto(nomeIntestatario, cognomeIntestatario, cfIntestatario, pecIntestatario);
		intestatario.setDenominazioneComune(denominazioneComuneIntestatario);
		intestatario.setIndirizzo(indirizzoIntestatario);
		
		DomusSoggetto richiedente = new DomusSoggetto(nomeRichiedente, cognomeRichiedente, cfRichiedente, pecRichiedente);
		DomusUbicazioneIntervento ubicazione = new DomusUbicazioneIntervento(istatComune, denominazioneComune, particellaTop, nomeLuogo,
				civico, cap, foglio, mappale);

		return new DomusXmlIstanza(idPratica, intestatario, richiedente, ubicazione,oggetto);

	}

	
	public void dump(){
		logger.debug("Richiedente CF"+ richiedente.getCf());
		logger.debug("Richiedente Nome"+ richiedente.getNome());
		logger.debug("Richiedente Cognome"+ richiedente.getCognome());
		logger.debug("Richiedente Pec"+ richiedente.getPec());
		logger.debug("Oggetto Pec"+ this.oggetto);	
	}
	
	
	public String getIdPratica() {
		return idPratica;
	}

	public DomusSoggetto getIntestatario() {
		return intestatario;
	}

	public DomusSoggetto getRichiedente() {
		return richiedente;
	}

	public DomusUbicazioneIntervento getUbicazioneIntervento() {
		return ubicazioneIntervento;
	}

	public String getNomeIntestatario() {
		return intestatario.getNome();
	}

	public String getCognomeIntestatario() {
		return intestatario.getCognome();
	}

	public String getCfIntestatario() {
		return intestatario.getCf();
	}

	public String getPecIntestatario() {
		return intestatario.getPec();
	}

	public String getNomeRichiedente() {
		return richiedente.getNome();
	}

	public String getCognomeRichiedente() {
		return richiedente.getCognome();
	}

	public String getCfRichiedente() {
		return richiedente.getCf();
	}

	public String getPecRichiedente() {
		return richiedente.getPec();
	}

	public String getNomeComune() {
		return ubicazioneIntervento.getNomeComune();
	}

	public String getCodiceIstat() {
		return ubicazioneIntervento.getCodiceIstatComune();
	}

	public String getAddress() {
		String cap = ubicazioneIntervento.getCap();
		if(cap != null && cap.length() > 0)
			cap = ", CAP " + cap;
		else
			cap = "";
		
		return ubicazioneIntervento.getParticellaTop()+
				" "+ubicazioneIntervento.getNomeLuogo()+
				" n."+ubicazioneIntervento.getCivico()+
				cap;
	}
	
	public String getFoglioMappale() {
		if(ubicazioneIntervento.getFoglio() == null 
				|| ubicazioneIntervento.getMappale() == null) 
			return null;
		
		return ubicazioneIntervento.getFoglio()+"/"+ubicazioneIntervento.getMappale();
	}

	public String getComuneIntestatario(){
		return intestatario.getDenominazioneComune();
		
	}
	
	public String getIndirizzoIntestatario() {
		return intestatario.getIndirizzo();
	}


	@Override
	public String getOggettoPratica() {
		return this.oggetto;
	}



		


}
