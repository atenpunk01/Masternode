package co.th.aten.network.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.security.Restrictions;
import co.th.aten.network.security.annotation.Authenticated;


public class UserControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463221528014730255L;

	@Inject
	Logger log;

	@Inject
	UserStore userStore;


	@Inject
	@Authenticated
	private Instance<UserLogin> userLogin;

	@Inject
	Identity identity;
	@Inject
	Restrictions restrictions;
	
	//@Inject
	//AuditService auditService;


	public List<UserLogin> getUsersList(String searchString) {
		UserLogin u = userLogin.get();
		if (u != null && identity != null) {
			log.info("current user id=" + u.getUserId());
			if (restrictions.isSys(identity)) {
				return userStore.getUsersList(searchString);
			} else {
//				if (u.getDepartmentid().getDepartmentid() == 9) {
//					return userStore.getUsersListByPostOffice(u.getPostoffice(), searchString);
//				} else if (u.getDepartmentid().getDepartmentid() == 91) {
//					PostCode2 po = postCode2Store.getPostCode(u.getPostoffice());
//					short regId = po.getRegId().getRegId();
//					return userStore.getUsersListByReg(regId, null, searchString);
//				} else if (u.getDepartmentid().getDepartmentid() == 92) {
//					PostCode2 po = postCode2Store.getPostCode(u.getPostoffice());
//					String upper = po.getUpperCenter();
//					return userStore.getUsersListByUpperCenter(upper, null, searchString);
//				} else {
//					return userStore.getUsersListByDepartment(u.getDepartmentid().getDepartmentid(), searchString);
//				}
			}
		}
		return new ArrayList<UserLogin>();
	}

	public boolean validate(UserLogin user) {

		if (!userStore.checkLoginName(user.getUserId(), user.getLoginName())) {
			return false;
		}


		return true;
	}
	
}
