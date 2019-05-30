package com.promotion.aggregate.domain;

import javax.persistence.*;

@Entity
@Table(name = "sys_action_data")
public class SysActionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sys_action__id")
    private Integer sysActionId;

    @Column(name = "key_name")
    private String keyName	;

    @Column(name = "key_value_type")
    private String keyValueType;

    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "status")
    private  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysActionId() {
        return sysActionId;
    }

    public void setSysActionId(Integer sysActionId) {
        this.sysActionId = sysActionId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(String keyValueType) {
        this.keyValueType = keyValueType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
