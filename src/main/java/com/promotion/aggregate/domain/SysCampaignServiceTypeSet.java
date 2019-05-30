package com.promotion.aggregate.domain;

import javax.persistence.*;

@Entity
@Table(name = "sys_campaign_service_type_set")
public class SysCampaignServiceTypeSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sys_campaign__id")
    private Integer sysCampaignId;

    @Column(name = "service_type__id")
    private  String serviceTypeId;

    @Column(name = "gateway")
    private  String gateway;

    @Column(name = "status")
    private String status;

}
