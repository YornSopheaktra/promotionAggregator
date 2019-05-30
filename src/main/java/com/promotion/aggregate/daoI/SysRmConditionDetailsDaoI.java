package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysRmConditionDetails;

import java.util.List;

public interface SysRmConditionDetailsDaoI {

    List<SysRmConditionDetails> getSysRmConditionDetails(List<Integer> mappingId);

}
