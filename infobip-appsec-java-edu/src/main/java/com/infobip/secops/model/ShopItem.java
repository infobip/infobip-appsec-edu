package com.infobip.secops.model;

import javax.persistence.*;

@Entity
@Table(name = "shopitem")
public class ShopItem {

    @Id
    @GeneratedValue
    @Column(name = "shopitem_id")
    private Long id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "name", unique = true)
    private String name;

    public ShopItem() {
    }

    public ShopItem(Integer price, String name) {
        this();
        this.price = price;
        this.name = name;
    }

    public ShopItem(Long id, Integer price, String name) {
        this(price, name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
