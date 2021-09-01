package conferenza.adapder.alfresco;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DocumentUtils {
	private static final Logger logger = LogManager.getLogger(DocumentUtils.class.getName());

	public static final String TEMP_FOLDER_NAME = "TEMPORARY_UPLOADED_FILES";

	public static final String DOCUMENT_SEPARATOR_ALFRESCO_DOCUMENT = "_____";

	public static final String DOCUMENT_SEPARATOR = "____";

	public static final String DOCUMENT_PLACEHOLDER = "#";

	public static final String GENERIC_SUMMARY_VIEW = "generic_summary";

	private static final int MAX_FILE_NAME_LENGTH = 249;

	private static Properties defaultProps;

	
	
	public static String getFilename(String documentoId) {
		if (documentoId == null || DOCUMENT_PLACEHOLDER.equals(documentoId)
				|| DocumentCostants.SEPARATOR.equals(documentoId) || !documentoId.contains(DOCUMENT_SEPARATOR)) {
			return null;
		} else {
			String[] parts = documentoId.split(DOCUMENT_SEPARATOR);
			return parts[0];
		}
	}


	
	
	
}
