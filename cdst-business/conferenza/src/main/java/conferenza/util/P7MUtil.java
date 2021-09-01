package conferenza.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class P7MUtil {

	/*
	*  Sandro Demontis
	*  Classe con utility per estrazione file da un .p7m e conversioni
	*  per ottenere un byte array da un file e da un InputStream  
	*/

	private static final Logger log = LoggerFactory.getLogger(P7MUtil.class);

	public static byte[] extractFileFromP7M(byte[] filep7m) throws Exception {
		
	    byte[] extractedFileByteArray = getData(filep7m);
	    
	    return extractedFileByteArray;
	}
	
	public static byte[] fromFileToByteArray(File file) {
	    try {
	        return FileUtils.readFileToByteArray(file);
	    } catch (IOException e) {
	        log.error("Error while reading .p7m file!", e);
	    }
	    return new byte[0];
	}
	
	private static byte[] getData(final byte[] p7bytes) {
	    CMSSignedData cms = null;
	    try {
	        cms = new CMSSignedData(p7bytes);
	    } catch (CMSException e) {
	        log.error("Error while converting bytes to CMSSignedData : " + e.getMessage(), e);
	    }
	    if( cms == null || cms.getSignedContent() == null) {
	        //return new byte[0];	restituisco lo stream di input
	    	return p7bytes;
	    }
	    return (byte[]) cms.getSignedContent().getContent();
	}

	public static byte[] fromInputStreamToByteArray(InputStream is) throws IOException {
	 
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int nRead;
	    byte[] data = new byte[1024];
	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	        buffer.write(data, 0, nRead);
	    }
	 
	    buffer.flush();
	    byte[] byteArray = buffer.toByteArray();
	    return byteArray;
	}

}
