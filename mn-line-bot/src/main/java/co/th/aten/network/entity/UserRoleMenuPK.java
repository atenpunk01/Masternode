/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.network.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author IdeaPad
 */
@Embeddable
public class UserRoleMenuPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_id")
    private int roleId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "menu_id")
    private String menuId;

    public UserRoleMenuPK() {
    }

    public UserRoleMenuPK(int roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roleId;
        hash += (menuId != null ? menuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleMenuPK)) {
            return false;
        }
        UserRoleMenuPK other = (UserRoleMenuPK) object;
        if (this.roleId != other.roleId) {
            return false;
        }
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.UserRoleMenuPK[ roleId=" + roleId + ", menuId=" + menuId + " ]";
    }
    
}
