/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.th.aten.network.security;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.DefaultBundleKey;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.service.UserService;

/**
 * This implementation of a <strong>Authenticator</strong> that uses Seam
 * security.
 * 
 * @author <a href="http://community.jboss.org/people/spinner)">Jose Rodolfo
 *         freitas</a>
 */
@Stateful
@Named("schneiderAuthenticator")
public class SchneiderAuthenticator extends BaseAuthenticator implements Authenticator {

	@Inject
	private Logger log;

	@Inject
	private Credentials credentials;

	@Inject
	private Messages messages;

	@Inject
	@Authenticated
	private Event<UserLogin> loginEventSrc;

	@Inject
	private Identity identity;

	@Inject
	private UserService userService;

	public void authenticate() {
		log.info("Logging in " + credentials.getUsername());
		if ((credentials.getUsername() == null) || (credentials.getCredential() == null)) {
			messages.error(new DefaultBundleKey("identity_loginFailed")).defaults("Invalid username or password");
			setStatus(AuthenticationStatus.FAILURE);
		}
		PasswordCredential passwordCredential = (PasswordCredential) credentials.getCredential();
		UserLogin user = userService.authenticate(credentials.getUsername(), passwordCredential.getValue());

		if (user != null 
				&& (user.getGroupId().getGroupId()==1 
				    ||user.getGroupId().getGroupId()==2) ) {
			loginEventSrc.fire(user);
//			messages.info(new DefaultBundleKey("identity_loggedIn"), user.getLoginName()).defaults("You're signed in as {0}")
//			.params(user.getLoginName());
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(new SimpleUser(user.getLoginName()));

			//			try {
			//				List<UserRole> urList = userService.getUserRole(user.getUserId());
			//				identity.addGroup("1", "STAFF");
			//				for (UserRole ur : urList) {
			//					if (ur.getRole().getRoleKey() != null && ur.getRole().getRoleKey().equals("ROLE_USER")) {
			//						identity.addRole("user", "1", "STAFF");
			//					}
			//					if (ur.getRole().getRoleKey() != null && ur.getRole().getRoleKey().equals("ROLE_ADMIN")) {
			//						identity.addRole("admin", "1", "STAFF");
			//					}
			//				}
			//				if (urList.size() == 0) {
			identity.addRole("user", "1", "STAFF");
			//				}
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//			}
			return;
		} else {			
			// login fail invalid password
			log.infov("invalid password , user={0},invalidCount={1}", credentials.getUsername(), 0);
			messages.error(new DefaultBundleKey("identity_loginFailed")).defaults("Invalid username or password");
			setStatus(AuthenticationStatus.FAILURE);
		}

	}

}
