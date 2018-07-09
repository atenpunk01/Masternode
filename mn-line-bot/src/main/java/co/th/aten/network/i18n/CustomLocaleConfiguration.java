package co.th.aten.network.i18n;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.seam.international.locale.LocaleConfiguration;
import org.jboss.solder.logging.Logger;

public class CustomLocaleConfiguration extends LocaleConfiguration {

	@Inject
	private Logger log;
	
	@PostConstruct
	public void setup() {
		log.info("setup supported locale");
		addSupportedLocaleKey("en");
		addSupportedLocaleKey("th");
	}
}
