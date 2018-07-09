package co.th.aten.network.security;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.events.AlreadyLoggedInEvent;
import org.jboss.seam.security.events.LoginFailedEvent;
import org.jboss.seam.security.events.NotLoggedInEvent;
import org.jboss.seam.security.events.PostAuthenticateEvent;
import org.jboss.solder.core.Requires;

@ApplicationScoped
@Requires("org.jboss.seam.international.status.Messages")
public class SecurityEventMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4577602535014320386L;
	
	private static final String DEFAULT_LOGIN_FAILED_MESSAGE = "Login failed - please check your username and password before trying again.";
	private static final String DEFAULT_LOGIN_SUCCESSFUL_MESSAGE = "Hello Welcome, {0}.";
	private static final String DEFAULT_ALREADY_LOGGED_IN_MESSAGE = "You're already logged in. Please log out first if you wish to log in again.";
	private static final String DEFAULT_NOT_LOGGED_IN_MESSAGE = "Please log in first.";

	public void postAuthenticate(@Observes PostAuthenticateEvent event, Messages messages, Identity identity) {
		//messages.info(DEFAULT_LOGIN_SUCCESSFUL_MESSAGE, identity.getUser().getId());
	}

	public void addLoginFailedMessage(@Observes LoginFailedEvent event, Messages messages) {
		//messages.error(DEFAULT_LOGIN_FAILED_MESSAGE);
	}

//	public void addLoginSuccessMessage(@Observes LoggedInEvent event, Messages messages, Credentials credentials) {
//		messages.info(DEFAULT_LOGIN_SUCCESSFUL_MESSAGE, credentials.getUsername());
//	}

	public void addAlreadyLoggedInMessage(@Observes AlreadyLoggedInEvent event, Messages messages) {
		messages.error(DEFAULT_ALREADY_LOGGED_IN_MESSAGE);
	}

	public void addNotLoggedInMessage(@Observes NotLoggedInEvent event, Messages messages) {
		messages.error(DEFAULT_NOT_LOGGED_IN_MESSAGE);
	}
}
