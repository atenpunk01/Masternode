package co.th.aten.network.control;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.producer.DBDefault;

public class UserStore extends BasicStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7974450769728194068L;

	@Inject
	private Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	public UserLogin authicate(String loginName, String password) {
		log.info("authicate user");
		UserLogin user = getUser(loginName);
		if (user != null) {			
			if (user.getPassword().equals(password)) {
				log.infov("login success,{0}", user.getLoginName());
				updateLoginTime(user.getUserId());				
				refresh(user);
				log.info("user first name="+user.getUserName());
				//auditService.auditLogin(user.getUserid(), "SUCCESS");
				return user;
			} else {
				log.infov("login fail,{0}", user.getLoginName());
//				updateLoginFail(user.getUserid(), user.getInvalidpwcount());
				//user = userStore.getUserById(user.getUserid());
				//auditService.auditLogin(user.getUserid(), "FAIL("+user.getInvalidpwcount()+")");
				return null;
			}
		}

		return null;
	}

	public List<UserLogin> getUsersList(String searchString) {
		String orderBy = "loginName";
		return em.createQuery("From UserLogin where " +
				" (loginName like :search " +
				" or userName like :search " +
				"  )" +
				" and status = 0 order by " + buildOrderBy(orderBy), UserLogin.class)
				.setParameter("search", buildSearchString(searchString))
				.getResultList();
	}

	public UserLogin getUser(int userId) {
		log.debug("find UserLogin where userId=" + userId);
		List<UserLogin> users = em.createQuery("From UserLogin where userid=:userId ", UserLogin.class)
				.setParameter("userId", userId)
				.getResultList();
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public UserLogin getUser(String loginName) {
		log.debug("find UserLogin where loginname=" + loginName);
		List<UserLogin> users = em.createQuery("From UserLogin where loginName=:login  and status = 0 ", UserLogin.class)
				.setParameter("login", loginName)
				.getResultList();
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public UserLogin getUserById(int userId) {
		log.debug("find UserLogin where userId=" + userId);
		return em.createQuery("From UserLogin where userId=:userId and status = 0 ", UserLogin.class)
				.setParameter("userId", userId)
				.getSingleResult();
	}

	public UserLogin getUserByIdAll(int userId) {
		log.debug("find UserLogin where userId=" + userId);
		return em.createQuery("From UserLogin where userId=:userId ", UserLogin.class)
				.setParameter("userId", userId)
				.getSingleResult();
	}

	public void updateLoginTime(int userId) {
		em.createQuery("update UserLogin set lastLogin = :lastTimeLogin where userId = :userId ")
				.setParameter("lastTimeLogin", new Date(), TemporalType.TIMESTAMP)
				.setParameter("userId", userId)
				.executeUpdate();
	}

//	public void updateLoginFail(int userId, int invalidCount) {
//		//		user.setInvalidpwcount((short)(user.getInvalidpwcount()+1));
//		//		user.setLastinvalidpwd(new Date());		
//		em.createQuery(
//				"update User set lastinvalidpwd = :lastInvalidPwd , invalidpwcount = :invalidCount where userid = :userId ")
//				.setParameter("lastInvalidPwd", new Date(), TemporalType.TIMESTAMP)
//				.setParameter("invalidCount", (short) (invalidCount + 1))
//				.setParameter("userId", userId)
//				.executeUpdate();
//	}

	public void refresh(UserLogin user) {
		em.refresh(user);
	}

	public void disable(int userId) {
		if (userId != 1) {
			int rows = em.createQuery("update UserLogin set status = 1 where userId = :userId")
					.setParameter("userId", userId)
					.executeUpdate();
			log.infov("set isdisable ,userId={0},updated={1}", userId, rows);
		}
	}

	public void enable(int userId) {
		int rows = em.createQuery("update UserLogin set status = 0 where userId = :userId")
				.setParameter("userId", userId)
				.executeUpdate();
		log.infov("unset isdisable ,userId={0},updated={1}", userId, rows);
	}

	public boolean checkOldPassword(int userId, String oldPassword) {
		UserLogin user = em.find(UserLogin.class, userId);
		if (user != null) {
			if (user.getPassword().equals(oldPassword)) {
				return true;
			}
		}
		return false;
	}
	

	public boolean checkLoginName(int userId, String loginName) {
		int count = 0;
		if (userId != 0) {
			count = em.createQuery("select count(*) from UserLogin where loginName = :loginName " +
					" and userId <> :userId " +
					" and status = :cancel ", Long.class)
					.setParameter("loginName", loginName)
					.setParameter("userId", userId)
					.setParameter("cancel", (short) 0)
					.getSingleResult().intValue();
		} else {
			count = em.createQuery("select count(*) from UserLogin where loginName = :loginName " +
					" and status = :cancel ", Long.class)
					.setParameter("loginName", loginName)
					.setParameter("cancel", (short) 0)
					.getSingleResult().intValue();
		}
		if (count > 0) {
			return false;
		}
		return true;
	}

	public boolean changePassword(int userId, String newPassword) {
		int row = em.createQuery("update UserLogin set password = :hpasswd ," +
				" forceChange = :forceChange where userId = :userId")
				.setParameter("hpasswd", newPassword)
				.setParameter("userId", userId)
				.setParameter("forceChange", (short) 0)
				.executeUpdate();
		log.infov("change password,userId={0},success={1}", userId, row);
		if (row == 1) {
			return true;
		}
		return false;
	}

}
