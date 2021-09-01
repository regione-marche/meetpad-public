package cdst_be_marche.adapder.alfresco;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StringsUtils {
	
	private static final Logger logger = LogManager.getLogger(StringsUtils.class.getName());

	public static String removeSomeSpecialCharacters(String field) {

		if (StringUtils.isBlank(field)) {
			return null;
		}

		if (field.contains("\t")) {
			field = field.replaceAll("\t", " ");
		}

		if (field.contains("\r")) {
			field = field.replaceAll("\r", " ");
		}

		if (field.contains("\n")) {
			field = field.replaceAll("\n", " ");
		}

		if (field.contains("\"")) {
			field = field.replaceAll("\"", " ");
		}

		if (field.contains("//")) {
			field = field.replaceAll("//", "-");
		}

		if (field.contains("'")) {
			field = field.replaceAll("'", " ");
		}

		return field;
	}

	public static String tag(String pre, Object str, String post) {
		if (str == null)
			return "";

		if (pre == null)
			pre = "";
		if (post == null)
			post = "";

		return pre + str.toString() + post;
	}

	public static String tags(String... strs) {
		if (strs == null || strs.length == 0)
			return "";
		if (strs.length == 1)
			return strs[0] == null ? "" : strs[1];

		String sep = "";
		StringBuilder res = new StringBuilder(strs.length * 10);
		int i = 0;
		while (i < strs.length) {
			String pre = strs[i++];
			if (pre == null)
				pre = "";
			String str = "";
			if (i < strs.length)
				str = strs[i++];
			String post = "";
			if (i < strs.length)
				post = strs[i++];
			if (str == null || str.isEmpty()) {
				pre = "";
				post = "";
			}

			res.append(sep);
			res.append(pre);
			res.append(str);
			sep = post;
		}

		res.append(sep);
		return res.toString();
	}

	public static String firstNotNull(String... strs) {
		if (strs == null || strs.length == 0)
			return "";
		for (String s : strs) {
			if (s != null) {
				return s;
			}
		}

		return "";
	}

	/**
	 * <p>
	 * Checks if a CharSequence is empty (""), null or whitespace only.
	 * </p>
	 *
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace
	 *         only
	 * @since 2.0
	 * @since 3.0 Changed signature from isBlank(String) to
	 *        isBlank(CharSequence)
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty (""), not null and not whitespace
	 * only.
	 * </p>
	 *
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null and
	 *         not whitespace only
	 * @since 2.0
	 * @since 3.0 Changed signature from isNotBlank(String) to
	 *        isNotBlank(CharSequence)
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * <p>
	 * Checks if a CharSequence is empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * CharSequence. That functionality is available in isBlank().
	 * </p>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is empty or null
	 * @since 3.0 Changed signature from isEmpty(String) to
	 *        isEmpty(CharSequence)
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty ("") and not null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null
	 * @since 3.0 Changed signature from isNotEmpty(String) to
	 *        isNotEmpty(CharSequence)
	 */
	public static boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}

	public static boolean isAnyBlank(Object... objs) {
		if (objs == null || objs.length == 0)
			return true;
		for (Object obj : objs) {
			if (obj == null)
				return true;
			String s = obj.toString();
			if (isBlank(s))
				return true;
		}

		return false;
	}

	public static String stringFormat(String pattern, Map<String, String> valuesMap) {

		// Map valuesMap = HashMap();
		// valuesMap.put("animal", "quick brown fox");
		// valuesMap.put("target", "lazy dog");
		// String pattern = "The ${animal} jumps over the ${target}.";
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String resolvedString = sub.replace(pattern);
		return resolvedString;
	}

	public static String getFileFromUrl(String url) {
		String ret = url;
		if (url.toUpperCase().startsWith("HTTP")) {
			ret = null;
		}
		return ret;
	}

	public static String sanityzeApostrophe(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}

		return value.replace("'", "''");
	}

	public static String getSqlString(String value) {
		if (value == null) {
			return "NULL";
		} else {
			String ret = value.replace("'", "''");

			if (ret.length() > 0 && ret.endsWith("\\")) {
				ret = ret.substring(0, ret.length() - 1);
			}

			return "'" + ret + "'";
		}
	}

	public static String sanityzeFootableDataView(String text) {

		if (StringUtils.isBlank(text)) {
			logger.warn("Text cannot be null..");
			return null;
		}

		String ret = text.trim();
		ret = ret.replace("\n", "");
		ret = ret.replace("\r", "");
		ret = ret.replace("\t", "");
		ret = ret.replace("/", DocumentCostants.SEPARATOR);
		ret = ret.replace("\\", DocumentCostants.SEPARATOR);
		ret = ret.replace(":", DocumentCostants.SEPARATOR);
		ret = ret.replace("|", DocumentCostants.SEPARATOR);
		ret = ret.replace("\"", DocumentCostants.SEPARATOR);
		ret = ret.replace("?", DocumentCostants.SEPARATOR);
		ret = ret.replace("*", DocumentCostants.SEPARATOR);
		ret = ret.replace("<", DocumentCostants.SEPARATOR);
		ret = ret.replace(">", DocumentCostants.SEPARATOR);

		return ret;
	}

	public static String sanityze(String text) {

		if (StringUtils.isBlank(text)) {
			logger.warn("Text cannot be null..");
			return null;
		}

		String ret = text.trim();
		ret = ret.replace("\n", "");
		ret = ret.replace("\r", "");
		ret = ret.replace("/", DocumentCostants.SEPARATOR);
		ret = ret.replace("\\", DocumentCostants.SEPARATOR);
		ret = ret.replace(":", DocumentCostants.SEPARATOR);
		ret = ret.replace("|", DocumentCostants.SEPARATOR);
		ret = ret.replace("\"", DocumentCostants.SEPARATOR);
		ret = ret.replace("?", DocumentCostants.SEPARATOR);
		ret = ret.replace("*", DocumentCostants.SEPARATOR);
		ret = ret.replace("<", DocumentCostants.SEPARATOR);
		ret = ret.replace(">", DocumentCostants.SEPARATOR);
		ret = ret.replace(".", "");
		return ret;
	}

	public static String sanityzeForRasWs(String text) {

		if (StringUtils.isBlank(text)) {
			logger.warn("Text cannot be null..");
			return null;
		}

		String ret = text.trim();
		ret = ret.replace("\n", "");
		ret = ret.replace("\r", "");
		ret = ret.replace("/", DocumentCostants.SEPARATOR);
		ret = ret.replace("\\", DocumentCostants.SEPARATOR);
		ret = ret.replace("|", DocumentCostants.SEPARATOR);
		ret = ret.replace("\"", DocumentCostants.SEPARATOR);
		ret = ret.replace("?", DocumentCostants.SEPARATOR);
		ret = ret.replace("*", DocumentCostants.SEPARATOR);
		ret = ret.replace("<", DocumentCostants.SEPARATOR);
		ret = ret.replace(">", DocumentCostants.SEPARATOR);
		ret = ret.replace(".", "");
		return ret;
	}

	public static String sanityzeForVtiger(String text) {

		if (StringUtils.isBlank(text)) {
			logger.warn("Text cannot be null..");
			return null;
		}

		String ret = text.trim();
		ret = ret.replace("\n", "");
		ret = ret.replace("\r", "");
		ret = ret.replace("/", "");
		ret = ret.replace("\\", "");
		ret = ret.replace("|", "");
		ret = ret.replace("\"", "");
		ret = ret.replace("?", "");
		ret = ret.replace("*", "");
		ret = ret.replace("<", "");
		ret = ret.replace(">", "");
		ret = ret.replace(".", "");

		return ret;
	}

	public static String replaceAllHeadWithBr(String toSanityze) {

		if (StringUtils.isNotBlank(toSanityze)) {
			toSanityze = toSanityze.replaceAll("(\r\n|\r|\n|\n\r)", "<br />");
			logger.debug("After sanityze : [" + toSanityze + "]");
		}

		return toSanityze;
	}

	public static String smartTruncate(String name) {
		if (name == null) {
			return StringUtils.EMPTY;
		}
		String ret;

		int length = name.trim().length();
		if (length > 255) {
			ret = name.substring(0, 200) + DocumentCostants.SEPARATOR + name.substring(length - 54, length);
		} else {
			ret = name;
		}

		return ret;
	}

	public static String join(List<String> list, String separator) {
		return list.stream().map(i -> i.toString()).collect(Collectors.joining(separator));
	}

	public static String joinLong(List<Long> list, String separator) {
		return "[" + list.stream().map(i -> i.toString()).collect(Collectors.joining(separator)) + "]";
	}

	public static String joinLongStringPairs(Map<Long, String> source, String separator) {
		String result = "[";
		Set<Entry<Long, String>> tuples = source.entrySet();
		int i = 0;
		for (Entry<Long, String> tuple : tuples) {
			result += "[" + "'" + tuple.getKey() + "'" + separator + "'" + tuple.getValue() + "']";
			if (i < (tuples.size() - 1)) {
				result += separator;
			}
			i++;
		}
		result += "]";

		return result;
	}

	public static String smartTruncate(String name, int i) {
		if (name == null) {
			return StringUtils.EMPTY;
		}
		String ret;
		int left = (int) Math.round(i * 0.8);
		int right = i - left;

		int length = name.trim().length();
		if (length > i) {
			ret = name.substring(0, left) + " ... " + name.substring(length - right - 5, length);
		} else {
			ret = name;
		}

		return ret;
	}

	public static String truncate(String name, int lenght) {
		if (name == null) {
			return StringUtils.EMPTY;
		}
		String ret;

		int length = name.trim().length();
		if (length > lenght) {
			ret = name.substring(0, lenght - 5) + " ... ";
		} else {
			ret = name;
		}

		return ret;
	}

	public static String getVtigerDescriptionKey(String category) {
		String retVal;

		if (StringUtils.isNotBlank(category) && category.contains("[[") && category.contains("]]")) {
			retVal = category.substring(category.indexOf("[[") + 2, category.lastIndexOf("]]"));
		} else {
			retVal = category;
		}
		return retVal;
	}

	public static String getVtigerDescriptionValue(String category) {
		String retVal;

		if (StringUtils.isNotBlank(category) && category.contains("[[") && category.contains("]]")) {
			retVal = category.substring(category.indexOf("]]") + 3);
		} else {
			retVal = category;
		}
		return retVal;
	}

	public static String nil(Object text) {
		if (text == null)
			return "";
		String t = text.toString();
		if (t == null)
			return "";
		return t;
	}
}
