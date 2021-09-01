package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 *	{
 *		"edit": "true",
 *		"download": "true",
 *		"review": "edit"
 *	},
 * </pre>
 * 
 * @author arosina
 *
 */
public class OODocumentPermissionsDTO {
	
	@JsonProperty("edit")
	private String edit;

	@JsonProperty("download")
	private String download;

	@JsonProperty("review")
	private String review;

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

}
