package cdst_be_marche.adapder.documentale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdst_be_marche.DTO.AlfrescoDocumentAdapterDTO;
import cdst_be_marche.DTO.DocumentAdapterDTO;
import cdst_be_marche.model.AlfrescoDocumentAdapter;
import cdst_be_marche.repository.AlfrescoDocumentAdapterRepository;

@Service
public class DocumentAdapterService {

	@Autowired
	AlfrescoDocumentAdapterRepository alfrescoRepository;
	

	public AlfrescoDocumentAdapter storeAlfrescoDocument(DocumentAdapterDTO adapterDTO) {
		if (adapterDTO instanceof AlfrescoDocumentAdapterDTO)
			return storeAlfrescoDocument(adapterDTO) ;
		
		return null;
	}

		
	
	
	public AlfrescoDocumentAdapter storeAlfrescoDocument(AlfrescoDocumentAdapter alfrescoDocument) {
		alfrescoDocument=alfrescoRepository.save(alfrescoDocument);
		return alfrescoDocument;
	}
	
	
	public AlfrescoDocumentAdapter storeAlfrescoDocument(AlfrescoDocumentAdapterDTO alfrescoDTO) {
		return storeAlfrescoDocument(AlfrescoDocumentAdapter.fillAfrescoDocumentAdapter(alfrescoDTO));		
	}
	
}
