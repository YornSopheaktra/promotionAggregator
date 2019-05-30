package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.MappingRulesMechanics;

import java.util.List;

public interface MappingRuleMechanicsDaoI {

    MappingRulesMechanics getMappingRuleMechanicsByid(Integer id);
    List<MappingRulesMechanics> getListMappingRuleMechanics();
}
