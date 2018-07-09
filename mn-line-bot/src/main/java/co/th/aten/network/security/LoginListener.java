package co.th.aten.network.security;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import org.jboss.seam.faces.event.PostLoginEvent;
import org.jboss.solder.logging.Logger;

@Specializes
public class LoginListener extends org.jboss.seam.faces.security.LoginListener {
	
	@Inject
	private Logger log;
	
	@Override
	public void observePostLoginEvent(@Observes PostLoginEvent event) {
		
		log.infov("observePostLoginEvent,CurrentPhaseId={0}",event.getFacesContext().getCurrentPhaseId());
		log.infov("event.getFacesContext().getExternalContext().isResponseCommitted()={0}",event.getFacesContext().getExternalContext().isResponseCommitted());
		if (!event.getFacesContext().getExternalContext().isResponseCommitted()){
			//super.observePostLoginEvent(event);
		}

	}
}
