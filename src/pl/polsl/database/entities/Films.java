package pl.polsl.database.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FILMY")
public class Films implements Serializable, IEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;

    @Column(name = "NAZWA", nullable = false, length = 511)
    private String name;

    @Column(name = "CZAS_TRWANIA", nullable = false, length = 20)
    private String durationTime;

    protected Films() {
    }

    public Films(String name, String durationTime) {
        this.name = name;
        this.durationTime = durationTime;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the durationTime
     */
    public String getDurationTime() {
        return durationTime;
    }

    /**
     * @param durationTime the durationTime to set
     */
    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Films)) {
            return false;
        }
        Films other = (Films) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return String.format("pl.polsl.database.entities.Rooms[ id=%d, name=%s, durationTime=%s]", 
                id, name, durationTime);
    }

}
