package com.promotion.aggregate.process;

import com.promotion.aggregate.base.BaseProcess;
import com.promotion.aggregate.domain.SysConditions;
import com.promotion.aggregate.dto.ConditionResults;
import com.promotion.aggregate.service.PromotionShareData;
import com.te.common.util.Debug;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractPromoAggregatorService implements BaseProcess<RequestDTO, ResponseDTO, PromotionShareData> {

    protected abstract Object checkProcess(RequestDTO req, PromotionShareData shareData);

    @Override
    public ResponseDTO process(RequestDTO req, PromotionShareData shareData) {
        ResponseDTO res= new ResponseDTO();
        Object isReturnResult = checkProcess(req,shareData);
        Debug.debugObject("isReturnResult",isReturnResult);
        this.checkConditionResult(shareData, isReturnResult,res);
        return res;
    }

    private  void  checkConditionResult(PromotionShareData shareData, Object isReturnResult,ResponseDTO responseDTO){
        boolean returnValue=false;
        try{
            if(isReturnResult!=null){
                if(shareData.sysRmConditionDetails.getOperator().equalsIgnoreCase("in_list")){

                    List<String> resultList = Arrays.asList(shareData.sysRmConditionDetails.getKeyResult().split("\\s*,\\s*"));
                    if (resultList.contains(isReturnResult.toString())){
                        returnValue = true;
                    }
                }else if(shareData.sysRmConditionDetails.getOperator().equals("=")){
                    if (shareData.sysRmConditionDetails.getKeyValueType().equalsIgnoreCase("string")){
                        if( String.valueOf(isReturnResult).equals(shareData.sysRmConditionDetails.getKeyResult())) returnValue = true;

                    }else if(shareData.sysRmConditionDetails.getKeyValueType().equalsIgnoreCase("number")){
                        if( Integer.valueOf(isReturnResult.toString())== Integer.valueOf(shareData.sysRmConditionDetails.getKeyResult())) returnValue = true;
                    }
                }else if(shareData.sysRmConditionDetails.getOperator().equals("not_check")){
                    returnValue = true;
                }
            }
            if (shareData.conditionResultsList.isEmpty()){
                addNewCondition(shareData,returnValue);
            }else {
                int ind =0;
                boolean isExisting = false;
                boolean isCondition=false;
                for (ConditionResults conR:shareData.conditionResultsList) {

                    // check if the same condition id  check the result need to set in 1 record else add new record
                    if (conR.getSysConditionId()== shareData.sysRmConditionDetails.getSysConditionId()){

                        if(shareData.sysRmConditionDetails.getType().equalsIgnoreCase(conR.getType())){ // it mean aggregate OR
                            if (returnValue==true) isCondition=true;
                            else{
                                isCondition= conR.isReturnResult();
                            }
                        }else{ // Aggregate AND
                            if (returnValue==true && conR.isReturnResult()==true)
                                isCondition=true;
                        }
                        shareData.conditionResultsList.get(ind).setReturnResult(isCondition);
                        isExisting = true;
                        break;
                    }
                    ind ++;
                }
                if(isExisting==false){
                    addNewCondition(shareData,returnValue);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void  addNewCondition(PromotionShareData shareData, boolean returnValue){
        String aggregateType="AND";
        for ( SysConditions cond: shareData.listSysConditions) {
            if (cond.getId() == shareData.sysRmConditionDetails.getSysConditionId()){
                aggregateType= cond.getAggregatorType();
                break;
            }
        }
        ConditionResults conditionResults = new ConditionResults();
        conditionResults.setId(shareData.sysRmConditionDetails.getId());
        conditionResults.setSysConditionId(shareData.sysRmConditionDetails.getSysConditionId());
        conditionResults.setKeyValue(shareData.sysRmConditionDetails.getKeyValue());
        conditionResults.setKeyResult(shareData.sysRmConditionDetails.getKeyResult());
        conditionResults.setType(shareData.sysRmConditionDetails.getType());
        conditionResults.setReturnResult(returnValue);
        conditionResults.setAggregateType(aggregateType); // add fix test
        shareData.conditionResultsList.add(conditionResults);
    }
}
