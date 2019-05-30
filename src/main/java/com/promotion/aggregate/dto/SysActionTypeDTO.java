package com.promotion.aggregate.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SysActionTypeDTO {

	@Id
	private Integer sysActionTypeId ;
	private String name;
	private String status;
	
	public Integer getSysActionTypeId() {
		return sysActionTypeId;
	}
	public void setSysActionTypeId(Integer sysActionTypeId) {
		this.sysActionTypeId = sysActionTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
