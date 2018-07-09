/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.network.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IdeaPad
 */
@Entity
@Table(name = "user_role_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRoleMenu.findAll", query = "SELECT u FROM UserRoleMenu u"),
    @NamedQuery(name = "UserRoleMenu.findByRoleId", query = "SELECT u FROM UserRoleMenu u WHERE u.userRoleMenuPK.roleId = :roleId"),
    @NamedQuery(name = "UserRoleMenu.findByMenuId", query = "SELECT u FROM UserRoleMenu u WHERE u.userRoleMenuPK.menuId = :menuId")})
public class UserRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserRoleMenuPK userRoleMenuPK;
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserMenu userMenu;

    public UserRoleMenu() {
    }

    public UserRoleMenu(UserRoleMenuPK userRoleMenuPK) {
        this.userRoleMenuPK = userRoleMenuPK;
    }

    public UserRoleMenu(int roleId, String menuId) {
        this.userRoleMenuPK = new UserRoleMenuPK(roleId, menuId);
    }

    public UserRoleMenuPK getUserRoleMenuPK() {
        return userRoleMenuPK;
    }

    public void setUserRoleMenuPK(UserRoleMenuPK userRoleMenuPK) {
        this.userRoleMenuPK = userRoleMenuPK;
    }

    public UserMenu getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleMenuPK != null ? userRoleMenuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleMenu)) {
            return false;
        }
        UserRoleMenu other = (UserRoleMenu) object;
        if ((this.userRoleMenuPK == null && other.userRoleMenuPK != null) || (this.userRoleMenuPK != null && !this.userRoleMenuPK.equals(other.userRoleMenuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.UserRoleMenu[ userRoleMenuPK=" + userRoleMenuPK + " ]";
    }
    
}
