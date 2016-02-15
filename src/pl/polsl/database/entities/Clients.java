package pl.polsl.database.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clients table
 * 
 * @author Mateusz Sojka
 * @version 1.5
 */
@Entity
@Table(name = "CLIENTS")
public class Clients implements Serializable, IEntity {

    private static final long serialVersionUID = 7L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name="CLIENT_OR_COMPANY_NAME", nullable = false)
    private String clientOrCompanyName;
    
    @Column(name="PRICE", nullable = true)
    private double price;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "RESERVATION", nullable = false)
    private Promotions reservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    protected Clients(){
        
    }
    
    public Clients(String name, Double price, Promotions reservations){
        this.clientOrCompanyName=name;
        this.price = price;
        this.reservation=reservations;
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
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "pl.polsl.database.entities.Clients[ id=" + id + " clientOrCompanyName=" 
                + clientOrCompanyName + " price=" + price + " reservation=" 
                + reservation.toString() + " ]";
    }

    /**
     * @return the clientOrCompanyName
     */
    public String getClientOrCompanyName() {
        return clientOrCompanyName;
    }

    /**
     * @param clientOrCompanyName the clientOrCompanyName to set
     */
    public void setClientOrCompanyName(String clientOrCompanyName) {
        this.clientOrCompanyName = clientOrCompanyName;
    }

    /**
     * @return the reservations
     */
    public Promotions getReservations() {
        return reservation;
    }

    /**
     * @param reservations the reservations to set
     */
    public void setReservations(Promotions reservations) {
        this.reservation = reservations;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(Double price){
        this.price=price;
    }

}
