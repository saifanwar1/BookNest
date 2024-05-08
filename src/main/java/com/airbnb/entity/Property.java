package com.airbnb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "bedroom", nullable = false, length = 45)
    private String bedrooms;

    @Column(name = "bathrooms", nullable = false, length = 200)
    private String bathrooms;

    @Column(name = "guests", nullable = false)
    private Integer guests;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightly_price;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



    public Integer getNightly_price() {
        return nightly_price;
    }

    public void setNightly_price(Integer nightly_price) {
        this.nightly_price = nightly_price;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}