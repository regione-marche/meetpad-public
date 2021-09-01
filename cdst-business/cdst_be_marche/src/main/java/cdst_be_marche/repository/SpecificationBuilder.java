package cdst_be_marche.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationBuilder<T extends Object> {
	
	public List<SearchCriteria> buildIdConfCriteria(List<Integer> lista) {
		List<SearchCriteria> params = new ArrayList<>();
		for (Integer id : lista) {
			params.add(new SearchCriteria("idConferenza", "=", id));
		}
		return params;
	}

	public Specification<T> buildConferenzaSpec(List<SearchCriteria> parametriRicerca,
			List<SearchCriteria> criteriaPartecipanti, List<SearchCriteria> criteriaProfili, boolean allConference) {

		Specification<T> allConfSpec = buildAll(allConference);
		return Specification.where(allConfSpec.or(buildOr(criteriaProfili)).or(buildOr(criteriaPartecipanti)))
				.and(buildAnd(parametriRicerca));
	}

	public Specification<T> buildAnd(List<SearchCriteria> parametriAnd) {
		List<Specification<T>> specs = parametriAnd.stream()
				.map(param -> new SearchSpecification<T>(param)).collect(Collectors.toList());
		return specs.stream().reduce(new SearchSpecification<T>(new SearchCriteria(null, "true", null)),
				(a, b) -> a.and(b));
		
	}

	public Specification<T> buildOr(List<SearchCriteria> criteriaOr) {
		List<Specification<T>> specs = criteriaOr.stream()
				.map(param -> new SearchSpecification<T>(param)).collect(Collectors.toList());
		return specs.stream().reduce(new SearchSpecification<T>(new SearchCriteria(null, "false", null)),
				(a, b) -> a.or(b));
	
	}
	
	public Specification<T> buildEventoSpec(List<SearchCriteria> criteriaConferenza, List<SearchCriteria> criteriaMittente,
			List<SearchCriteria> criteriaVisibilitaExtra, List<SearchCriteria> criteriaDestinatari,
			List<SearchCriteria> criteriaIdEvento, Boolean allEvents, Boolean isCreatore) {
		Specification<T> allConfSpec = (allEvents || isCreatore) ? new SearchSpecification<T>(new SearchCriteria(null, "true", null))
				: new SearchSpecification<T>(new SearchCriteria(null, "false", null));
		return Specification.where(allConfSpec.or(buildOr(criteriaMittente)).or(buildOr(criteriaVisibilitaExtra))
				.or(buildOr(criteriaDestinatari))).and(buildAnd(criteriaIdEvento)).and(buildAnd(criteriaConferenza));
	}
	
	public Specification<T> buildAll(Boolean all) {
		return all ? new SearchSpecification<T>(new SearchCriteria(null, "true", null))
				: new SearchSpecification<T>(new SearchCriteria(null, "false", null));
	}
}
