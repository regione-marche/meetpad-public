package conferenza.chatbot;
//
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//
//import org.dom4j.util.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.jndi.JndiTemplate;
//
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
public class MysqlConfigurationOld {
//
//	@Value("${spring.datasource.jndi-name}")
//	private String chatbotDS;
//
//	@Value("${spring.datasource.driver-class-name}")
//	private String chatbotDriver;
//
//	@Value("${spring.datasource.url}")
//	private String chatbotUrl;
//
//	@Value("${spring.datasource.username}")
//	private String chatbotUsername;
//
//	@Value("${spring.datasource.password}")
//	private String chatbotPassword;
//
//	private DataSource dataSource;
//
//	@Primary
//	@Bean(name = "dataSource")
//	public DataSource getDataSource() {
//		if (dataSource == null) {
//			if (chatbotDS != null && !chatbotDS.equals("")) {
//				try {
//					dataSource = (DataSource) new JndiTemplate().lookup(chatbotDS);
//				} catch (NamingException e) {
//					//e.printStackTrace();
//					DriverManagerDataSource ds = new DriverManagerDataSource();
//					ds.setDriverClassName(chatbotDriver);
//					ds.setUrl(chatbotUrl);
//					ds.setUsername(chatbotUsername);
//					ds.setPassword(chatbotPassword);
//					dataSource = ds;
//				}
//			} else {
//				DriverManagerDataSource ds = new DriverManagerDataSource();
//				ds.setDriverClassName(chatbotDriver);
//				ds.setUrl(chatbotUrl);
//				ds.setUsername(chatbotUsername);
//				ds.setPassword(chatbotPassword);
//				dataSource = ds;
//			}
//		}
//		return dataSource;
//
//	}
//
//
}