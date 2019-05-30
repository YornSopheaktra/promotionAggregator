package com.promotion.aggregate.domain;


import javax.persistence.*;

@Table(name="sys_campaign")
@Entity
public class SysCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;

    @Column(name="sys_campaign_category__id")
    private Integer sysCampaignCategoryId;

    @Column(name="promotion_type")
    private String promotionType;

    @Column(name="promotion_model")
    private String promotionModel;

    @Column(name = "stock_account_id")
    private  String stockAccountId;

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionModel() {
        return promotionModel;
    }

    public void setPromotionModel(String promotionModel) {
        this.promotionModel = promotionModel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSysCampaignCategoryId() {
        return sysCampaignCategoryId;
    }

    public void setSysCampaignCategoryId(Integer sysCampaignCategoryId) {
        this.sysCampaignCategoryId = sysCampaignCategoryId;
    }

    public String getStockAccountId() {
        return stockAccountId;
    }

    public void setStockAccountId(String stockAccountId) {
        this.stockAccountId = stockAccountId;
    }
}
