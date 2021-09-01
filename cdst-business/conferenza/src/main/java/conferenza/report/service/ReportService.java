package conferenza.report.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conferenza.model.Conferenza;
import conferenza.report.JasperRerportsSimpleConfig; //org.baeldung.jasperreports.config.JasperRerportsSimpleConfig;
import conferenza.report.component.SimpleReportExporter;
import conferenza.report.component.SimpleReportFiller;
import conferenza.report.controller.ReportTestController;
import conferenza.report.model.ConferenzaSvolta;
import conferenza.report.model.Report;
import conferenza.report.model.ReportAdapter;
import conferenza.report.model.ReportParameter;
import conferenza.report.repository.ConferenzaReportRepository;
import conferenza.report.repository.ReportParameterRepository;
import conferenza.report.repository.ReportRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.util.RandomUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
	
    @Autowired
    ConferenzaReportRepository conferenzaRepository;
    
    @Autowired
    JasperRerportsSimpleConfig reportConfig;
    
    @Autowired
    ReportRepository reportRepository;
    
    @Autowired
    ReportParameterRepository reportParameterRepository;
    
    @Autowired
    ReportServiceAdapter serviceAdapter;
    
    
    public static String REPORT_TYPE_OPENDATA="OPENDATA";
    public static String REPORT_TYPE_REPORT="REPORT";
    

    public static String REPORT_VISIBILITY_PUBLIC="PUBLIC";
    public static String REPORT_VISIBILITY_PRIVATE="PRIVATE";
    
    public final String MODELLO_CONFERENZA_TIPO_REPORT = "conferenzaTipoReport";
    public final String MODELLO_CONFERENZA_TEST_REPORT = "conferenzaTestReport";
    public final String MODELLO_CONFERENZE_SVOLTE = "conferenzeSvolte";
    
    public final String ANNO_CORRENTE = "annoCorrente";
    public final String ANNO_PRECEDENTE = "annoPrecedente";
    public final String GLOBALE = "globale";
    
    //senza estenzione
    String name;
    //con estenzione
    String outName;
    //titolo del report    
    String title;

    public String reportClasspath;
    public String urlRoot;
    String urlPDF ; 
    String urlCSV;

    String outPDF ; 
    String outCSV;
    

    /**
     * 
     * @param report
     * @return
     */
	public JRDataSource getJRDatasourceConferenzaAll() {
		JRDataSource dataSource=null;
		List<Conferenza> conferenze = null;
		conferenze = conferenzaRepository.getListConferenza();
    	dataSource = new JRBeanCollectionDataSource(conferenze, false);
    	return dataSource;
    }
	
    /**
     * 
     * @param report
     * @return
     * @throws IOException 
     */
	public JRDataSource getJRDatasourceConferenzaByTipo(Report report) throws IOException {
		JRDataSource dataSource=null;
		List<Conferenza> conferenze = null;
		String tipologia=null;
		List<ReportParameter> parameterList = reportParameterRepository.getListConditionParameterById(report.getId());                
        if(parameterList!=null || parameterList.isEmpty()==false)
        for(ReportParameter parametro :parameterList) {        	
        	if("String".equals(parametro.getTipo()) ) {
        		tipologia=parametro.getParametrodefault();
        	}	
        } 		
		if(tipologia==null)
			throw new IOException("Manca la condizione per il report");
		
		conferenze = conferenzaRepository.getListConferenzaByTipologiaConferenzaSpec(tipologia);
    	dataSource = new JRBeanCollectionDataSource(conferenze, false);
    	return dataSource;
    }	
	
	public JRDataSource getJRDatasourceConferenzeSvolte(Report report) throws IOException {
		JRDataSource dataSource=null;
		List<ConferenzaSvolta> conferenzeSvolte = null;
		String valoreAnno=null;
		List<ReportParameter> parameterList = reportParameterRepository.getParametroAnnoParameterById(report.getId());                
        if(parameterList!=null || parameterList.isEmpty()==false)
        for(ReportParameter parametro :parameterList) {        	
        	if("String".equals(parametro.getTipo()) ) {
        		valoreAnno=parametro.getParametrodefault();
        	}	
        } 		
		if(valoreAnno==null) {
			throw new IOException("Manca la condizione per il report");
		}
		
		
		String dataInizioString = "";
		String dataFineString = "";
		int anno = 0;
		
		switch (valoreAnno) { 
			case ANNO_CORRENTE:
			case ANNO_PRECEDENTE:
				anno = 0;
				switch (valoreAnno) {
					case ANNO_CORRENTE:
						anno = Calendar.getInstance().get(Calendar.YEAR);
						break;
					case ANNO_PRECEDENTE:
						anno = Calendar.getInstance().get(Calendar.YEAR) - 1;
						break;
				}
			
				dataInizioString = Integer.toString(anno) + "-01-01 00:00:00";
				dataFineString = Integer.toString(anno) + "-12-31 00:00:00";
				
				break;
			case GLOBALE:
				dataInizioString = "2000-01-01 00:00:00";
				anno = Calendar.getInstance().get(Calendar.YEAR);
				dataFineString = Integer.toString(anno) + "-12-31 00:00:00";
				break;
		} 
		
		java.sql.Timestamp timeStampDataInizio = java.sql.Timestamp.valueOf(dataInizioString);
		java.sql.Timestamp timeStampDataFine = java.sql.Timestamp.valueOf(dataFineString);
	
		List<Object[]> objectList = conferenzaRepository.getListConferenzeSvolte(timeStampDataInizio,timeStampDataFine);
		for(Object[] obj : objectList){
			if (conferenzeSvolte == null) {
				conferenzeSvolte = new ArrayList<ConferenzaSvolta>();
			}
			conferenzeSvolte.add(new ConferenzaSvolta((String)obj[0],(java.math.BigInteger)obj[1],(java.math.BigInteger)obj[2]));
		}
		
		dataSource = new JRBeanCollectionDataSource(conferenzeSvolte, false);
     	
    	return dataSource;
    }	
	
	
	public void formatName(String pName) {
		formatName(pName, null);
	}
	
	/**
	 * 
	 * @param pName
	 */
	public void formatName(String pName, String codice) {
		serviceAdapter.setToken(getToken());
		Calendar now = Calendar.getInstance();
		int month = now.get(Calendar.MONTH) + 1;
		String monthString = Integer.toString(month);
		if (month < 10) {
			monthString = "0" + monthString;
		}
		String day = Integer.toString(now.getInstance().get(Calendar.DAY_OF_MONTH));
		if (now.getInstance().get(Calendar.DAY_OF_MONTH) < 10) {
			day += "0" + day;
		}
		String ora =  Integer.toString(now.getInstance().get(Calendar.HOUR_OF_DAY));
		if (now.getInstance().get(Calendar.HOUR_OF_DAY) < 10) {
			ora += "0" + ora;
		}
		String minuti =  Integer.toString(now.getInstance().get(Calendar.MINUTE));
		if (now.getInstance().get(Calendar.MINUTE) < 10) {
			minuti += "0" + minuti;
		}
		String secondi =  Integer.toString(now.getInstance().get(Calendar.SECOND));
		if (now.getInstance().get(Calendar.SECOND) < 10) {
			secondi += "0" + secondi;
		}
		
		
		if (codice != null) {
			pName += "_" + codice;  
		}
		
		pName += "_" + now.get(Calendar.YEAR) + monthString + 
				day + "_" + ora + minuti + secondi;
		
		//pName=pName+serviceAdapter.getToken();
		setName(pName);
	}
	
	
	/**
	 * 
	 * @return
	 */
    private String getToken() {
    	return RandomUtil.getRandomToken();    	
    }
    
    
    /**
     * 
     * @param titolo
     * @return
     */
    public String getTitle(String titolo) {
    	Date datagenerazione=new  Date();
    	return titolo+" - "+datagenerazione;
    }
    
    
    /**
     * 
     * @param codice
     * @return
     * @throws IOException
     */
    public Report getReportBycodice(String codice) throws IOException {
    	Report report=reportRepository.getReportByCodice(codice);
    	//[VERIFICA] - Codice Report
    	if(report==null)
    		throw new IOException("Codice Report Non gestito");

    	return report;
    }
    
    /**
     * 
     * @param report
     * @throws IOException
     */
    public void doSampleOpenDataReport(Report report) throws IOException {
    	report.setTiporeport(REPORT_TYPE_OPENDATA);
    	doSampleReport(report) ;    	
    }

    public void doConferenzaByTipoOpenDataReport(Report report) throws IOException {
    	report.setTiporeport(REPORT_TYPE_OPENDATA);
    	doConferenceByTipoReport(report) ;    	
    }
    
    
    
    /**
     * 
     * @param report
     * @throws IOException
     */
    public void doConferenceByTipoReport(Report report) throws IOException {
    	//[VERIFICA] - ESTENSIONE
    	String extension=getExtensionByReportType(report.getTiporeport()); 
    	if(extension==null )
    		throw new IOException("Estendione Report Non gestita");
    	
		String reportName=report.getModello();
		String title =report.getDescrizione();
    	
		//===============================================================================
		formatName(reportName, report.getCodice());
		//===============================================================================
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JasperRerportsSimpleConfig.class);
        ctx.refresh();

        SimpleReportFiller simpleReportFiller = ctx.getBean(SimpleReportFiller.class);
        switch (report.getModello()) {
          case MODELLO_CONFERENZA_TIPO_REPORT:
          case MODELLO_CONFERENZA_TEST_REPORT:
        	  simpleReportFiller.setDataSource(getJRDatasourceConferenzaByTipo(report));
        	  break;
          case MODELLO_CONFERENZE_SVOLTE:
        	  simpleReportFiller.setDataSource(getJRDatasourceConferenzeSvolte(report));
        	  break;
        }
        
        simpleReportFiller.setReportTemplateFileName(getReportResourcePath()+"/"+ reportName +".jrxml");
        simpleReportFiller.setReportOutputFileName(getUrlRoot()+"/"+ reportName +".jasper");
        
        simpleReportFiller.compileReport();

        //TITOLO	
        Map<String, Object> parameters = new HashMap<>();
        //Parametro di default!
        parameters.put("title",  getTitle(title));
        
        //LOGO
        InputStream imgInputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(getUrlRoot()+"/"+"LogoSmall.png");
        parameters.put("logo", imgInputStream);
                
        //Gestione dei parametri..
        List<ReportParameter> parameterList = reportParameterRepository.getListParameterById(report.getId());        
        if(parameterList!=null || parameterList.isEmpty()==false)
        for(ReportParameter parametro :parameterList) {        	
        	if("String".equals(parametro.getTipo()) ) {
        		//Eventuale sovrascrittura del titolo
        		if("title".equals(parametro.getNome()) ) {
        			parameters.put(parametro.getNome(),getTitle(parametro.getParametrodefault()));
        		}else
        			parameters.put(parametro.getNome(), parametro.getParametrodefault());
        	}	
        	else if("Integer".equals(parametro.getTipo()) )        	
        		parameters.put(parametro.getNome(), Integer.valueOf(parametro.getParametrodefault()) );
        	else 
        		throw new IOException("Tipo Paremetro NON supportato");
        }        
        //parameters.put("minSalary", 15000.0);
        //parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        simpleReportFiller.setParameters(parameters);
        
        simpleReportFiller.fillReport();

        SimpleReportExporter simpleExporter = ctx.getBean(SimpleReportExporter.class);
        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());
        
        if(REPORT_TYPE_REPORT.equals(report.getTiporeport())) {
        	setOutName(getName() + ".pdf");
        	setOutPDF(getUrlPDF() +"/"+ getOutName());
        	simpleExporter.exportToPdf(getOutPDF(), "conferenza");
        }	
        else if(REPORT_TYPE_OPENDATA.equals(report.getTiporeport())) {
        	setOutName(getName() + ".csv");
        	setOutCSV(getUrlCSV() +"/"+ getOutName());
            simpleExporter.exportToCsv(getOutCSV());                
        }       
                
        store(report);
    	
    }

    	
    	
    /**
     * 
     * @param report
     * @throws IOException
     */
    public void doSampleReport(Report report) throws IOException {
    	    	
    	//[VERIFICA] - ESTENSIONE
    	String extension=getExtensionByReportType(report.getTiporeport()); 
    	if(extension==null )
    		throw new IOException("Estendione Report Non gestita");
    	
		String reportName=report.getModello();
		String title =report.getDescrizione();
    	
		//===============================================================================
		formatName(reportName);
		//===============================================================================
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JasperRerportsSimpleConfig.class);
        ctx.refresh();

        SimpleReportFiller simpleReportFiller = ctx.getBean(SimpleReportFiller.class);
        simpleReportFiller.setDataSource(getJRDatasourceConferenzaAll());       
        simpleReportFiller.setReportTemplateFileName(getReportResourcePath()+"/"+ reportName +".jrxml");
        simpleReportFiller.setReportOutputFileName(getUrlRoot()+"/"+ reportName +".jasper");
        simpleReportFiller.compileReport();

        //simpleReportFiller.setReportFileName("employeeReport.jrxml");
        //simpleReportFiller.compileReport();

        Map<String, Object> parameters = new HashMap<>();

        
        parameters.put("title",  getTitle(title));
        
        //Gestione dei parametri..
        List<ReportParameter> parameterList = reportParameterRepository.getListParameterById(report.getId());        
        if(parameterList!=null || parameterList.isEmpty()==false)
        for(ReportParameter parametro :parameterList) {        	
        	if("String".equals(parametro.getTipo()) ) {
        		parameters.put(parametro.getNome(), parametro.getParametrodefault());
        	}	
        	else if("Integer".equals(parametro.getTipo()) )        	
        		parameters.put(parametro.getNome(), Integer.valueOf(parametro.getParametrodefault()) );
        	else 
        		throw new IOException("Tipo Paremetro NON supportato");
        }        
        //parameters.put("minSalary", 15000.0);
        //parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        simpleReportFiller.setParameters(parameters);
        
        simpleReportFiller.fillReport();

        SimpleReportExporter simpleExporter = ctx.getBean(SimpleReportExporter.class);
        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());
        
        if(REPORT_TYPE_REPORT.equals(report.getTiporeport())) {
        	setOutName(getName() + ".pdf");
        	setOutPDF(getUrlPDF() +"/"+ getOutName());
        	simpleExporter.exportToPdf(getOutPDF(), "conferenza");
        }	
        else if(REPORT_TYPE_OPENDATA.equals(report.getTiporeport())) {
        	setOutName(getName() + ".csv");
        	setOutCSV(getUrlCSV() +"/"+ getOutName());
            simpleExporter.exportToCsv(getOutCSV());                
        }       
        
        
        store(report);
    }
    
    /**
     * 
     * @param report
     */
    private void store(Report report) {
		ReportAdapter adapter=new ReportAdapter();
		if(REPORT_TYPE_REPORT.equals(report.getTiporeport())) {
			adapter.setPath(this.getOutPDF());
		}else if(REPORT_TYPE_OPENDATA.equals(report.getTiporeport())) {
			adapter.setPath(this.getOutCSV());	
		}
		adapter.setFkReport(report.getId());
		adapter.setToken(serviceAdapter.getToken());
		adapter.setDtIns(new Date());
		serviceAdapter.store(adapter);

	}

	/**
     * 
     * @param reportType
     * @return
     */
    private String getExtensionByReportType(String reportType) {
    	if(reportType==null)
    		return "pdf";
    	
    	if(REPORT_TYPE_REPORT.equals(reportType))
    		return "pdf";
    	else if(REPORT_TYPE_OPENDATA.equals(reportType))
    		return "csv";   
    	
    	return null;
    }
    
        
	public void doTest() {
		//===============================================================================
		//
		//===============================================================================		
		String reportName="conferenzaTestReport";
		String title ="Report Test Conferenza";
		//===============================================================================
		formatName(reportName);

		//===============================================================================
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(JasperRerportsSimpleConfig.class);
        ctx.refresh();

        SimpleReportFiller simpleReportFiller = ctx.getBean(SimpleReportFiller.class);
        simpleReportFiller.setDataSource(getJRDatasourceConferenzaAll());
        simpleReportFiller.setReportTemplateFileName(getReportResourcePath()+"/"+ reportName +".jrxml");
        simpleReportFiller.setReportOutputFileName(getUrlRoot()+"/"+ reportName +".jasper");
        simpleReportFiller.compileReport();

        //simpleReportFiller.setReportFileName("employeeReport.jrxml");
        //simpleReportFiller.compileReport();

        Map<String, Object> parameters = new HashMap<>();
        
        parameters.put("title",  getTitle("Report Test Conferenza"));
        
        

        
        //parameters.put("minSalary", 15000.0);
        //parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

        simpleReportFiller.setParameters(parameters);
        simpleReportFiller.fillReport();

        SimpleReportExporter simpleExporter = ctx.getBean(SimpleReportExporter.class);
        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());
        
        setUrlPDF(getUrlPDF()+"/"+ getName() + ".pdf");
        setUrlCSV(getUrlCSV() +"/"+ getName() + ".csv");
        
        
        simpleExporter.exportToPdf(getUrlPDF(), "conferenza");
        //simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
        simpleExporter.exportToCsv(getUrlCSV());
        //simpleExporter.exportToHtml("employeeReport.html");		
		
	}
	
	
	/**
	 * 
	 * @param filein
	 * @return
	 * @throws FileNotFoundException
	 */
	public ResponseEntity<Resource> fileToResponseEntityResource(File filein) 
			throws FileNotFoundException {
		
		InputStream stream = new FileInputStream(filein);
		InputStreamResource inputStreamResource = new InputStreamResource(stream);
		return new ResponseEntity<Resource>(inputStreamResource, null, HttpStatus.OK);		
	}
	
	
	//===========================================================================

	//===========================================================================
	
	/**
	 * 
	 * @param token
	 * @return
	 */
    private File getPDFFile(String name) {
    	
    	String path= getOutPDF();
    	LOGGER.debug("getPDFFile: "+path);
    	File lfFile= new File(path); 
    	return lfFile;
    }
	
    private File getCSVFile(String name) {    	
    	String path= getOutCSV();
    	LOGGER.debug("getUrlCSV: "+path);
    	File lfFile= new File(path); 
    	return lfFile;
    }
    
    
    /**
     * 
     * @return
     */
    public String getFilename() {
    	if(getOutCSV()!=null)
    		return getOutCSV();
    	else
    		return getOutPDF();
    }
    
	/**
	 * 
	 * 
	 * 
	 * @param filein
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStreamResource getStreamResource(String name,String resourceType) 
			throws FileNotFoundException {
		
		File filein =null;
		
		if(REPORT_TYPE_REPORT.equals(resourceType))
			filein =getPDFFile(name);
		else if(REPORT_TYPE_OPENDATA.equals(resourceType))
			filein =getCSVFile(name);
		
		InputStream stream = new FileInputStream(filein);
		InputStreamResource inputStreamResource = new InputStreamResource(stream);
		
		//return new ResponseEntity<Resource>(inputStreamResource, null, HttpStatus.OK);
		return inputStreamResource;
	}	
	/**
	 * 
	 * @param filein
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStreamResource getCSVResource(String name) 
			throws FileNotFoundException {
		
		File filein =getPDFFile(name);
		
		InputStream stream = new FileInputStream(filein);
		InputStreamResource inputStreamResource = new InputStreamResource(stream);
		
		//return new ResponseEntity<Resource>(inputStreamResource, null, HttpStatus.OK);
		return inputStreamResource;
	}		

	
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConferenzaReportRepository getConferenzaRepository() {
		return conferenzaRepository;
	}

	public void setConferenzaRepository(ConferenzaReportRepository conferenzaRepository) {
		this.conferenzaRepository = conferenzaRepository;
	}

	public  String getUrlRoot() {
		return reportConfig.getFileroot();
	}
	
	public  String getReportResourcePath() {
		return reportConfig.getReportReousrcePath();
	}

	public  void setUrlRoot(String purlRoot) {
		urlRoot = purlRoot;
	}

	public String getUrlPDF() {
		return getUrlRoot() + "/temp";
	}

	public void setUrlPDF(String urlPDF) {
		this.urlPDF = urlPDF;
	}

	public String getUrlCSV() {
		return getUrlRoot() + "/temp";
	}

	public void setUrlCSV(String urlCSV) {
		this.urlCSV = urlCSV;
	}

	public String getOutPDF() {
		return outPDF;
	}

	public void setOutPDF(String outPDF) {
		this.outPDF = outPDF;
	}

	public String getOutCSV() {
		return outCSV;
	}

	public void setOutCSV(String outCSV) {
		this.outCSV = outCSV;
	}

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public List<Report> getPublicReports() {
		return reportRepository.findByVisibilita(ReportService.REPORT_VISIBILITY_PUBLIC);
	}
	
	private List<Report> getPublicReportListByTiporeport(String tiporeport) {
		return reportRepository.getPublicReportListByTiporeport(tiporeport); 	
	}
	
	public List<Report> getReportList() {
		return reportRepository.getPublicReportListByTiporeport(REPORT_TYPE_REPORT); 	
	}

	public List<Report> getOpendataList() {
		return reportRepository.getPublicReportListByTiporeport(REPORT_TYPE_OPENDATA); 	
	}
	
}
