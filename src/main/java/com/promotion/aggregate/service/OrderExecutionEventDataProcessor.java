package com.promotion.aggregate.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.promotion.aggregate.configure.RabbitMQConfig;
import com.promotion.aggregate.dao.EventsDao;
import com.promotion.aggregate.dao.RuleCampaignMappingLimitDao;
import com.promotion.aggregate.dao.RuleCampaignMappingLimitFilterDao;
import com.promotion.aggregate.dao.SysActionDataDao;
import com.promotion.aggregate.dao.SysActionTypeDao;
import com.promotion.aggregate.domain.ActionDataHistory;
import com.promotion.aggregate.domain.ActionHistory;
import com.promotion.aggregate.domain.Events;
import com.promotion.aggregate.domain.RuleCampaignMappingLimit;
import com.promotion.aggregate.domain.RuleCampaignMappingLimitFilter;
import com.promotion.aggregate.domain.SysActionData;
import com.promotion.aggregate.domain.SysCampaign;
import com.promotion.aggregate.dto.ActionMapping;
import com.promotion.aggregate.util.ActionType;
import com.te.common.util.Debug;
import com.tm.core.dto.OrderExecutionEventData;
import com.tm.core.service.RuleEngineService;
import com.tmc.frmk.core.repo.EntityRepo;

@Service
public class OrderExecutionEventDataProcessor {

    @Autowired
    private SysActionTypeDao sysActionTypeDao;

    @Autowired
    private SysActionDataDao sysActionDataDao;

    @Autowired
    private RuleCampaignMappingLimitDao ruleCampaignMappingLimitDao;

    @Autowired
    RuleCampaignMappingLimitFilterDao ruleCampaignMappingLimitFilterDao;

    @Autowired
    private EntityRepo repo;

    @Autowired
    private EventsDao eventsDao;

	@Autowired
	private RuleEngineService ruleEngineService;

    public static   OrderExecutionEventData merge(OrderExecutionEventData obj, OrderExecutionEventData  obj1 ) throws NoSuchFieldException, IllegalAccessException {
        OrderExecutionEventData copy = new OrderExecutionEventData();
        BeanUtils.copyProperties(obj, copy);
        Field[] fields = copy.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object copyValue = field.get(copy);

            Field tempFile = obj1.getClass().getDeclaredField(name);
            tempFile.setAccessible(true);
            Object tempValue = tempFile.get(obj1);

            if (Number.class.isAssignableFrom(copyValue.getClass())) {//field number
                if ((copyValue == null || copyValue.equals(0) || copyValue.equals(new BigDecimal("0.0"))) && !copyValue.equals(tempValue)) {
                    field.set(copy, tempValue);
                }else if((tempValue != null && !tempValue.equals(0) && !tempValue.equals(new BigDecimal("0.0")))){
                    field.set(copy, tempValue);
                }
            } else {
                if ((copyValue == null || copyValue.toString().isEmpty()) && !copyValue.equals(tempValue)) {
                    field.set(copy, tempValue);
                }else if((tempValue != null && !tempValue.toString().isEmpty()) ){
                    field.set(copy, tempValue);
                }
            }

        }
        return copy;
    }

    public static OrderExecutionEventData RenderOrderExecutionEventData(OrderExecutionEventData original, JSONObject orderExecutionEventDataJS) {
        OrderExecutionEventData orderExecutionEventData = new OrderExecutionEventData();
        try{

            BeanUtils.copyProperties(original, orderExecutionEventData);
            Field[] fields = orderExecutionEventData.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if(orderExecutionEventDataJS.has(field.getName())){
                    if(Number.class.isAssignableFrom(field.getType()))
                        field.set(orderExecutionEventData, passValue(field.getType(),orderExecutionEventDataJS.optString(field.getName(),"0")));
                    else
                        field.set(orderExecutionEventData, orderExecutionEventDataJS.get(field.getName()));
                }

            }
        }catch ( Exception e){
            e.printStackTrace();
        }

        return orderExecutionEventData;
    }

    private OrderExecutionEventData getOrderExecutionEventData(PromotionShareData shareData, Integer actionId, OrderExecutionEventData orderExecutionEventDataFromClient) throws NoSuchFieldException, IllegalAccessException {

        List<SysActionData> listActionData= sysActionDataDao.getListActionDataByActionId(actionId);
        List<SysActionData> listActionDataHistory = new ArrayList<>();
        JSONObject orderExecutionEventDataJS =new JSONObject();
        OrderExecutionEventData orderExecutionEventDataFromDB =new OrderExecutionEventData();
        if(!listActionData.isEmpty() ){
            String actionValue;
            String actionKey;
            for ( SysActionData sysActionData: listActionData) {
                actionValue=sysActionData.getKeyValue();
                if(sysActionData.getKeyValue().contains("#")){
                    actionKey = sysActionData.getKeyValue().toString().replaceAll("#","");
                    actionValue = (null == shareData.conditionDetailResult? "": shareData.conditionDetailResult.containsKey(actionKey)?shareData.conditionDetailResult.get(actionKey).toString():"");
                    if("".equals(actionValue))
                        actionValue = shareData.promotionRequestDTO.getData().containsKey(actionKey)? shareData.promotionRequestDTO.getData().get(actionKey).toString(): "";
                }
                sysActionData.setKeyValue(actionValue);
                listActionDataHistory.add(sysActionData);
                orderExecutionEventDataJS.put(sysActionData.getKeyName(),sysActionData.getKeyValue());
            }

        }
        shareData.sysActionDataHistory= listActionDataHistory;
        if(null==orderExecutionEventDataFromClient && listActionData.isEmpty()) return  null;

        if(orderExecutionEventDataJS!=null){
            orderExecutionEventDataFromDB= OrderExecutionEventDataProcessor.RenderOrderExecutionEventData(orderExecutionEventDataFromDB,orderExecutionEventDataJS);
        }
      /*  try{
            ObjectMapper mapper = new ObjectMapper();
            //String jsonString = new Gson().toJson(orderExecutionEventDataJson, Map.class);

            Map<String, Object> map = new HashMap<String, Object>();
            map = mapper.readValue(String.valueOf(orderExecutionEventData), new TypeReference<Map<String, String>>(){});

            String jsonString = new Gson().toJson(map, Map.class);
            JsonNode actualObj = mapper.readTree(jsonString);
            orderExecutionEventDataFromClient = mapper.treeToValue(actualObj, OrderExecutionEventData.class);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        if(orderExecutionEventDataFromClient==null) orderExecutionEventDataFromClient= new OrderExecutionEventData();

        OrderExecutionEventData mergedOrderExecutionEventData = OrderExecutionEventDataProcessor.merge(orderExecutionEventDataFromClient, orderExecutionEventDataFromDB);
        return  mergedOrderExecutionEventData;
    }

    public void checkDestinationDirection(PromotionShareData shareData, SysCampaign sysCampaign) {
        try {
            ActionMapping actionMapping = sysActionTypeDao.getActionMapping(sysCampaign.getId());
            shareData.actionMapping = actionMapping;
            if (actionMapping.getActionTypeName().equals(ActionType.callToRuleEngine)) {
                System.out.println("======Prepare Data for invoke to Rule Engine !");
                String ruleEngineDataMap=null;
                OrderExecutionEventData orderExecutionEventDataFromClient=null;
                try{

                    ObjectMapper mapper = new ObjectMapper();
                    ruleEngineDataMap = new Gson().toJson(shareData.promotionRequestDTO.getData().get("ruleEngineData"), Map.class);
                    JsonNode actualObj = mapper.readTree(ruleEngineDataMap);
                    orderExecutionEventDataFromClient = mapper.treeToValue(actualObj, OrderExecutionEventData.class);

                }catch (Exception e){
                }
                OrderExecutionEventData orderExecutionEventData= getOrderExecutionEventData(shareData,actionMapping.getId(),orderExecutionEventDataFromClient);
                if(orderExecutionEventData==null){
                    System.out.print("=============================================== orderExecutionEventData is null, Please check the configure" );
                }
                Debug.debugObject("orderExecutionEventData For Rule Engine: ", orderExecutionEventData);
                this.setEvent(shareData,orderExecutionEventData);
                //------------- this key do not remove!!! it's for reference to rule engine




                //---------- put these fields to hash map for specify for campaign limit filter
                shareData.conditionDetailResult.put("initiator_user_id", orderExecutionEventData.getInitiatorUserId());
                shareData.conditionDetailResult.put("payer_user_id",orderExecutionEventData.getPayerUserId());
                shareData.conditionDetailResult.put("payee_user_id", orderExecutionEventData.getPayeeUserId());
                shareData.conditionDetailResult.put("mapping_rule_campaign__id", shareData.promotionEffectiveMapped.getMappingRuleCampaignId());

                if(!this.checkLimitation(shareData,sysCampaign)) {
                    shareData.events.setMessageCode("E00001");
                    shareData.events.setMessage("promotion reached txn limited");
                    shareData.events.setStatus("TR");
                    repo.save(shareData.events);
                    System.out.println("This Account: "+ shareData.getUserAccountId() + " has reached max limit txn with this promotion : " + sysCampaign.getName());
                    return;
                }
                repo.save(shareData.events);
                orderExecutionEventData.setOrderId(shareData.events.getId().toString());
                ruleEngineService.doAction(RabbitMQConfig.CONFIG_DATA, orderExecutionEventData);
            } else if (actionMapping.getActionTypeName().equals(ActionType.directBack)) {
                this.setEvent(shareData,null);
                repo.save(shareData.events);
            } else if (actionMapping.getActionTypeName().equals(ActionType.callTofixedCashback)) {
                //--------------------- not yet implement
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            saveHistory(shareData);
        }

    }

    public void  saveHistory(PromotionShareData shareData){

        try{
            ActionHistory actionHistory = new ActionHistory();
            actionHistory.setMappingRulesCampaignId(shareData.actionMapping.getMappingRuleCampaignId());
            actionHistory.setSysEventId(shareData.events.getId());
            actionHistory.setSysActionTypeId(shareData.actionMapping.getActionTypeId());
            actionHistory.setSysActionId(shareData.actionMapping.getId());
            actionHistory.setStatus(shareData.events.getStatus());
            repo.save(actionHistory);

            ActionDataHistory actionDataHistory = new ActionDataHistory();
            for (SysActionData actionData: shareData.sysActionDataHistory) {
                actionDataHistory= new ActionDataHistory();
                actionDataHistory.setActionHistoryId(actionHistory.getId());
                actionDataHistory.setKeyName(actionData.getKeyName());
                actionDataHistory.setKeyValue(actionData.getKeyValue());
                actionDataHistory.setKeyValueType(actionData.getKeyValueType());
                repo.save(actionDataHistory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setEvent(PromotionShareData shareData,OrderExecutionEventData orderExecutionEventData) {

        HashMap<String, Object> response = new HashMap<>();
        response.put("isGetPromotion", shareData.getIsGetPromotion());
        response.put("promotionType",shareData.promotionEffectiveMapped.getPromotionType());
        response.put("promotionStockId",shareData.promotionEffectiveMapped.getStockAccountId());
        String requestData = new Gson().toJson(shareData.promotionRequestDTO);
        Events events = new Events();

        events.setDeviceId(shareData.promotionRequestDTO.getDeviceId());
        events.setServiceTypeId(shareData.promotionRequestDTO.getServiceId());
        events.setRequestGateway(shareData.promotionRequestDTO.getRequestGateWay());
        events.setMappingRuleCampaignId(shareData.promotionEffectiveMapped.getMappingRuleCampaignId());
        events.setRequestData(requestData);
        events.setCreatedAt(new Date());
        events.setName(shareData.actionMapping.getActionTypeName());
        events.setUserId(shareData.getUserAccountId());
        events.setResponseData(new Gson().toJson(response));
        events.setUserType("performer");
        if (orderExecutionEventData!=null){
            String orderExecutionEventDataStr = new Gson().toJson(orderExecutionEventData);
            events.setOrderExecutionEventData(orderExecutionEventDataStr);
            events.setUserId(orderExecutionEventData.getInitiatorUserId().toString());
            events.setUserType(orderExecutionEventData.getInitiatorUserType());
            events.setPayerUserId(orderExecutionEventData.getPayerUserId().toString());
            events.setPayerUserType(orderExecutionEventData.getPayerUserType());
            events.setPayeeUserId(orderExecutionEventData.getPayeeUserId().toString());
            events.setPayeeUserType(orderExecutionEventData.getPayeeUserType());
            events.setInitiatorUserId(shareData.getUserAccountId().toString());
            events.setInitiatorUserType(orderExecutionEventData.getInitiatorUserType());
        }

        events.setMessageCode("S00001");
        events.setMessage("Success");
        events.setStatus("TS");
        shareData.events = events;
    }

    private boolean checkLimitation(PromotionShareData shareData, SysCampaign sysCampaign) {
        RuleCampaignMappingLimit ruleCampaignMappingLimit = ruleCampaignMappingLimitDao.getMappingLimitByMappingCampaignId(shareData.promotionEffectiveMapped.getMappingRuleCampaignId());
        if (ruleCampaignMappingLimit== null) return  true; //------------ this promotion dont have any limit
        List<RuleCampaignMappingLimitFilter> ruleCampaignMappingLimitFilterList = ruleCampaignMappingLimitFilterDao.getLimitFilterByLimitId(ruleCampaignMappingLimit.getId());
        if (ruleCampaignMappingLimitFilterList.isEmpty()) return  true; //------------ this promotion dont have any limit

        String additionalFields="";
        String key;
        String value;
        for ( RuleCampaignMappingLimitFilter limitFilter: ruleCampaignMappingLimitFilterList) {

            value=limitFilter.getKeyValue();
            if(limitFilter.getKeyValue().contains("#")){
                key = limitFilter.getKeyValue().toString().replaceAll("#","");
                value = (null == shareData.conditionDetailResult? "": shareData.conditionDetailResult.containsKey(key)?shareData.conditionDetailResult.get(key).toString():"");
                if("".equals(value))
                    value = shareData.promotionRequestDTO.getData().containsKey(key)? shareData.promotionRequestDTO.getData().get(key).toString(): "";
            }
            limitFilter.setKeyValue(value);
            String charector = "text".equals(limitFilter.getKeyValueType())? "'" : "";
            additionalFields += " "+ limitFilter.getAggregatorType() + " "+ limitFilter.getKeyName() + "="+ charector +limitFilter.getKeyValue() + charector;
        }

        Integer totalLimitCount = eventsDao.getCountSummaryEvent(shareData.promotionEffectiveMapped.getMappingRuleCampaignId(),shareData.promotionRequestDTO.getServiceId(),shareData.promotionRequestDTO.getRequestGateWay(),additionalFields);

        if (ruleCampaignMappingLimit.getLimitType().equals("count")){
            if(totalLimitCount < Integer.valueOf(ruleCampaignMappingLimit.getLimitValue())) {
                return true;
            }
        }

        return  false;
    }

    private static Number passValue(Class clazz,String value){
        if(value.trim().length()<=0) return 0;
        if(clazz.equals(Integer.class))
            return Integer.valueOf(value);
        else if(clazz.equals(Float.class))
            return Float.valueOf(value);
        else if(clazz.equals(Long.class))
            return Long.valueOf(value);
        else if(clazz.equals(Double.class))
            return Double.valueOf(value);
        else if(clazz.equals(BigDecimal.class))
            return new BigDecimal(value);
        else
            return 0;
    }

}
