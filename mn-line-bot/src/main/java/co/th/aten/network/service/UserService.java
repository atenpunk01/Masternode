package co.th.aten.network.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.UserControl;
import co.th.aten.network.control.UserStore;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.security.annotation.AdminOrSys;
import co.th.aten.network.util.HashUtil;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105334552629163651L;

	@Inject
	Logger log;

	@Inject
	UserStore store;
	@Inject
	UserControl control;

	//	@Inject
	//	private AuditService auditService;

	//private Users user;

	public UserLogin getUser(int userId) {
		return store.getUser(userId);
	}

	public UserLogin authenticate(String loginName, String password) {
		log.info("authenticate UserLogin");
		HashUtil hashUtil = new HashUtil();
//		log.infov("**={0}",hashUtil.hash(password));
		return store.authicate(loginName, hashUtil.hash(password));
//		return store.authicate(loginName, password);
	}

	public void updateLoginTime(int userId) {
		store.updateLoginTime(userId);
	}

	@AdminOrSys
	public List<UserLogin> getUsersList(String searchString) {
		return control.getUsersList(searchString);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void disable(int userId) {
		store.disable(userId);
	}

//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@AdminOrSys
	public void enable(int userId) {
		store.enable(userId);
	}

	public boolean checkLoginName(int userId,String loginName){
		return store.checkLoginName(userId,loginName);
	}
	
	public boolean checkOldPassword(int userId, String oldPassword) {
		HashUtil hashUtil = new HashUtil();
		return store.checkOldPassword(userId, hashUtil.hash(oldPassword));
//		return store.checkOldPassword(userId, oldPassword);
	}

	public boolean changePassword(int userId, String newPassword) {
		HashUtil hashUtil = new HashUtil();
		return store.changePassword(userId, hashUtil.hash(newPassword));
//		return store.changePassword(userId, newPassword);
	}

	
	public boolean validate(UserLogin user){
		return control.validate(user);
	}
	
}
