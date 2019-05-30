package com.promotion.aggregate.domain;

import javax.persistence.*;

@Entity
@Table(name = "action_data_history")
public class ActionDataHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "action_history_id")
    private Integer actionHistoryId;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "key_value_type")
    private String keyValueType;

    @Column(name = "key_value")
    private String keyValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionHistoryId() {
        return actionHistoryId;
    }

    public void setActionHistoryId(Integer actionHistoryId) {
        this.actionHistoryId = actionHistoryId;
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
