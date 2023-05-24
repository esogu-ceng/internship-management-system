package tr.edu.ogu.ceng.internationalization;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import tr.edu.ogu.ceng.model.Language;
import tr.edu.ogu.ceng.security.UserPrincipal;

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
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String languageAbbr = "tr"; // default
		String countryAbbr = "TR"; // default
		if (userPrincipal != null) {
			Language language = userPrincipal.getUser().getLanguage();
			if (language != null) {
				languageAbbr = language.getLanguageAbbr();
				countryAbbr = language.getCountryAbbr();
			}
		}
		return new Locale(languageAbbr, countryAbbr);
	}
}
