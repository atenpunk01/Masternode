/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.network.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IdeaPad
 */
@Entity
@Table(name = "user_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserMenu.findAll", query = "SELECT u FROM UserMenu u"),
    @NamedQuery(name = "UserMenu.findByMenuId", query = "SELECT u FROM UserMenu u WHERE u.menuId = :menuId"),
    @NamedQuery(name = "UserMenu.findByTitleId", query = "SELECT u FROM UserMenu u WHERE u.titleId = :titleId"),
    @NamedQuery(name = "UserMenu.findByNameTitle", query = "SELECT u FROM UserMenu u WHERE u.nameTitle = :nameTitle"),
    @NamedQuery(name = "UserMenu.findByNameMenu", query = "SELECT u FROM UserMenu u WHERE u.nameMenu = :nameMenu")})
public class UserMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "menu_id")
    private String menuId;
    @Column(name = "title_id")
    private Integer titleId;
    @Size(max = 200)
    @Column(name = "name_title")
    private String nameTitle;
    @Size(max = 500)
    @Column(name = "name_menu")
    private String nameMenu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userMenu")
    private Collection<UserRoleMenu> userRoleMenuCollection;

    public UserMenu() {
    }

    public UserMenu(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    @XmlTransient
    public Collection<UserRoleMenu> getUserRoleMenuCollection() {
        return userRoleMenuCollection;
    }

    public void setUserRoleMenuCollection(Collection<UserRoleMenu> userRoleMenuCollection) {
        this.userRoleMenuCollection = userRoleMenuCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuId != null ? menuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserMenu)) {
            return false;
        }
        UserMenu other = (UserMenu) object;
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.th.aten.network.entity.UserMenu[ menuId=" + menuId + " ]";
    }
    
}
