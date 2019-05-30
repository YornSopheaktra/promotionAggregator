package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysActionData;

import java.util.List;

public interface SysActionDataDaoI {
    public List<SysActionData> getListActionDataByActionId(Integer actionId);
}
