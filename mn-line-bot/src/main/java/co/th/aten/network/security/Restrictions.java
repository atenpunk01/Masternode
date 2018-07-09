package co.th.aten.network.security;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.Constants;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.security.annotation.Admin;
import co.th.aten.network.security.annotation.AdminOrSys;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.security.annotation.Manager;
import co.th.aten.network.security.annotation.Report;
import co.th.aten.network.security.annotation.Supervisor;
import co.th.aten.network.security.annotation.Sys;
import co.th.aten.network.security.annotation.SysOrAdminHq;

public class Restrictions {

	@Inject
	Logger log;

	@Inject
	@Authenticated
	private Instance<UserLogin> user;

	@Secures
	@Admin
	public boolean isAdmin(Identity identity) {
		log.debug("isAdmin," + user.get().getLoginName());
		return identity.hasRole("admin", "1", "GROUP");
	}

	@Secures
	@Sys
	public boolean isSys(Identity identity) {
		log.debug("isSys," + user.get().getLoginName());
		return identity.hasRole("sys", "1", "GROUP");
	}

	@Secures
	@AdminOrSys
	public boolean isAdminOrSys(Identity identity) {
		log.debug("isSys," + user.get().getLoginName());
		if (identity.hasRole("sys", "1", "GROUP")) {
			return true;
		}
		log.debug("isAdmin," + user.get().getLoginName());
		return isAdmin(identity);
	}

	@Secures
	@SysOrAdminHq
	public boolean isAdminOrSysHq(Identity identity) {
		log.debug("isSysHQ," + user.get().getLoginName());
		return false;
	}

	@Secures
	@Report
	public boolean isReport(Identity identity) {
		log.debug("isReport," + user.get().getLoginName());
		return false;
	}

	@Secures
	@Supervisor
	public boolean isSupervisor(Identity identity) {
		log.debug("isSupervisor," + user.get().getLoginName());
		return false;
	}

	@Secures
	@Manager
	public boolean isManager(Identity identity) {
		log.debug("isManager," + user.get().getLoginName());
		return false;
	}

}
