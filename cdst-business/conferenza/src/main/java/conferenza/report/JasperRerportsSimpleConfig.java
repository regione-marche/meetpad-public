package conferenza.report;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import conferenza.report.component.SimpleReportExporter;
import conferenza.report.component.SimpleReportFiller;

@Configuration
public class JasperRerportsSimpleConfig {

	    //@Bean
		//public DataSource dataSource() {
		//        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScript("classpath:employee-schema.sql").build();
		//}
	
	    @Value("${report.fileroot}")
	    private String fileroot="report";

	    @Value("${report.resourcepath}")
	    private String reportReousrcePath="/report";
	    	    
	    //@Primary
	    //@ConfigurationProperties(prefix="spring.datasource")
		//@Bean
		//public DataSource dataSource() {
		//	return   dataSource; //DataSourceBuilder.create().build();
		//}
	    

	    @Bean
	    public SimpleReportFiller reportFiller() {
	        return new SimpleReportFiller();
	    }

	    @Bean
	    public SimpleReportExporter reportExporter() {
	        return new SimpleReportExporter();
	    }

		public String getFileroot() {
			return fileroot;
		}

		public void setFileroot(String fileroot) {
			this.fileroot = fileroot;
		}

		public String getReportReousrcePath() {
			return reportReousrcePath;
		}

		public void setReportReousrcePath(String reportReousrcePath) {
			this.reportReousrcePath = reportReousrcePath;
		}
	    
}
