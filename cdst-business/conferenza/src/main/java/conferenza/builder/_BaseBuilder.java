package conferenza.builder;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import conferenza.DTO.PersonaDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.exception.ParseDateException;
import conferenza.model.Persona;
import conferenza.model.bean._Typological;

public class _BaseBuilder {

	@Autowired
	HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	public static ModelMapper getMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(getDateToStringConverter());
		modelMapper.addConverter(getStringToDateConverter());
		modelMapper.addConverter(getTimeToStringConverter());
		modelMapper.addConverter(getStringToTimeConverter());
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	public static Converter<Date, String> getDateToStringConverter() {
		Converter<Date, String> toString = new AbstractConverter<Date, String>() {
			public String convert(Date date) {
				if (date != null)
					return new SimpleDateFormat("dd-MM-yyyy").format(date);
				else
					return "";
			}
		};
		return toString;
	}
	
	public static Converter<Time, String> getTimeToStringConverter() {
		Converter<Time, String> toString = new AbstractConverter<Time, String>() {
			public String convert(Time time) {
				if (time != null)
					return time.toLocalTime().toString();
				else
					return "";
			}
		};
		return toString;
	}

	public static Converter<String, Date> getStringToDateConverter() {
		Converter<String, Date> toDate = new AbstractConverter<String, Date>() {
			public Date convert(String string) {
				if (string != null && !string.isEmpty()) {
					try {
						return new SimpleDateFormat("dd-MM-yyyy").parse(string);
					} catch (ParseException e) {
						throw new ParseDateException(e.getMessage());

					}
				} else
					return null;
			}
		};
		return toDate;
	}
	
	public static Converter<String, Time> getStringToTimeConverter() {
		Converter<String, Time> toTime = new AbstractConverter<String, Time>() {
			public Time convert(String string) {
				if (string != null && !string.isEmpty()) {
					return Time.valueOf(LocalTime.parse(string));
				} else
					return null;
			}
		};
		return toTime;
	}

	/**
	 * 
	 *  Non è detto che ci sia una request ..il servizio deve essere robusto rispetto alla chiamata
		Caused by: java.lang.IllegalStateException: No thread-bound request found: Are you referring to request attributes outside of an actual web request
	 * 
	 * 
	 * @param typological
	 * @return
	 */
	public LabelValue createNotNullLabelValue(_Typological typological) {
		String descrizione = null;
		if (typological != null) {
			try{
				descrizione = messageSource.getMessage(typological.getDescrizione(), null,typological.getDescrizione(), request.getLocale());					
			}catch (Exception e) {
				descrizione =typological.getDescrizione();
			}
			return new LabelValue(typological.getCodice(), descrizione);
		}
		return null;
	}

	public String dateToString(Date date) {
		if (date != null)
			return new SimpleDateFormat("dd-MM-yyyy").format(date);
		else
			return "";
	}

	public Date stringToDate(String data) {
		if (data != null)
			try {
				return new SimpleDateFormat("dd-MM-yyyy").parse(data);
			} catch (ParseException e) {
				throw new ParseDateException(e.getMessage());

			}
		else
			return null;
	}

	public PersonaDTO personaToPersonaDTO(Persona persona) {
		PersonaDTO personaDTO = new PersonaDTO();
		personaDTO.setNome(persona.getNome());
		personaDTO.setId(persona.getIdPersona());
		personaDTO.setCognome(persona.getCognome());
		personaDTO.setCodiceFiscale(persona.getCodiceFiscale());
		personaDTO.setEmail(persona.getEmail());
		return personaDTO;
	}
	
	public String mapColonne(String colonna) {
		if (colonna.equals("name"))
			colonna = "nome";
		if (colonna.equals("lastname"))
			colonna = "cognome";
		if (colonna.equals("fiscalCode"))
			colonna = "codiceFiscale";
		return colonna;
		
	}

}
