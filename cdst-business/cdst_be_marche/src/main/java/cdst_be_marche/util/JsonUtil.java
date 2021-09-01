package cdst_be_marche.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import cdst_be_marche.DTO.bean.LabelValue;

public class JsonUtil {

	public static List<LabelValue> jsonToListLabelValue(String json) {
		ObjectMapper objectMapper = new ObjectMapper();

		if (json != null && !json.isEmpty()) {
			try {
				return objectMapper.readValue(json,
						objectMapper.getTypeFactory().constructCollectionType(List.class, LabelValue.class));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<LabelValue>();
	}

	public static boolean validateListLabelValue(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.readValue(json,
					objectMapper.getTypeFactory().constructCollectionType(List.class, LabelValue.class));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(jsonToListLabelValue("[{\"key\":\"1\"},{\"key\":\"2\"}]"));
	}

	public static LabelValue jsonToLabelValue(String json) {
		ObjectMapper objectMapper = new ObjectMapper();

		if (json != null && !json.isEmpty()) {
			try {
				return objectMapper.readValue(json, LabelValue.class);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
