package cdst_be_marche.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import cdst_be_marche.adapder.alfresco.AlfrescoHelper;
import cdst_be_marche.exception.FileStorageException;
import cdst_be_marche.file.FileStorageProperties;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.RegistroDocumento;
import cdst_be_marche.model.TokenRegistroDocumento;
import cdst_be_marche.repository.TokenRegistroDocumentoRepository;
import cdst_be_marche.util.DbConst;

@Transactional
@Service
public class RegistroDocumentoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroDocumentoService.class);

	private final String downloadContextPath;
	private final int tokenExpireMinutes;

	@Autowired
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;

	@Autowired
	DocumentoService documentoService;

	@Autowired
	FileSystemService fileSystemService;
	
	@Autowired
	private AlfrescoHelper alfrescoHelper;

	@Autowired
	public RegistroDocumentoService(FileStorageProperties fileStorageProperties) {
		this.downloadContextPath = fileStorageProperties.getDownloadContextPath();
		this.tokenExpireMinutes = new Integer(fileStorageProperties.getTokenExpireMinutes()).intValue();
	}

	public Resource loadFileAsResource(RegistroDocumento registro) {
		if (DbConst.REGISTRO_DOCUMENTO_TIPO_FILESYSTEM.equals(registro.getTipo().getCodice())) {
			return fileSystemService.loadFileAsResourceFromFileSystem(registro.getRiferimentoEsterno());
		} else if (DbConst.REGISTRO_DOCUMENTO_TIPO_ALFRESCO.equals(registro.getTipo().getCodice())) {
			return loadFileAsResourceFromAlfresco(registro.getRiferimentoEsterno());
		} else
			throw new FileStorageException("Tipologia registro non supportata: " + registro.getTipo());
	}

	private Resource loadFileAsResourceFromAlfresco(String adapterKey) {
		try {
			return alfrescoHelper.loadFileAsResource(adapterKey);
		} catch (IOException e) {
			throw new FileStorageException("Problemi durante il recupero del file: " + e.getMessage());
		}
	}

	public Resource loadFileAsResource(String token) {
		RegistroDocumento registro = getRegistroDocumentoByToken(token);
		return loadFileAsResource(registro);
	}

	private RegistroDocumento getRegistroDocumentoByToken(String tokenFile) {
		TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(tokenFile);
		return tokenRegistroDocumento.getRegistroDocumento();
	}

	public String getTokenRegistroDocumento(RegistroDocumento registroDocumento) {
		TokenRegistroDocumento token = tokenRegistroDocumentoRepository
				.save(new TokenRegistroDocumento(registroDocumento, computeScadenza()));
		return token.getToken();
	}

	private Date computeScadenza() {
		return DateUtils.addMinutes(new Date(), tokenExpireMinutes);
	}

	public String resolveFileDownloadUri(RegistroDocumento registroDocumento) {
		if (registroDocumento != null) {
			UriComponentsBuilder ucb = ServletUriComponentsBuilder.fromCurrentContextPath();
			if (downloadContextPath != null && !downloadContextPath.isEmpty()) {
				ucb = UriComponentsBuilder.fromHttpUrl(downloadContextPath);
			}
			return ucb.path("/downloadFile/").path(getTokenRegistroDocumento(registroDocumento)).toUriString();
		}
		return null;
	}

	public File loadFileFromDocumento(Documento documento) {
		Resource resource = null;
		if (documento != null) {
			RegistroDocumento registroDocumento = documentoService.getRegistroDocumento(documento);
			if (registroDocumento != null) {
				resource = loadFileAsResource(registroDocumento);
			}
		}

		File fileIndizione = null;
		try {
			if (resource != null)
				fileIndizione = resource.getFile();
		} catch (IOException e) {
			LOGGER.error("load file indizione: " + e.getMessage());
			e.printStackTrace();
		}

		return fileIndizione;
	}

}