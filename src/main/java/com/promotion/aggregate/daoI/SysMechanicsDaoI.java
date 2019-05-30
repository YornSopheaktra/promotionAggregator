package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysMechanics;

import java.util.List;

public interface SysMechanicsDaoI {
    SysMechanics getSysMechanicsById(Integer id);
    List<SysMechanics> getListSysMechanics();
}
