package spring.config;

import org.springframework.format.Formatter;
import spring.dto.RoleDTO;

import java.text.ParseException;

import java.util.Locale;

public class RoleFormatter implements Formatter<RoleDTO> {

	@Override
	public String print (RoleDTO roleDTO, Locale locale) {
		if (roleDTO == null) {
			return "";
		} else {
			return String.format(locale, "%s, %s", roleDTO.getId(), roleDTO.getName());
		}
	}

	@Override
	public RoleDTO parse(String text, Locale locale) throws ParseException {
		if (text != null) {
			String[] parts = text.split(",");
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setId(parts[0].trim());
				roleDTO.setName(parts[1].trim());
				return roleDTO;


		}
		return null;
	}
}
