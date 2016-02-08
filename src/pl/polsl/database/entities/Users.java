package pl.polsl.database.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author matis
 */
@Entity
public class Users implements Serializable, IEntity {

    private static final long serialVersionUID = 8L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user", nullable = false, unique = true)
    private String user;

    @Column(name = "password", nullable = false, unique = false)
    private String password;

    @Column(name = "alterPriv", nullable = false)
    private boolean alterPrivileges;

    @Column(name = "deletePriv", nullable = false)
    private boolean deletePrivileges;

    @Column(name = "insertPriv", nullable = false)
    private boolean insertPrivileges;

    @Column(name = "selectPriv", nullable = false)
    private boolean selectPrivileges;

    @Column(name = "updatePriv", nullable = false)
    private boolean updatePrivileges;

    @Column(name = "createPriv", nullable = false)
    private boolean createPrivileges;

    @Column(name = "allPriv", nullable = false)
    private boolean allPrivileges;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected Users() {
    }

    public Users(String user, String password, boolean alter, boolean delete, boolean insert,
            boolean select, boolean update, boolean create, boolean all) {
        this.user = user;
        this.password = password;
        this.alterPrivileges = alter;
        this.deletePrivileges = delete;
        this.insertPrivileges = insert;
        this.selectPrivileges = select;
        this.updatePrivileges = update;
        this.createPrivileges = create;
        this.allPrivileges = all;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pl.polsl.database.entities.Users[ id=" + id + " ]";
    }

    /**
     * @return the alterPrivileges
     */
    public boolean isAlterPrivileges() {
        return alterPrivileges;
    }

    /**
     * @param alterPrivileges the alterPrivileges to set
     */
    public void setAlterPrivileges(boolean alterPrivileges) {
        this.alterPrivileges = alterPrivileges;
    }

    /**
     * @return the deletePrivileges
     */
    public boolean isDeletePrivileges() {
        return deletePrivileges;
    }

    /**
     * @param deletePrivileges the deletePrivileges to set
     */
    public void setDeletePrivileges(boolean deletePrivileges) {
        this.deletePrivileges = deletePrivileges;
    }

    /**
     * @return the insertPrivileges
     */
    public boolean isInsertPrivileges() {
        return insertPrivileges;
    }

    /**
     * @param insertPrivileges the insertPrivileges to set
     */
    public void setInsertPrivileges(boolean insertPrivileges) {
        this.insertPrivileges = insertPrivileges;
    }

    /**
     * @return the selectPrivileges
     */
    public boolean isSelectPrivileges() {
        return selectPrivileges;
    }

    /**
     * @param selectPrivileges the selectPrivileges to set
     */
    public void setSelectPrivileges(boolean selectPrivileges) {
        this.selectPrivileges = selectPrivileges;
    }

    /**
     * @return the updatePrivileges
     */
    public boolean isUpdatePrivileges() {
        return updatePrivileges;
    }

    /**
     * @param updatePrivileges the updatePrivileges to set
     */
    public void setUpdatePrivileges(boolean updatePrivileges) {
        this.updatePrivileges = updatePrivileges;
    }

    /**
     * @return the createPrivileges
     */
    public boolean isCreatePrivileges() {
        return createPrivileges;
    }

    /**
     * @param createPrivileges the createPrivileges to set
     */
    public void setCreatePrivileges(boolean createPrivileges) {
        this.createPrivileges = createPrivileges;
    }

    /**
     * @return the allPrivileges
     */
    public boolean isAllPrivileges() {
        return allPrivileges;
    }

    /**
     * @param allPrivileges the allPrivileges to set
     */
    public void setAllPrivileges(boolean allPrivileges) {
        this.allPrivileges = allPrivileges;
    }

    public boolean checkUserPrivileges(String requestedPriv) {
        switch (requestedPriv) {
            case "alterPriv":
                return this.isAlterPrivileges();
            case "deletePriv":
                return this.isDeletePrivileges();
            case "insertPriv":
                return this.isInsertPrivileges();
            case "selectPriv":
                return this.isSelectPrivileges();
            case "updatePriv":
                return this.isUpdatePrivileges();
            case "createPriv":
                return this.isCreatePrivileges();
            case "allPriv":
                return this.isAllPrivileges();
            default:
                return false;
        }
    }

}
