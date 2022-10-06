package com.api.shoesshop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column
    private int quantity;

    @Column(name = "product_detail_id_pk")
    private long productDetailId;

    @Column(name = "cart_id_pk")
    private long cartId;

    @ManyToOne
    @JoinColumn(name = "product_detail_id_pk", insertable = false, updatable = false)
    private ProductDetail productDetail;

    public Long getId() {
        return id;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getCartId() {
        return cartId;
    }

    public long getProductDetailId() {
        return productDetailId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public void setProductDetailId(long productDetailId) {
        this.productDetailId = productDetailId;
    }
}
