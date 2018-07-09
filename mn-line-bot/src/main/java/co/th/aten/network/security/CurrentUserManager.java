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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.Constants;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.entity.UserRoleMenu;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;


/**
 * Exposes the currently logged in user
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
@SessionScoped
@Named("currentUserManager")
public class CurrentUserManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7016685883035435434L;
	@Inject
	Logger log;

	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private Identity identity;
	private List<UserRoleMenu> userRoleMenuList;
	private boolean menu01;
	private boolean menu02;
	private boolean menu03;
	private boolean menu04;
	private boolean menu05;
	private boolean menu06;
	private boolean menu07;
	private boolean menu08;
	private UserLogin currentUser;
	
	private int numberFund;
	private int totalFund;

	@Produces
	@Authenticated
	@Named("currentUser")
	public UserLogin getCurrentAccount() {
		return currentUser;
	}

	// Injecting HttpServletRequest instead of HttpSession as the latter conflicts with a Weld bean on GlassFish 3.0.1
	public void onLogin(@Observes @Authenticated UserLogin user, HttpServletRequest request) {
		currentUser = user;
		log.infov("set current UserLogin,userId={0},loginName={1},userName={2}",currentUser.getUserId(),currentUser.getLoginName(),currentUser.getUserName());

		checkPermission();
		// reward authenticated users with a longer session
		// default is kept short to prevent search engines from driving up # of sessions
		// request.getSession().setMaxInactiveInterval(3600);
		
		if(currentUser.getGroupId().getGroupId()==6){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) facesContext
					.getExternalContext()
					.getContext();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			HttpServletResponse res = (HttpServletResponse) response;
			try {
				res.sendRedirect(servletContext.getContextPath() + "/partner/chart");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(currentUser.getGroupId().getGroupId()==7){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) facesContext
					.getExternalContext()
					.getContext();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			HttpServletResponse res = (HttpServletResponse) response;
			try {
				res.sendRedirect(servletContext.getContextPath() + "/store/sell");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkPermission(){
		userRoleMenuList = new ArrayList<UserRoleMenu>();
		menu01 = false;
		menu02 = false;
		menu03 = false;
		menu04 = false;
		menu05 = false;
		menu06 = false;
		menu07 = false;
		menu08 = false;
		try {
			log.info(Constants.LOGGER_FORMAT+currentUser.getUserId()+"; "+"--------------- role id "+currentUser.getRoleId());
			if(currentUser.getRoleId()!=null){
				userRoleMenuList = em.createQuery("From UserRoleMenu " +
						" Where userRoleMenuPK.roleId =:roleId ",UserRoleMenu.class)
						.setParameter("roleId", currentUser.getRoleId().intValue())
						.getResultList();
				if(userRoleMenuList!=null){
					for(UserRoleMenu userRoleMenu:userRoleMenuList){
						if(userRoleMenu.getUserMenu().getTitleId() == 1){
							menu01 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 2){
							menu02 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 3){
							menu03 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 4){
							menu04 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 5){
							menu05 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 6){
							menu06 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 7){
							menu07 = true;
						}
						if(userRoleMenu.getUserMenu().getTitleId() == 8){
							menu08 = true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public boolean menuDisable(String menuId) throws IOException{
		boolean chk = false;
		if(userRoleMenuList!=null){
			for(UserRoleMenu userRoleMenu:userRoleMenuList){
				if(userRoleMenu.getUserRoleMenuPK().getMenuId().equals(menuId)){
					chk = true;
					break;
				}
			}
		}
		return chk;
	}

	public void menuActive(String menuId) throws IOException{
		boolean chk = false;
		if(userRoleMenuList!=null){
			for(UserRoleMenu userRoleMenu:userRoleMenuList){
				if(userRoleMenu.getUserRoleMenuPK().getMenuId().equals(menuId)){
					log.info("success page menuId = "+menuId);
					chk = true;
					break;
				}
			}
		}
		if(!chk){
			log.info("denied page menuId = "+menuId);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) facesContext
					.getExternalContext()
					.getContext();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(servletContext.getContextPath() + "/denied");
			throw new org.jboss.seam.security.AuthorizationException("Access Denied");
		}
	}

	public boolean getMenu01() {
		return menu01;
	}

	public void setMenu01(boolean menu01) {
		this.menu01 = menu01;
	}

	public boolean getMenu02() {
		return menu02;
	}

	public void setMenu02(boolean menu02) {
		this.menu02 = menu02;
	}

	public boolean getMenu03() {
		return menu03;
	}

	public void setMenu03(boolean menu03) {
		this.menu03 = menu03;
	}

	public boolean getMenu04() {
		return menu04;
	}

	public void setMenu04(boolean menu04) {
		this.menu04 = menu04;
	}

	public boolean getMenu05() {
		return menu05;
	}

	public void setMenu05(boolean menu05) {
		this.menu05 = menu05;
	}

	public boolean isMenu06() {
		return menu06;
	}

	public void setMenu06(boolean menu06) {
		this.menu06 = menu06;
	}

	public boolean isMenu07() {
		return menu07;
	}

	public void setMenu07(boolean menu07) {
		this.menu07 = menu07;
	}

	public int getNumberFund() {
		return numberFund;
	}

	public void setNumberFund(int numberFund) {
		this.numberFund = numberFund;
	}

	public int getTotalFund() {
		return totalFund;
	}

	public void setTotalFund(int totalFund) {
		this.totalFund = totalFund;
	}

	public boolean isMenu08() {
		return menu08;
	}

	public void setMenu08(boolean menu08) {
		this.menu08 = menu08;
	}

}
