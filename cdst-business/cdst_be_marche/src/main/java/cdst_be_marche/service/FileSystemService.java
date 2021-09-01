package cdst_be_marche.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cdst_be_marche.exception.FileStorageException;
import cdst_be_marche.exception.MyFileNotFoundException;
import cdst_be_marche.file.FileStorageProperties;
import cdst_be_marche.model.Documento;

@Transactional
@Service
public class FileSystemService {

	/*
	 * TODO: controllo su dimensione massima file
	 * 
	 * TODO: salvataggio su cartella remota, attualmente la cartella base si trova
	 * sotto tomcat
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);

	private final Path fileStorageLocation;
	private final String uploadSubdir;

	@Autowired
	public FileSystemService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this.uploadSubdir = fileStorageProperties.getUploadSubdir();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file, Documento documento) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			return storeFile(file.getInputStream(), fileName, documento);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public String storeFile(InputStream inputStream, String fileName, Documento documento) {
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = getTargetPath(documento).resolve(fileName);
			Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return Paths.get(getTargetPathRelative(documento), fileName).toString();
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * <pre>
	 * Calcolo del path assoluto per il salvataggio del documento. La cartella viene
	 * creata se non presente.
	 * 
	 * @param documento
	 * @return
	 */
	private Path getTargetPath(Documento documento) {
		Path targetPath = Paths.get(fileStorageLocation.toString(), getTargetPathRelative(documento));
		try {
			Files.createDirectories(targetPath);
			return targetPath;
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	/**
	 * <pre>
	 * Calcolo del percorso relativo per il salvataggio del documento secondo quanto definito nel parametro 
	 * - file.upload-subdir: struttura della sottocartella in cui salvare il documento
	 * 
	 * Per definire la sottocartella i parametri attualmente utilizzabili sono cfAmministrazioneProcedente e idConferenza:
	 * esempio upload-subdir: /${cfAmministrazioneProcedente}/${idConferenza}
	 * Per aggiungere ulteriori parametri modificare il metodo {@link #getUploadSubdirElements getUploadSubdirElements}
	 * </pre>
	 * 
	 * @param documento
	 * @return
	 */
	private String getTargetPathRelative(Documento documento) {
		Path pathRelative = Paths.get("./");
		if (uploadSubdir != null && !uploadSubdir.isEmpty()) {
			List<String> parametri = getListaParametri(uploadSubdir);
			for (String parametro : parametri) {
				pathRelative = Paths.get(pathRelative.toString(), valore(parametro, documento));
			}
		}
		return pathRelative.toString();
	}

	/**
	 * restituisce la lista dei subpath da una stringa di tipo
	 * "/${subpath1}/${subpath2}"
	 * 
	 * @param stringaParametrica
	 * @return
	 */
	private List<String> getListaParametri(String stringaParametrica) {
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < stringaParametrica.length(); i++) {
			if (stringaParametrica.charAt(i) == '$') {
				String s = stringaParametrica.substring(i, stringaParametrica.indexOf("}", i) + 1);
				lista.add(s.substring(2, s.length() - 1));
			}
		}
		return lista;
	}

	private String valore(String parametro, Documento documento) {
		if (parametro.equals("cfAmministrazioneProcedente")) {
			return documento.getConferenza().getAmministrazioneProcedente().getCodiceFiscaleEnte();
		} else if (parametro.equals("idConferenza")) {
			return documento.getConferenza().getIdConferenza().toString();
		} else
			return null;
	}

	/**
	 * Viene restituita la risorsa presente nel sottoPath indicato nel riferimento
	 * esterno. Nel riferimento esterno del registro è presente il percorso della
	 * sottocartella rispetto alla cartella radice: esempio:
	 * /80008630420/61/Analisi_rev1-4.docx
	 * 
	 * @param registro
	 * @return
	 */
	public Resource loadFileAsResourceFromFileSystem(String path) {
		try {
			Path filePath = fileStorageLocation.resolve(path).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + path);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + path, ex);
		}
	}

	public static void main(String[] args) {
		String stringaParametrica = "/${cfAmministrazioneProcedente}/${idConferenza}";
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < stringaParametrica.length(); i++) {
			if (stringaParametrica.charAt(i) == '$') {
				String s = stringaParametrica.substring(i, stringaParametrica.indexOf("}", i) + 1);
				// lista.add(s.substring(2, s.length() - 1));
				lista.add(s);
			}
		}
		System.out.println(lista);
	}

}