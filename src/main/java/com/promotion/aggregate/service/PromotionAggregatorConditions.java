package com.promotion.aggregate.service;

import com.promotion.aggregate.dto.ConditionResults;
import com.te.common.util.Debug;

public class PromotionAggregatorConditions  {

    public static boolean checkPromotionMechanicSetResult(PromotionShareData shareData){
        Debug.debugObject("shareData.conditionResultsList",shareData.conditionResultsList);
        // check all mechanism  of rule set mapping conditions
        boolean returnedResult = shareData.sysRmConditionDetails == null;  // if there are no condition detail for check so assume Return isGetPromo = Y
        for (ConditionResults conditionResults:shareData.conditionResultsList) {
            if (conditionResults.isReturnResult() == true) {
                if ("AND".equals(conditionResults.getAggregateType())) {
                    returnedResult = true;
                    //----------- not break because of need to check all condition *aggregate AND*
                } else if ("OR".equals(conditionResults.getAggregateType())) {
                    returnedResult =true;
                    //----------- break because of no need to check all condition *aggregate OR*
                    break;
                }
            } else {
                returnedResult = false;
                if ("AND".equals(conditionResults.getAggregateType())) break;
            }
        }

        System.out.print("checkPromotionMechanicSetResult return : "+returnedResult);
        return  returnedResult;
    }
}
