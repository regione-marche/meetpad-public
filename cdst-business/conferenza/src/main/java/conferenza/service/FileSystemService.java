package conferenza.service;

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

import conferenza.DTO.DocumentoFileDTO;
import conferenza.exception.FileStorageException;
import conferenza.exception.MyFileNotFoundException;
import conferenza.exception.NotFoundEx;
import conferenza.file.FileStorageProperties;
import conferenza.file.WriteDocService;
import conferenza.model.Documento;
import conferenza.model.Modello;
import conferenza.repository.ModelloRepository;

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
	
	private final Path fileDelegateStorageLocation;
	private final String delegateSubdir;
	
	@Autowired
	WriteDocService docService;
	
	@Autowired
	ModelloRepository modelloRepo;
	
	@Autowired
	public FileSystemService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		this.uploadSubdir = fileStorageProperties.getUploadSubdir();

		this.fileDelegateStorageLocation = Paths.get(fileStorageProperties.getDelegateDir()).toAbsolutePath().normalize();;
		this.delegateSubdir = fileStorageProperties.getDelegateSubdir();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
		
		try {
			Files.createDirectories(this.fileDelegateStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files for delegates will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file, Documento documento) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		InputStream in = null;
		try {
			in = file.getInputStream();
			return storeFile(in, fileName, documento);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		finally {
			if(in != null) try { in.close(); } catch (Exception e) { }
		}
	}
	
	public String storeFile(Resource  file, Documento documento) {
		// Normalize file name
		InputStream in = null;
		try {
			in = file.getInputStream();
			return storeFile(in, documento.getNome(), documento);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + documento.getNome() + ". Please try again!", ex);
		}
		finally {
			if(in != null) try { in.close(); } catch (Exception e) { }
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
	
	public String storeFileDaModello(DocumentoFileDTO documentoFileDTO, Documento documentoSaved) {
		Modello modello = modelloRepo.findById(documentoFileDTO.getModello()).orElse(null);
		if (modello == null) {
			throw new NotFoundEx("modello non trovato");
		}
		String fileName = modello.getDescrizione() + ".docx";
		InputStream modelloInput = getClass().getClassLoader().getResourceAsStream(fileName);
		String path = storeFile(modelloInput, generaNomeFilePerUtente(fileName,
				Integer.toString(documentoSaved.getIdDocumento())), documentoSaved);
		String pathNormalizzato = fileStorageLocation.resolve(path).normalize().toString();
		docService.aggiornaModelloFile(fileName, pathNormalizzato, documentoSaved.getConferenza());
		return path;
	}	

	private String generaNomeFilePerUtente(String modello, String id) {
		String nomeFile = "Documento" + id + StringUtils.capitalize(modello);
		return nomeFile;
	}

	/**
	 * <pre>
	 * Calcolo del path assoluto per il salvataggio del documento. La cartella viene
	 * creata se non presente.
	 * 
	 * @param documento
	 * @return
	 */
	public Path getTargetPath(Documento documento) {
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
		//return pathRelative.toString().substring(2); //xmf: windows fix
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
	
	public String storeFile(MultipartFile file, String idDelegate) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		InputStream in = null;
		try {
			in = file.getInputStream();
			return storeFile(in, fileName, idDelegate);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		finally {
			if(in != null) try { in.close(); } catch (Exception e) { }
		}
	}
	
	public String storeFile(InputStream inputStream, String fileName, String idDelegate) {
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = getTargetPath(idDelegate).resolve(fileName);
			Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return Paths.get(getTargetPathRelative(idDelegate), fileName).toString();
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
	
	public Path getTargetPath(String idDelegate) {
		Path targetPath = Paths.get(fileDelegateStorageLocation.toString(), getTargetPathRelative(idDelegate));
		try {
			Files.createDirectories(targetPath);
			return targetPath;
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}
	
	private String getTargetPathRelative(String idDelegate) {
		Path pathRelative = Paths.get("./");
		if (delegateSubdir != null && !delegateSubdir.isEmpty()) {
				pathRelative = Paths.get(pathRelative.toString(), idDelegate);
		}
		return pathRelative.toString();
	}
	
	public Resource loadFileAsResourceFromFileSystemForDelegate(String path) {
		try {
			Path filePath = fileDelegateStorageLocation.resolve(path).normalize();
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
	
}