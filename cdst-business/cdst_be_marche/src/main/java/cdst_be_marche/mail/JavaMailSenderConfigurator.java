package cdst_be_marche.mail;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
mail.protocol 
mail.host 
mail.port 
mail.smtp.socketFactory.port 
mail.smtp.auth 
mail.smtp.starttls.enable 
mail.smtp.starttls.required 
mail.smtp.debug 
mail.smtp.socketFactory.fallback 
mail.from 
mail.username 
mail.password 

mail:
    default-encoding: UTF-8
    host: smtp.gmail.com
    username: meetpadmarche@gmail.com
    from: meetpadmarche@gmail.com
    password: m33tP4D123
    port: 587
    protocol: smtp
    smtp:
          debug: true
          auth: true
          starttls:
            required: true
            enable: true   
          socketFactory:
            fallback: false                         
    test-connection: false

    
 * @author guideluc
 *
 */
@Configuration
//@PropertySource("classpath:mail.properties")
public class JavaMailSenderConfigurator {
	
	@Value("${mail.defaultFormatPec}")
    private boolean defaultFormatPec;
    @Value("${mail.protocol}")
    private String protocol;
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private int port;
    //@Value("${mail.smtp.socketFactory.port}")
    @Value("${mail.port}")
    private int socketPort;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.smtp.starttls.required}")
    private boolean startlls_required;
    @Value("${mail.smtp.debug}")
    private boolean debug;
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.sendEnabled}")
    private boolean sendEnabled;
    @Value("${mail.recipient.fake}")
    private boolean fakeRecipient;
    @Value("${mail.recipient.fakeAddress}")
    private String fakeRecipientAddress;
    @Value("${mail.baseUrlIndizione}")
    private String baseUrlIndizione;
    
    @Value("${mail.smtp.ehlo}")
    private String ehlo;

    
    //POP3
    @Value("${mail.pop3.port}")
    private String pop3Port;
    @Value("${mail.pop3.host}")
    private String pop3Host;
    @Value("${mail.pop3.folder}")
    private String pop3Folder;
    
    
    JavaMailSenderImpl mailSender;
    
    public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public MimeMessage createMimeMessage() {
		if(mailSender!=null)
			return  mailSender.createMimeMessage();
		return null;
	}
	
	@Bean
    public JavaMailSender javaMailSender() {
        mailSender = new JavaMailSenderImpl();

        mailSender.createMimeMessage();
        
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", auth);
        mailProperties.put("mail.smtp.starttls.enable", starttls);
        mailProperties.put("mail.smtp.starttls.required", startlls_required);
        mailProperties.put("mail.smtp.socketFactory.port", socketPort);
        mailProperties.put("mail.smtp.debug", debug);
        //mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProperties.put("mail.smtp.socketFactory.fallback", fallback);

        if("false".equals(ehlo))	
        	mailProperties.put("mail.smtp.ehlo",ehlo);
        
        mailSender.setJavaMailProperties(mailProperties);
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }

	public boolean isSendEnabled() {
		return sendEnabled;
	}

	public void setSendEnabled(boolean sendEnabled) {
		this.sendEnabled = sendEnabled;
	}

	public boolean isFakeRecipient() {
		return fakeRecipient;
	}

	public void setFakeRecipient(boolean fakeRecipient) {
		this.fakeRecipient = fakeRecipient;
	}

	public String getFakeRecipientAddress() {
		return fakeRecipientAddress;
	}

	public void setFakeRecipientAddress(String fakeRecipientAddress) {
		this.fakeRecipientAddress = fakeRecipientAddress;
	}

	public String getBaseUrlIndizione() {
		return baseUrlIndizione;
	}

	public void setBaseUrlIndizione(String baseUrlIndizione) {
		this.baseUrlIndizione = baseUrlIndizione;
	}

	public String getEhlo() {
		return ehlo;
	}

	public void setEhlo(String ehlo) {
		this.ehlo = ehlo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(String pop3Port) {
		this.pop3Port = pop3Port;
	}

	public String getPop3Host() {
		return pop3Host;
	}

	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	public String getPop3Folder() {
		return pop3Folder;
	}

	public void setPop3Folder(String pop3Folder) {
		this.pop3Folder = pop3Folder;
	}

	public boolean isDefaultFormatPec() {
		return defaultFormatPec;
	}

	public void setDefaultFormatPec(boolean defaultFormatPec) {
		this.defaultFormatPec = defaultFormatPec;
	}
	
	
	
}
