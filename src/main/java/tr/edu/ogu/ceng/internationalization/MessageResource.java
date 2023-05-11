package tr.edu.ogu.ceng.internationalization;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MessageResource {

	private MessageSource messageSource;

	public String getMessage(String code) {
		Locale selectedLocale = getUserLocale();
		return messageSource.getMessage(code, null, selectedLocale);
	}

	public String getMessage(String code, Object... args) {
		Locale selectedLocale = getUserLocale();
		return messageSource.getMessage(code, args, selectedLocale);
	}

	private Locale getUserLocale() {
		return new Locale("tr", "TR"); // FIXME getLocale from user
	}
}
