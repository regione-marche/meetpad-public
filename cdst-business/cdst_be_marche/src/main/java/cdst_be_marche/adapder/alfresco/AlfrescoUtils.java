package cdst_be_marche.adapder.alfresco;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.data.PermissionMapping;
import org.apache.commons.lang.StringUtils;

public class AlfrescoUtils {
	private AlfrescoUtils() {
		super();
	}

	/**
	 * Review permissions during development
	 *
	 * @param groupName
	 *            The group name
	 * @param permissionMappings
	 *            The Permission mappings
	 * @return {@link List<String>} of permissions
	 */
	public static List<String> getAlfrescoPermissionsByGroupName(String groupName,
			Map<String, PermissionMapping> permissionMappings) {
		List<String> permissions = permissionMappings.get(PermissionMapping.CAN_VIEW_CONTENT_OBJECT).getPermissions();

		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_DESCENDENTS_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_CHILDREN_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_PARENTS_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_FOLDER_PARENT_OBJECT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_CREATE_DOCUMENT_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_CREATE_FOLDER_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_PROPERTIES_OBJECT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_UPDATE_PROPERTIES_OBJECT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_DELETE_OBJECT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_DELETE_TREE_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_DELETE_CONTENT_DOCUMENT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_ADD_TO_FOLDER_OBJECT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_ADD_TO_FOLDER_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_REMOVE_FROM_FOLDER_OBJECT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_REMOVE_FROM_FOLDER_FOLDER));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_CHECKOUT_DOCUMENT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_CANCEL_CHECKOUT_DOCUMENT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_CHECKIN_DOCUMENT));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_ALL_VERSIONS_VERSION_SERIES));
		permissions.addAll(getPermissions(permissionMappings, PermissionMapping.CAN_GET_OBJECT_RELATIONSHIPS_OBJECT));

		return permissions;
	}

	private static List<String> getPermissions(Map<String, PermissionMapping> permissionMappings, String permission) {
		return permissionMappings.get(permission) != null ? permissionMappings.get(permission).getPermissions()
				: new LinkedList<>();
	}

	public static String getAlfrescoDocumentName(String alfrescoId) {
		if (alfrescoId != null) {
			if (alfrescoId.contains(DocumentUtils.DOCUMENT_SEPARATOR)) {
				String[] split = alfrescoId.split(DocumentUtils.DOCUMENT_SEPARATOR);
				return split[0];
			} else {
				return StringUtils.EMPTY;
			}
		} else {
			return StringUtils.EMPTY;
		}
	}

	public static String getAlfrescoDocumentId(String alfrescoId) {
		if (alfrescoId != null) {
			if (alfrescoId.contains(DocumentUtils.DOCUMENT_SEPARATOR)) {
				String[] split = alfrescoId.split(DocumentUtils.DOCUMENT_SEPARATOR);
				return split[1];
			} else {
				return StringUtils.EMPTY;
			}
		} else {
			return StringUtils.EMPTY;
		}
	}

}
