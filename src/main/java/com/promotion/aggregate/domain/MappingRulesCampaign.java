package com.promotion.aggregate.domain;


import javax.persistence.*;
import java.util.Date;

@Table(name = "mapping_rules_campaign")
@Entity
public class MappingRulesCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="description")
    private String  description;

    @Column(name="effective_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveStartDate;

    @Column(name="effective_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveEndDate;

    @Column(name="status")
    private String status;

    @Column(name="list_sys_rules__id")
    private String listSysRulesId;

    @Column(name="sys_campaign__id")
    private Integer sysCampaignId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getListSysRulesId() {
        return listSysRulesId;
    }

    public void setListSysRulesId(String listSysRulesId) {
        this.listSysRulesId = listSysRulesId;
    }

    public Integer getSysCampaignId() {
        return sysCampaignId;
    }

    public void setSysCampaignId(Integer sysCampaignId) {
        this.sysCampaignId = sysCampaignId;
    }
}
