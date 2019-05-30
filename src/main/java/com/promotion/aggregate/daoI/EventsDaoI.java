package com.promotion.aggregate.daoI;

public interface EventsDaoI {

    public Integer getCountSummaryEvent( Integer ruleCampaignMappingId, String serviceTypeId, String gateWays, String additionFields);
}
