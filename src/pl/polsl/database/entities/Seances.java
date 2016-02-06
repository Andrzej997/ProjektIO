package pl.polsl.database.entities;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;

@Entity
@Table(name="SEANSE")
public class Seances implements Serializable, IEntity{

    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = false)
    private Long id;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="ID_FILMU")
    private Films film;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="NUMER_SALI")
    private Rooms room;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name="DATA_SEANSU", nullable = false)
    private Calendar date;
    
    @Column(name="PODSTAWOWA_CENA_BILETU", nullable = true, precision = 2)
    private double basicTicketPrice;

    protected Seances(){}
    public Seances(Films film, Rooms room, Calendar date, double basicTicketPrice){
        this.film=film;
        this.room=room;
        this.date=date;
        this.basicTicketPrice=basicTicketPrice;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seances)) {
            return false;
        }
        Seances other = (Seances) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pl.polsl.database.entities.Seances[ id=" + id + " ]";
    }

    /**
     * @return the film
     */
    public Films getFilm() {
        return film;
    }

    /**
     * @param film the film to set
     */
    public void setFilm(Films film) {
        this.film = film;
    }

    /**
     * @return the room
     */
    public Rooms getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Rooms room) {
        this.room = room;
    }

    /**
     * @return the date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * @return the basicTicketPrice
     */
    public double getBasicTicketPrice() {
        return basicTicketPrice;
    }

    /**
     * @param basicTicketPrice the basicTicketPrice to set
     */
    public void setBasicTicketPrice(double basicTicketPrice) {
        this.basicTicketPrice = basicTicketPrice;
    }
    
}
