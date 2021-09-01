package conferenza.adapder.integrazioni.firma.calamaio.service;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import conferenza.adapder.integrazioni.firma.calamaio.adapter.CalamaioClientAdapter;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.service.FirmaService;

@Service
public class CalamaioService {

	private static final Logger logger = LogManager.getLogger(CalamaioService.class.getName());

	public static Integer TIPOFIRMA_CALAMAIO=3;
	
	@Autowired
	CalamaioClientAdapter calamaioClientAdapter;
	
	public FirmaDTO fillCalamaioXMLRequest(FirmaDTO par1) 
			throws IOException{
				String callbackbody=calamaioClientAdapter.fillCalamaioXMLRequest(par1);
				par1.setCallbackbody(callbackbody);
				return par1;
	}
	
	public MultipartFile checkHcmSignatureCalamaio(MultipartFile inFile,
	        String _hsmSignature_user,
	        String _hsmSignature_password,
	        String _hsmSignature_otp,
	        String _hsmSignature_domain,
	        boolean isAdditionalSign,
	        String tipoFirma) throws Exception {
		
		MockMultipartFile result = null;
		
		try {
			byte[] fileArray = calamaioClientAdapter.checkHcmSignatureCalamaio(
	        _hsmSignature_user,
	        _hsmSignature_password,
	        _hsmSignature_otp,
	        _hsmSignature_domain,
	        inFile.getBytes(),
	        tipoFirma);
//byte[] fileArray = inFile.getBytes();
			
			String fname = inFile.getName();
			String org_fname = inFile.getOriginalFilename();	
			if (tipoFirma != null && tipoFirma.equals("aruba.remote.pades")) {
				fname += "." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
				org_fname += "." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
			} else {
				if(!org_fname.endsWith("." + FirmaService.SIGNED_FILE_EXTENSION)) {
					fname += "." + FirmaService.SIGNED_FILE_EXTENSION;
					org_fname += "." + FirmaService.SIGNED_FILE_EXTENSION;
				}
			}
			
			//for DEBUG: byte[] fileArray = inFile.getBytes();
			if(fileArray != null)
				result = new MockMultipartFile(
					fname,
					org_fname,
					inFile.getContentType(),
					fileArray);
			
		} catch (Exception ex) {
			logger.debug("@calamaio - checkHcmSignatureCalamaio: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
			throw ex;
		}
		
		return result;
	}	
	
	
	public MultipartFile[] checkHcmMultipleSignatureCalamaio(MultipartFile[] inFileArray,
		String _hsmSignature_user,
		String _hsmSignature_password,
		String _hsmSignature_otp,
		String _hsmSignature_domain,
		boolean isAdditionalSign,
		Boolean cadesPades) throws Exception {
		
		MockMultipartFile[] resultArray = new MockMultipartFile[inFileArray.length];
		
		try {
			
			// partendo dall'array di MultipartFile creo un array di byte[] da passare al ws
			int dim = inFileArray.length;
			byte[][] inFileByteArray = new byte[dim][];
			for (int i = 0; i < dim; i++) {
				inFileByteArray[i] = inFileArray[i].getBytes();
			}
			
			// il ws prende in input un array di byte[] e restituisce un oggetto simile
			// contenente i file firmati
			// IMPORTANTE: Mi aspetto che l'ordine dei file venga garantito
			byte[][] outFileByteArray = calamaioClientAdapter.checkHcmMultipleSignatureCalamaio(
	        _hsmSignature_user,
	        _hsmSignature_password,
	        _hsmSignature_otp,
	        _hsmSignature_domain,
	        inFileByteArray,
	        cadesPades);
			
			// Per ogni file restituito creo un oggetto MultipartFile da restituire
			// Aggiungo tutto ad un array di MultipartFile[]
			for (int i = 0; i < outFileByteArray.length; i++) {
				
				MultipartFile inFile = inFileArray[i];
				byte[] fileArray = outFileByteArray[i];
				
				String fname = inFile.getName();
				String org_fname = inFile.getOriginalFilename();
				if (cadesPades != null && cadesPades) {
					fname += "." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
					org_fname += "." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
				} else {
					if(!org_fname.endsWith("." + FirmaService.SIGNED_FILE_EXTENSION)) {
						fname += "." + FirmaService.SIGNED_FILE_EXTENSION;
						org_fname += "." + FirmaService.SIGNED_FILE_EXTENSION;
					}
				}
				
				
				//for DEBUG: byte[] fileArray = inFile.getBytes();
				if(fileArray != null){
					MockMultipartFile result = new MockMultipartFile(fname,org_fname,inFile.getContentType(),fileArray);
					resultArray[i] = result;
				}
			}
						
		} catch (Exception ex) {
			logger.debug("@calamaio - checkHcmMultipleSignatureCalamaio: " + ex.getMessage() + " - " + Arrays.toString(ex.getStackTrace()));
			throw ex;
		}
		
		return resultArray;
	}
}
