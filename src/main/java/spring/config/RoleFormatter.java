package spring.config;

import com.google.gson.Gson;
import org.springframework.format.Formatter;
import spring.dto.RoleDTO;

import java.util.Locale;

public class RoleFormatter implements Formatter<RoleDTO> {

    @Override
    public String print(RoleDTO roleDTO, Locale locale) {
        return roleDTO != null ? roleDTO.toString() : null;
    }

    @Override
    public RoleDTO parse(String text, Locale locale) {
            return new Gson().fromJson(text, RoleDTO.class);
    }
}