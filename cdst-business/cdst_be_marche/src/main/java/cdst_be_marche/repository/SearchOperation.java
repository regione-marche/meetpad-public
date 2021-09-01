package cdst_be_marche.repository;

public enum SearchOperation {
	
	WHERE, AND, OR, NOT, DISTINCT,
	
    EQUAL, NULL, NOT_NULL, GREATER_THAN, LESS_THAN, GREATER_OR_EQUAL_THAN, LESS_OR_EQUAL_THAN, LIKE, STARTS_WITH, ENDS_WITH, IN,
    
    TRUE, FALSE, NOTEQUAL;

    /**
     * @param input
     * @return
     */
    public static SearchOperation getSimpleOperation(final String input) {
        switch (input) {
        case "true": 
        	return TRUE;
        case "false": 
        	return FALSE;
        case "-":
            return DISTINCT;
        case "=":
            return EQUAL;
        case "!=":
        	return NOTEQUAL;
        case "!":
        case "is null":
        case "IS NULL":
            return NULL;
        case "!!":
        case "is not null":
        case "IS NOT NULL":
            return NOT_NULL;
        case ">":
            return GREATER_THAN;
        case "<":
            return LESS_THAN;
        case ">=":
            return GREATER_OR_EQUAL_THAN;
        case "<=":
            return LESS_OR_EQUAL_THAN;
        case "%":
        case "like":
        case "LIKE":
            return LIKE;
        case "%-":
        case "end with":
        case "END WITH":
            return ENDS_WITH;
        case "-%":
        case "start with":
        case "START WITH":
            return STARTS_WITH;
        case "in":
        case "IN":
            return IN;
        default:
            return null;
        }
    }
}
