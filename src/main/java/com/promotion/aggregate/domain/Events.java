package com.promotion.aggregate.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "request_gateway")
    private String requestGateway;

    @Column(name = "service_type__id")
    private String serviceTypeId;

    @Column(name = "request_data")
    private String requestData;

    @Column(name = "response_data")
    private String responseData;

    @Column(name = "message_code")
    private String messageCode;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "order_execution_event_data")
    private  String orderExecutionEventData;

    @Column(name = "initiator_user_id")
    private  String initiatorUserId;

    @Column(name = "payer_user_id")
    private  String payerUserId;

    @Column(name = "payee_user_id")
    private  String payeeUserId;

    @Column(name = "amount")
    private  Double amount;

    @Column(name = "fee")
    private  Double fee;

    @Column(name = "currency")
    private  String currency;

    @Column(name = "mapping_rule_campaign__id")
    private  Integer mappingRuleCampaignId;

    @Column(name = "initiator_user_type")
    private  String initiatorUserType;

    @Column(name = "payer_user_type")
    private  String payerUserType;

    @Column(name = "payee_user_type")
    private  String payeeUserType;

    @Column(name = "status")
    private  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInitiatorUserType() {
        return initiatorUserType;
    }

    public void setInitiatorUserType(String initiatorUserType) {
        this.initiatorUserType = initiatorUserType;
    }

    public String getPayerUserType() {
        return payerUserType;
    }

    public void setPayerUserType(String payerUserType) {
        this.payerUserType = payerUserType;
    }

    public String getPayeeUserType() {
        return payeeUserType;
    }

    public void setPayeeUserType(String payeeUserType) {
        this.payeeUserType = payeeUserType;
    }

    public String getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(String initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public String getPayerUserId() {
        return payerUserId;
    }

    public void setPayerUserId(String payerUserId) {
        this.payerUserId = payerUserId;
    }

    public String getPayeeUserId() {
        return payeeUserId;
    }

    public void setPayeeUserId(String payeeUserId) {
        this.payeeUserId = payeeUserId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMappingRuleCampaignId() {
        return mappingRuleCampaignId;
    }

    public void setMappingRuleCampaignId(Integer mappingRuleCampaignId) {
        this.mappingRuleCampaignId = mappingRuleCampaignId;
    }

    public String getOrderExecutionEventData() {
        return orderExecutionEventData;
    }

    public void setOrderExecutionEventData(String orderExecutionEventData) {
        this.orderExecutionEventData = orderExecutionEventData;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRequestGateway() {
        return requestGateway;
    }

    public void setRequestGateway(String requestGateway) {
        this.requestGateway = requestGateway;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
