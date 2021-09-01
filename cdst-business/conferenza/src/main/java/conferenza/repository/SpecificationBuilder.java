package conferenza.repository;

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
			List<SearchCriteria> criteriaPartecipanti, List<SearchCriteria> criteriaProfili, 
			List<SearchCriteria> criteriaGruppoUtenti, List<SearchCriteria> criteriaStati, boolean allConference, boolean allState) {

		Specification<T> allConfSpec = buildAll(allConference);
		Specification<T> allStati = buildAll(allState);
		return Specification.where(allConfSpec.or(buildOr(criteriaGruppoUtenti))
									.or(buildOr(criteriaProfili))
									.or(buildOr(criteriaPartecipanti)))
				.and(buildAnd(parametriRicerca))
				.and(allStati.or(buildOr(criteriaStati)));
	}

	public Specification<T> buildConferenzaSpecNew(List<SearchCriteria> parametriRicerca,
			SearchCriteria criteriaPartecipanti, List<SearchCriteria> criteriaProfili, 
			List<SearchCriteria> criteriaGruppoUtenti, boolean allConference) {
		
		
		SearchSpecification<T> criteriaPartecipantiIn = new SearchSpecification<T>(new SearchCriteria(null, "false", null));
		if(criteriaPartecipanti != null) 
			criteriaPartecipantiIn  = new SearchSpecification<T>(criteriaPartecipanti);
		
		Specification<T> allConfSpec = buildAll(allConference);
		return Specification.where(allConfSpec.or(buildOr(criteriaGruppoUtenti))
									.or(buildOr(criteriaProfili))
									.or(criteriaPartecipantiIn))
				.and(buildAnd(parametriRicerca));
	}
	
	public Specification<T> buildAnd(List<SearchCriteria> parametriAnd) {
		if(parametriAnd == null) {
			return new SearchSpecification<T>(new SearchCriteria(null, "false", null));
		}
		List<Specification<T>> specs = parametriAnd.stream()
				.map(param -> new SearchSpecification<T>(param)).collect(Collectors.toList());
		return specs.stream().reduce(new SearchSpecification<T>(new SearchCriteria(null, "true", null)),
				(a, b) -> a.and(b));
		
	}

	public Specification<T> buildOr(List<SearchCriteria> criteriaOr) {
		if(criteriaOr == null) {
			return new SearchSpecification<T>(new SearchCriteria(null, "false", null));
		}
		List<Specification<T>> specs = criteriaOr.stream()
				.map(param -> new SearchSpecification<T>(param)).collect(Collectors.toList());
		return specs.stream().reduce(new SearchSpecification<T>(new SearchCriteria(null, "false", null)),
				(a, b) -> a.or(b));
	
	}
	
	public Specification<T> buildEventoSpec(List<SearchCriteria> criteriaConferenza,
			List<SearchCriteria> criteriaMittente, List<SearchCriteria> criteriaVisibilitaExtra,
			List<SearchCriteria> criteriaDestinatari, List<SearchCriteria> criteriaIdEvento, Boolean allEvents,
			Boolean isCreatore, Boolean isAdminProc, List<SearchCriteria> criteriaAccreditamentoEModificaRichiedente) {
		Specification<T> allConfSpec = (allEvents || isCreatore || isAdminProc)
				? new SearchSpecification<T>(new SearchCriteria(null, "true", null))
				: new SearchSpecification<T>(new SearchCriteria(null, "false", null));
		return Specification.where(buildAnd(criteriaAccreditamentoEModificaRichiedente)
				.and(allConfSpec.or(buildOr(criteriaMittente)).or(buildOr(criteriaVisibilitaExtra))
						.or(buildOr(criteriaDestinatari)))
				.and(buildAnd(criteriaIdEvento)).and(buildAnd(criteriaConferenza)));
	}
	
	public Specification<T> buildAll(Boolean all) {
		return all ? new SearchSpecification<T>(new SearchCriteria(null, "true", null))
				: new SearchSpecification<T>(new SearchCriteria(null, "false", null));
	}
	
		public Specification<T> buildEventoSpecAll(List<SearchCriteria> criteriaConferenza,
			List<SearchCriteria> criteriaMittente, List<SearchCriteria> criteriaVisibilitaExtra,
			List<SearchCriteria> criteriaDestinatari, List<SearchCriteria> criteriaIdEvento, Boolean allEvents,
			Boolean isCreatore, Boolean isAdminProc, List<SearchCriteria> criteriaRicerca,
			List<SearchCriteria> criteriaRuoloPart, List<SearchCriteria> criteriaPerPareriInterni, List<SearchCriteria> criteriaAccreditamento) {
		Specification<T> allConfSpec = (allEvents || isCreatore || isAdminProc)
				? new SearchSpecification<T>(new SearchCriteria(null, "true", null))
				: new SearchSpecification<T>(new SearchCriteria(null, "false", null));
			if(criteriaPerPareriInterni.size() == 0) {
				criteriaPerPareriInterni.add(new SearchCriteria(null, "true", null));
			}
			if(criteriaRuoloPart != null && criteriaRuoloPart.size() == 0) {
				criteriaRuoloPart.add(new SearchCriteria(null, "true", null));
			}
		return Specification
				.where(buildAnd(criteriaAccreditamento).and
						(allConfSpec.or(buildOr(criteriaMittente)).or(buildOr(criteriaVisibilitaExtra))
						.or(buildOr(criteriaDestinatari)))
				.and(buildAnd(criteriaIdEvento)).and(buildAnd(criteriaConferenza)).and(buildAnd(criteriaRicerca))
				.and(buildOr(criteriaRuoloPart)).and(buildOr(criteriaPerPareriInterni)));
	}
	
	public Specification<T> buildAllOr(List<SearchCriteria> listaCriteria) {
		if(listaCriteria.size() == 0) {
			return buildAll(Boolean.TRUE);
		} else {
			return buildOr(listaCriteria);
		}
	}
}
