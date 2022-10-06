package com.api.shoesshop.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue
    @Column(name = "product_detail_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "product_id_pk", nullable = false)
    private long productId;

    @Column(name = "inventory", nullable = false)
    private int inventory;

    @Column(name = "weight", nullable = false)
    private int weight;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Product.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id_pk", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "product_variant_details", joinColumns = {
            @JoinColumn(name = "product_detail_id_pk") }, inverseJoinColumns = {
                    @JoinColumn(name = "variant_value_id_pk") })
    List<VariantValue> variantValues = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getProductId() {
        return this.productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getInventory() {
        return this.inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<VariantValue> getVariantValues() {
        return this.variantValues;
    }

    public void setVariantValues(List<VariantValue> variantValues) {
        this.variantValues = variantValues;
    }

}
