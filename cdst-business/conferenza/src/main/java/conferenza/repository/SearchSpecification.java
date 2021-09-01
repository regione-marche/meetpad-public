package conferenza.repository;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification<T> implements Specification<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  SearchCriteria criteria;

	
	public SearchSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}


	public SearchCriteria getCriteria() {
		return criteria;
	}


	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		switch (SearchOperation.getSimpleOperation(criteria.getOperation())) {
		
		case DISTINCT:
			query.distinct(true);
			return null;
		case GREATER_THAN: 
            return builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		case LESS_THAN:
            return builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
		case TRUE:
			return builder.conjunction();
		case FALSE:
			return builder.disjunction();
		case EQUAL:
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(builder.upper(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toUpperCase() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.equal(root.<Date>get(criteria.getKey()), (Date)criteria.getValue());
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
		case NOTEQUAL:
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.notLike(builder.upper(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toUpperCase() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Date.class) {
                return builder.notEqual(root.<Date>get(criteria.getKey()), (Date)criteria.getValue());
            } else {
                return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            }
		case NULL:
			return builder.isNull(root.<String> get(criteria.getKey()));
		case NOT_NULL:
			return builder.isNotNull(root.<String> get(criteria.getKey()));
		case LESS_OR_EQUAL_THAN:
			if (root.get(criteria.getKey()).getJavaType() == Date.class) {
            	return builder.lessThanOrEqualTo(root.<Date> get(criteria.getKey()), (Date)criteria.getValue());
            } else {
            	return builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            }
		case GREATER_OR_EQUAL_THAN:
			if (root.get(criteria.getKey()).getJavaType() == Date.class) {
				return builder.greaterThanOrEqualTo(root.<Date> get(criteria.getKey()), (Date)criteria.getValue());
            } else {
            	return builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
            }
		case LIKE:
			return builder.like(builder.upper(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toUpperCase() + "%");
		case ENDS_WITH:
			return builder.like(builder.upper(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toUpperCase());
		case STARTS_WITH:
			return builder.like(builder.upper(root.<String>get(criteria.getKey())), criteria.getValue().toString().toUpperCase() + "%");
		case IN:
			return builder.and(root.<String>get(criteria.getKey()).in(
					Stream.of(criteria.getValue().toString().split(","))
				      .map (elem -> new String(elem))
				      .collect(Collectors.toList())
					));
		default:
			return null;
        }
    }
	
	
	
}
