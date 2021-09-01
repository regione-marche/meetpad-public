package conferenza.report.component;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRDataUtils;
import net.sf.jasperreports.engine.util.JRSaver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import conferenza.model.Conferenza;
import conferenza.repository.ConferenzaRepository;

@Component
public class SimpleReportFiller {

    private String reportOutputFileName;
    private String reportTemplateFileName;

    private JasperReport jasperReport;

    private JasperPrint jasperPrint;

    private JRDataSource dataSource;

    
    //@Qualifier("jdbcProductService")
    //@Autowired
    //JdbcTemplate jdbcTemplate;
    
    //@Autowired
    //@Qualifier(value = "todosView")
    //JasperReportsPdfView view;

    private Map<String, Object> parameters;

    public SimpleReportFiller() {
        parameters = new HashMap<>();
    }

    public void prepareReport() {
        compileReport();
        fillReport();
    }

    public void compileReport() {
        try {
            InputStream reportStream = getClass().getResourceAsStream(reportTemplateFileName);
            jasperReport = JasperCompileManager.compileReport(reportStream);
            JRSaver.saveObject(jasperReport, reportOutputFileName);
        } catch (JRException ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     */
    public void fillReport() {    	   
    	
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);            
        } catch (Exception ex) {
            Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReportOutputFileName() {
		return reportOutputFileName;
	}

	public void setReportOutputFileName(String reportOutputFileName) {
		this.reportOutputFileName = reportOutputFileName;
	}

	public String getReportTemplateFileName() {
		return reportTemplateFileName;
	}

	public void setReportTemplateFileName(String reportTemplateFileName) {
		this.reportTemplateFileName = reportTemplateFileName;
	}

	public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

	public JRDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(JRDataSource dataSource) {
		this.dataSource = dataSource;
	}
    
    
}
