package conferenza.report.component;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

@Lazy
@Configuration
public class DatasourceConfig {
	
	
       @Value("${report.driver}")
       private String driverClassName; 	
	
       @Value("${report.url}")
       private String jdbcUrl; 	

       @Value("${report.username}")
       private String jdbcUser; 	
       
       @Value("${report.password}")
       private String jdbcPassword; 	       
             
	
	   //@Bean(name = "dbreportservice")
	   //@ConfigurationProperties(prefix = "spring.dbreportservice")
	   //public DataSource createProductServiceDataSource() {
	   //   return DataSourceBuilder.create().build();
	   //}
	
      /* 
	   @Bean(name = "dbreportservice")	   
	   public DataSource createProductServiceDataSource() {
	      return null;//customDataSource();
	   }		   
	  
	   @Bean
	   public DataSource customDataSource() {
		    DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName(driverClassName);
		    dataSource.setUrl(jdbcUrl);
		    dataSource.setUsername(jdbcUser);
		    dataSource.setPassword(jdbcPassword);
		    return dataSource;
	   } 
	   
	   
	   @Bean(name = "jdbcReportService")
	   @Autowired
	   public JdbcTemplate createJdbcTemplate_ProductService(@Qualifier("dbreportservice") DataSource reportServiceDS) {
	      return new JdbcTemplate(reportServiceDS);
	   }

	*/
	   
	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	   

}
