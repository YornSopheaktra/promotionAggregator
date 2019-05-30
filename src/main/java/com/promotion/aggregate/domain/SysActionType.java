package com.promotion.aggregate.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_action_type")
public class SysActionType {

	@Id
	@Column(name="id")
	private Integer sysActionTypeId ;
	
	@Column(name="name")
	private String sysActionTypeName;
	
	@Column(name="status")
	private String status;
	
	@Column(name="created_by")
	private Integer createdBy;
	
	@Column(name="created_at")
	private Date createdAt;

	public Integer getSysActionTypeId() {
		return sysActionTypeId;
	}

	public void setSysActionTypeId(Integer sysActionTypeId) {
		this.sysActionTypeId = sysActionTypeId;
	}

	public String getSysActionTypeName() {
		return sysActionTypeName;
	}

	public void setSysActionTypeName(String sysActionTypeName) {
		this.sysActionTypeName = sysActionTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
