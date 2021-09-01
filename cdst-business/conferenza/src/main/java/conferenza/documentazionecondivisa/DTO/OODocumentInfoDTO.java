package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 *	{
 *		"key": "token",
 *		"fileType": "docx",
 *		"title": "nomedocumento",
 *		"url": "lsUrl"
 *	}
 * </pre>
 * 
 * @author arosina
 *
 */
public class OODocumentInfoDTO {

	@JsonProperty("key")
	private String key;

	@JsonProperty("fileType")
	private String fileType;

	@JsonProperty("title")
	private String title;

	@JsonProperty("url")
	private String url;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
