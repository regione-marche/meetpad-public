package cdst_be_marche.builder;

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

import cdst_be_marche.DTO.EnteDTO;
import cdst_be_marche.DTO.PersonaDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.exception.ParseDateException;
import cdst_be_marche.model.Ente;
import cdst_be_marche.model.Persona;
import cdst_be_marche.model.bean._Typological;

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

	protected LabelValue createNotNullLabelValue(_Typological typological) {
		if (typological != null) {
			String descrizione = messageSource.getMessage(typological.getDescrizione(), null,
					typological.getDescrizione(), request.getLocale());
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

	public EnteDTO enteToEnteDTO(Ente ente) {
		EnteDTO enteDTO = new EnteDTO();
		enteDTO.setNome(ente.getDescrizioneEnte());
		enteDTO.setDescrizione(ente.getDescrizioneEnte());
		enteDTO.setCodiceFiscale(ente.getCodiceFiscaleEnte());
		enteDTO.setPec(ente.getPecEnte());
		enteDTO.setId(ente.getIdEnte());
		return enteDTO;
	}
	
	public Ente enteDTOToEnte(EnteDTO enteDTO) {
		Ente ente = new Ente();
		ente.setDescrizioneEnte(enteDTO.getDescrizione());
		ente.setCodiceFiscaleEnte(enteDTO.getCodiceFiscale());
		ente.setFlagAmministrazioneProcedente(enteDTO.getFlagAmmProc());
		ente.setFlagAmministrazionePrincipale(Boolean.FALSE);
		if (enteDTO.getId() != null) {
			ente.setIdEnte(enteDTO.getId());
		}
		ente.setPecEnte(enteDTO.getPec());
		return ente;
	}

}
