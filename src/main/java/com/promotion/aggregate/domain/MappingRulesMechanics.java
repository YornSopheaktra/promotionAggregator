package com.promotion.aggregate.domain;

import javax.persistence.*;

@Entity
@Table(name = "mapping_rules_mechanics")
public class MappingRulesMechanics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="sys_rule__id")
    private Integer sysRuleId;

    @Column(name="sys_mechanic__id")
    private Integer sysMechanicId;

    @Column(name="type")
    private String type;

    @Column(name="status")
    private String status;

    
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

    public Integer getSysRuleId() {
        return sysRuleId;
    }

    public void setSysRuleId(Integer sysRuleId) {
        this.sysRuleId = sysRuleId;
    }

    public Integer getSysMechanicId() {
        return sysMechanicId;
    }

    public void setSysMechanicId(Integer sysMechanicId) {
        this.sysMechanicId = sysMechanicId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
