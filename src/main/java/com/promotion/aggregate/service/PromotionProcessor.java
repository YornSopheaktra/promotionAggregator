package com.promotion.aggregate.service;

import com.promotion.aggregate.base.ModulesFilter;
import com.promotion.aggregate.dao.*;
import com.promotion.aggregate.domain.*;
import com.promotion.aggregate.dto.PromotionEffectiveMapped;
import com.promotion.aggregate.modules.CheckCustomerInFocusGroup;
import com.promotion.aggregate.process.AbstractPromoAggregatorService;
import com.te.common.util.Debug;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PromotionProcessor extends ModulesFilter<RequestDTO, ResponseDTO, PromotionShareData> {


    @Autowired
    private SysCampaignDao sysCampaignDao;

    @Autowired
    private SysCampaignServiceTypeSetDao sysCampaignServiceTypeSetDao;

    @Autowired
    private PromotionAggregateDao promotionAggregateDao;

    @Autowired
    private MappingRuleMechanicsDao mappingRuleMechanicsDao;

    @Autowired
    private MappingRulesCampaignDao mappingRulesCampaignDao;

    @Autowired
    private SysMechanicsDao sysMechanicsDao;

    @Autowired
    private SysRuleMechanicSetDao sysRuleMechanicSetDao;

    @Autowired
    private SysConditionsDao sysConditionsDao;

    @Autowired
    private SysRmConditionDetailsDao sysRmConditionDetailsDao;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private OrderExecutionEventDataProcessor orderExecutionEventDataProcessor;

    @Override
    public ResponseDTO process(RequestDTO request, ResponseDTO response) {
        try {
            PromotionShareData shareData = new PromotionShareData();
            shareData.promotionRequestDTO = request;
            shareData.conditionResultsList = new ArrayList<>();
            shareData.listSysConditions = new ArrayList<>();
            int[] listRuleMechanicSetId = null;
            int ind = 0;
            boolean isMechanicResult = false;
            boolean isPromotion = false;
            boolean isReturnPromotion = false;
            String isGetPromotion = "N";
            String direction="";
            MappingRulesMechanics mappingRulesMechanics=null;
            SysMechanics sysMechanics=null;
            SysRuleMechanicSet sysRuleMechanicSet=null;
            shareData.setUserAccountId(request.getAccountId());
            HashMap<String,Object> dataPromotionResponse = new HashMap<>();
            HashMap<String, Object> resPromotion = null;
            List<SysCampaign> sysCampaigns = sysCampaignDao.getAllActiveSysCampaign();

            if (sysCampaigns.isEmpty()){
                response.setStatus("F");
                response.setErrorCode("E00001","Don't have any promotions active");
                return response;
            }

            //--------- Check All Sys Campaign that effectively
            for (SysCampaign sysCampaign : sysCampaigns) {
                isGetPromotion = "N";
                shareData.conditionDetailResult=new HashMap<>();
                Debug.debugObject("sysCampaign", sysCampaign);
                //-------- get promotion mapped service type id
                SysCampaignServiceTypeSet sysCampaignServiceTypeSet = sysCampaignServiceTypeSetDao
                        .getCampaignMappedServiceType(sysCampaign.getId(),
                                shareData.promotionRequestDTO.getServiceId(),
                                shareData.promotionRequestDTO.getRequestGateWay());
                if (sysCampaignServiceTypeSet == null) {
                    // check next promotion campaign
                    continue;
                }
                // Get Sys Campaign mapping rule, Mechanics

                List<PromotionEffectiveMapped> promotionEffectiveMapped = promotionAggregateDao.getPromotionEffectiveMappedRule(request.getRequestGateWay(), sysCampaign.getPromotionType(), request.getServiceId());
                if(promotionEffectiveMapped==null) {
                    // static check module user in forcus group or not
                    HashMap<String, Object> proResData= new HashMap<>();
                    proResData.put("isGetPromotion", isGetPromotion);
                    CheckCustomerInFocusGroup process = new CheckCustomerInFocusGroup();
                    Object isInFocusGroup = process.process(request,shareData);
                    proResData.put("isShowPromoMs", isInFocusGroup.toString());
                    dataPromotionResponse.put(sysCampaign.getPromotionType(), proResData);
                    continue;
                }
                // loop  for all promotion campaign mapped rule
                for (PromotionEffectiveMapped promotionMapped : promotionEffectiveMapped) {
                    shareData.promotionEffectiveMapped = promotionMapped;
                    // get mechanic id of promotion
                    listRuleMechanicSetId = Stream.of(promotionMapped.getListRuleMechanicSetId().split("\\s*,\\s*")).mapToInt(Integer::parseInt).toArray();
                    if (listRuleMechanicSetId.length <= 0) {
                        // check next campaign rule mapped
                        continue;
                    }
                    isMechanicResult = false;
                    for (Integer id : listRuleMechanicSetId) {
                        shareData.conditionResultsList = new ArrayList<>();
                        mappingRulesMechanics = mappingRuleMechanicsDao.getMappingRuleMechanicsByid(id);
                        sysRuleMechanicSet =sysRuleMechanicSetDao.getSysRuleMechanicSetByMappingRuleId(id);
                        sysMechanics =  sysMechanicsDao.getSysMechanicsById(mappingRulesMechanics.getSysMechanicId());

                        List<Integer> ruleMechanicSetListId = new ArrayList<>();
                        sysConditionsDao.getListSysConditions(sysRuleMechanicSet.getId()).forEach(data -> {
                            ruleMechanicSetListId.add(data.getId());
                            shareData.listSysConditions.add(data);
                        });
                        if (shareData.listSysConditions.isEmpty()) {
                           Debug.debugStr("This promotion" + sysCampaign.getName()+" doesn't have condition", "ruleMechanicSetListId:"+ ruleMechanicSetListId);
                        }
                        //----------- check all modules bean processor of condition details
                        sysRmConditionDetailsDao.getSysRmConditionDetails(ruleMechanicSetListId).forEach(ConditionDetail -> {
                            shareData.sysRmConditionDetails = ConditionDetail;
                            AbstractPromoAggregatorService processor = (AbstractPromoAggregatorService) getProcessor(context, ConditionDetail.getModuleName());
                            if (processor != null) {
                                processor.process(request, shareData);
                            } else {
                                System.out.print("Cannot find bean module of class:"+  ConditionDetail.getModuleName());
                            }
                        });
                        //----------- check mechanic set result of each condition
                        if ((isReturnPromotion = PromotionAggregatorConditions.checkPromotionMechanicSetResult(shareData)) == true) {
                            if (null == sysMechanics.getListSysCampaignCategoryId())
                                isMechanicResult = true;
                            else {
                                List<String> listCampCategoryId = Arrays.asList(sysMechanics.getListSysCampaignCategoryId().split("\\s*,\\s*"));
                                if (listCampCategoryId.contains(promotionMapped.getSysCampaignCategoryId().toString())) {
                                    isMechanicResult = true;
                                }
                            }
                            if ("OR".equals(sysMechanics.getAggregatorType()))
                                break;
                        } else {

                            if ("AND".equals(sysMechanics.getAggregatorType())) break;
                        }
                    }
                    //check all rule of campaign mapping  1 loop = 1 rule
                    if (ind <= 0) {
                        isPromotion = isMechanicResult;  // set value to first rule for compare next rules
                        isGetPromotion = isMechanicResult == true ? "Y" : "N";
                    } else {
                        isGetPromotion = "N";
                        if (isPromotion && isReturnPromotion) // each rule must be return true
                            isGetPromotion = "Y";
                        else
                            break;
                    }
                    ind++;
                }
                resPromotion= new HashMap<>();
                resPromotion.put("isGetPromotion", isGetPromotion);
                direction ="";
                shareData.setIsGetPromotion(isGetPromotion);
                if("Y".equals(isGetPromotion)) {
                    shareData.conditionDetailResult.put("promotion_type", sysCampaign.getPromotionType());
                    shareData.conditionDetailResult.put("is_get_promotion", isGetPromotion);
                    shareData.conditionDetailResult.put("stock_account_id", sysCampaign.getStockAccountId());
                    shareData.conditionDetailResult.put("service_type", request.getServiceId());
                    shareData.conditionDetailResult.put("device__id", request.getDeviceId());
                    shareData.conditionDetailResult.put("user_account_id", request.getAccountId());
                    orderExecutionEventDataProcessor.checkDestinationDirection(shareData, sysCampaign);
                    direction= shareData.actionMapping.getActionTypeName();
                }


                String isShowPromoMs = (null == shareData.conditionDetailResult? "in_focus_list": shareData.conditionDetailResult.containsKey("in_focus_list")?shareData.conditionDetailResult.get("in_focus_list").toString():"N");
                resPromotion.put("promotionStockId", sysCampaign.getStockAccountId());
                resPromotion.put("userAccountId",shareData.getUserAccountId());
                resPromotion.put("direction",direction);
                resPromotion.put("isShowPromoMs",isShowPromoMs);
                dataPromotionResponse.put(sysCampaign.getPromotionType(), resPromotion);
            }

            response.setData(dataPromotionResponse);
            response.setStatus("T");
            response.setErrorCode("S00001");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("F");
            response.setErrorCode("E00001", "Somethings went wrong, Please check with developer");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return response;
    }


}
