package cdst_be_marche;

import java.util.Arrays;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cdst_be_marche.auditor.AuditorAwareImpl;
import cdst_be_marche.file.FileStorageProperties;
import cdst_be_marche.mail.EmailStrategy;
import cdst_be_marche.properties.AutenticazioneProperties;
import cdst_be_marche.security.TokenFilter;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class, AutenticazioneProperties.class })
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
public class Application extends SpringBootServletInitializer {

	private static final String TOKEN_FILTER_EXCLUDE_URLS_PARAMETER = "tokenFilterExcludeUrls";

	@Autowired
	private TokenFilter tokenFilter;

	@Autowired
	AutenticazioneProperties autenticazioneProperties;
	
	@Autowired
    private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

	@Bean
	public MessageSource messageSource(@Value("${messages.properties}") String messaggi) {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(messaggi);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean validator(@Value("${messages.properties}") String messaggi) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource(messaggi));
		return bean;
	}

	/*
	 * @Bean public JavaMailSender getJavaMailSender() { JavaMailSenderConfigurator
	 * mailSenderC = new JavaMailSenderConfigurator(); JavaMailSender
	 * mailSender=mailSenderC.javaMailSender(); return mailSender; }
	 */

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public FilterRegistrationBean<TokenFilter> filterRegistrationBean() {
		FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<TokenFilter>();

		registrationBean.setFilter(tokenFilter);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registrationBean.setEnabled(autenticazioneProperties.getVerificaToken());
		registrationBean.addInitParameter(TOKEN_FILTER_EXCLUDE_URLS_PARAMETER,
				autenticazioneProperties.getTokenFilterExcludeUrls());

		return registrationBean;
	}

	@Bean
	public Executor taskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("EmailTask-");
		executor.initialize();
		return executor;

	}
	
	@Bean
    public EmailStrategy EmailStrategyAlias(@Value("${mail.qualifierStrategy}") String qualifier) {
        return (EmailStrategy) context.getBean(qualifier);
    }

}
