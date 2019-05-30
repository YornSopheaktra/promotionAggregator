package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysConditions;

import java.util.List;

public interface SysConditionsDaoI {
    List<SysConditions> getListSysConditions(Integer sysRuleMechanicSetId);
}
