package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysRules;

import java.util.List;

public interface SysRuleDaoI {
    SysRules getRuleByid(Integer id);
    List<SysRules> getListRule();
}
