package com.promotion.aggregate.domain;

import javax.persistence.*;

@Entity
@Table(name="sys_rm_condition_details")
public class SysRmConditionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="key_value")
    private String keyValue;

    @Column(name="key_result")
    private String keyResult;

    @Column(name="sys_condition__id")
    private Integer sysConditionId;

    @Column(name="type")
    private String type;

    @Column(name="status")
    private String status;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name="operator")
    private String operator;

    @Column(name = "key_value_type")
    private String keyValueType;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(String keyValueType) {
        this.keyValueType = keyValueType;
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

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyResult() {
        return keyResult;
    }

    public void setKeyResult(String keyResult) {
        this.keyResult = keyResult;
    }

    public Integer getSysConditionId() {
        return sysConditionId;
    }

    public void setSysConditionId(Integer sysConditionId) {
        this.sysConditionId = sysConditionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
