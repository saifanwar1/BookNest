package com.airbnb.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "booking")
public class Booking {
    public int getTotalPrice;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property  property;

    @ManyToOne
    @JoinColumn(name = "property_user_id")
    private PropertyUser propertyUser;

    @Column(name = "total_nights", nullable = false)

    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer totalNights;

    public int getGetTotalPrice() {
        return getTotalPrice;
    }

    public void setGetTotalPrice(int getTotalPrice) {
        this.getTotalPrice = getTotalPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalNights() {
        return totalNights;
    }

    public void setTotalNights(Integer totalNights) {
        this.totalNights = totalNights;
    }

    public PropertyUser getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUser propertyUser) {
        this.propertyUser = propertyUser;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}