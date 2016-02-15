package pl.polsl.database.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Users table
 * 
 * @author Mateusz Sojka
 * @version 1.3
 */
@Entity
@Table(name="USERS")
public class Users implements Serializable, IEntity {

    private static final long serialVersionUID = 8L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false, unique = false)
    private String password;

    @Column(name="TYPE", nullable=false)
    private String type;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected Users() {
    }

    public Users(String user, String password, String type) {
        this.username = user;
        this.password = password;
        this.type = type;
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
        return "pl.polsl.database.entities.Users[ id=" + id + " username=" 
                + username + " password=" + password + " type=" + type + " ]";
    }
    
    public static ArrayList<String> getPrivilegesTypes(){
        ArrayList<String> privArray = new ArrayList<>();
        privArray.add("alterPriv");
        privArray.add("deletePriv");
        privArray.add("insertPriv");
        privArray.add("selectPriv");
        privArray.add("updatePriv");
        privArray.add("createPriv");
        privArray.add("allPriv");
        return privArray;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
