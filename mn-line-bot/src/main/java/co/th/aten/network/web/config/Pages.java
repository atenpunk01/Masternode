package co.th.aten.network.web.config;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import org.jboss.seam.security.annotations.LoggedIn;

@ViewConfig
public interface Pages {

	static enum Pages1 {

		@ViewPattern("/sec/*")
		@LoginView("/login.jsf")
		@LoggedIn
		PRIVATE,

		@FacesRedirect
		@ViewPattern("/login.jsf")
		LOGIN;
	}
}
