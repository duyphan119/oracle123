package com.api.shoesshop.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "account_id_pk")
    private long accountId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id_pk")
    private Set<CartItem> items = new HashSet<>();

    // @OneToOne(targetEntity = Account.class)
    // private Account account;

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public Set<CartItem> getItems() {
        return items;
    }
}
