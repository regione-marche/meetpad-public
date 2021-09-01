package conferenza.adapder.integrazioni.firma.calamaio.controller;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conferenza.firma.service.DSSValidationService;

@RestController
@RequestMapping(value = "/firma")
public class FirmaTestController
{
	@Autowired
	DSSValidationService dssValidationService;
	
    @RequestMapping("/test")
    public String test()
    {
    	
    	String response = "OK: ";

		FileInputStream fis = null;
    	try {
    		File file = new File("/tmp/firma");
    		fis = new FileInputStream(file);
    		byte[] array = new byte[(int)file.length()];
    		fis.read(array);
    		
    		response += dssValidationService.validateBytesArray(array);
		} catch (Throwable e) {
			long t = System.currentTimeMillis();
			
    		response = "FIRMA TEST KO: " + t;
			System.out.println(response);
			e.printStackTrace();
		}
    	finally {
    		try {
        		fis.close();
			} catch (Exception skip) {
				skip.printStackTrace();
			}
		}
    	
        return response;
    }
}