package com.promotion.aggregate.service;

import com.google.gson.Gson;
import com.promotion.aggregate.base.ModulesFilter;
import com.promotion.aggregate.configure.RabbitMQConfig;
import com.promotion.aggregate.dao.*;
import com.promotion.aggregate.domain.MappingRulesMechanics;
import com.promotion.aggregate.domain.SysActionType;
import com.promotion.aggregate.domain.SysMechanics;
import com.promotion.aggregate.domain.SysRuleMechanicSet;
import com.promotion.aggregate.dto.PromotionEffectiveMapped;
import com.promotion.aggregate.process.AbstractPromoAggregatorService;
import com.promotion.aggregate.request.SendRequest;
import com.te.common.util.Debug;
import com.tm.core.dto.OrderExecutionEventData;
import com.tm.core.service.RuleEngineService;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import com.tmc.frmk.core.service.RedisService;
import com.tmc.frmk.core.utils.BeanUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PromotionAggregateProcessor extends ModulesFilter<RequestDTO, ResponseDTO, PromotionShareData> {

    @Autowired
    private SysConditionsDao sysConditionsDao;

    @Autowired
    private SysRmConditionDetailsDao sysRmConditionDetailsDao;

    @Autowired
    private PromotionAggregateDao promotionAggregateDao;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SysMechanicsDao sysMechanicsDao;
    
    @Autowired
    private SysActionTypeDao sysActionTypeDao;
    
    private static final String callToRuleEngine = "direct_to_rule_engine";

    @Autowired
    private MappingRuleMechanicsDao mappingRuleMechanicsDao;

    @Autowired
    private SysRuleMechanicSetDao sysRuleMechanicSetDao;
    
    @Autowired
    private RedisService redisService;
    
	@Autowired
	private RuleEngineService ruleEngineService;

    @Override
    public ResponseDTO process(RequestDTO request, ResponseDTO response) {
        try {
            String isGetPromotion ="N";
            String returnedResult ="Y";
            boolean isPromotion=false;
            boolean isReturnPromotion=false;
            String promotionType = request.getData().get("promotionType").toString();
            PromotionShareData shareData = new PromotionShareData();
            shareData.conditionResultsList = new ArrayList<>();
            shareData.listSysConditions= new ArrayList<>();
            String maxRank = "";
            String reward = "";
            
            /**
             *  get all promotion campaign that effective that mapping with rule, set and mechanisms
             *  by promotion type, service type and request gateway,
             *  if return null it mean promotion is not check the condition of promotion aggregate
             */
            List<PromotionEffectiveMapped> promotionEffectiveMapped = promotionAggregateDao.getPromotionEffectiveMappedRule(request.getRequestGateWay(), promotionType, request.getServiceId());
            if (promotionEffectiveMapped==null) {
                response.setErrorCode("S00001");
                response.setStatus("T");
                //----------------- Reason of this isGetPromotion= Y because of This promotion not have to check condition so assume is allow get promotion
                response.getData().put("isGetPromotion", "Y");
                throw  new  Throwable();
            }

            Debug.debugObject("PromotionEffectiveMapped",promotionEffectiveMapped);
            int ind=0;
            boolean isMechanicResult=false;
            int[] listRuleMechanicSetId =null;

            // loop  for all promotion campaign mapped rule
            shareData.conditionDetailResult=new HashMap<>();
            for (PromotionEffectiveMapped promotionMapped: promotionEffectiveMapped) {
                shareData.promotionEffectiveMapped = promotionMapped;
                // get mechanic id of promotion
                listRuleMechanicSetId = Stream.of(promotionMapped.getListRuleMechanicSetId().split("\\s*,\\s*")).mapToInt(Integer::parseInt).toArray();
                if (listRuleMechanicSetId.length<=0) {
                    response.setErrorCode("S00001");
                    response.setStatus("T");
                    // Reason of this isGetPromotion= Y because of This promotion not have to check condition so assume is allow get promotion
                    response.getData().put("isGetPromotion", "Y");
                    throw  new  Throwable();
                }
                isMechanicResult=false;
                for ( Integer id:listRuleMechanicSetId) {

                    shareData.conditionResultsList= new ArrayList<>();
                    MappingRulesMechanics mappingRulesMechanics= mappingRuleMechanicsDao.getMappingRuleMechanicsByid(id);
                    SysMechanics sysMechanics = sysMechanicsDao.getSysMechanicsById(mappingRulesMechanics.getSysMechanicId());
                    SysRuleMechanicSet sysRuleMechanicSet = sysRuleMechanicSetDao.getSysRuleMechanicSetByMappingRuleId(id);

                    List<Integer> ruleMechanicSetListId = new ArrayList<>();
                    sysConditionsDao.getListSysConditions(sysRuleMechanicSet.getId()).forEach(data -> {ruleMechanicSetListId.add(data.getId()); shareData.listSysConditions.add(data);});
                    if(shareData.listSysConditions.isEmpty()){
                        response.setErrorCode("S00001");
                        response.setStatus("T");
                        // Reason of this isGetPromotion= Y because of This promotion not have to check condition so assume is allow get promotion
                        response.getData().put("isGetPromotion", "Y");
                        throw  new  Throwable();
                    }
                    //----------- check all modules bean processor of condition details
                    sysRmConditionDetailsDao.getSysRmConditionDetails(ruleMechanicSetListId).forEach(ConditionDetail -> {
                        shareData.sysRmConditionDetails= ConditionDetail;
                        AbstractPromoAggregatorService processor = (AbstractPromoAggregatorService) getProcessor(context, ConditionDetail.getModuleName());
                        if (processor != null) {
                            processor.process(request, shareData);
                        } else {
                            // No Bean module found!;
                        }
                    });
                    //----------- check mechanic set result of each condition
                    if((isReturnPromotion=PromotionAggregatorConditions.checkPromotionMechanicSetResult(shareData))==true ){
                        if(null==sysMechanics.getListSysCampaignCategoryId())
                            isMechanicResult = true;
                        else{
                            List<String> listCampCategoryId = Arrays.asList(sysMechanics.getListSysCampaignCategoryId().split("\\s*,\\s*"));
                            if (listCampCategoryId.contains(promotionMapped.getSysCampaignCategoryId().toString())){
                                isMechanicResult = true;
                            }
                        }
                        if("OR".equals(sysMechanics.getAggregatorType())) break; // aggregator OR so when 1 true is break the loop with value true if AND continue next mechanics
                    }else {
                        /**
                         * --- if isPromotion false and aggregator AND so  break the loop with value false
                         * --- if isPromotion false and aggregator OR continue next mechanic
                         */
                        if("AND".equals(sysMechanics.getAggregatorType())) break;
                    }
                }
                //check all rule of campaign mapping  1 loop = 1 rule
                if(ind<=0) {
                    isPromotion = isMechanicResult;  // set value to first rule for compare next rules
                    isGetPromotion = isMechanicResult==true? "Y": "N";
                }else{
                    isGetPromotion ="N";
                    if (isPromotion && "Y".equals(returnedResult) && isReturnPromotion) // each rule must be return true
                        isGetPromotion= "Y";
                }
                ind++;
            }
            System.out.println("=======serviceType "+request.getServiceId());
            SysActionType sysActionType = sysActionTypeDao.getSysActionType(promotionEffectiveMapped.get(0).getCampaignId());
            if(sysActionType.getSysActionTypeName().equals(callToRuleEngine) && "Y".equals(isGetPromotion)){

                System.out.println("================= connected to rule engine !");
                //JSONObject ruleEngineDataMap = new JSONObject(new Gson().toJson(request.getData())).optJSONObject("ruleEngineData");

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = new Gson().toJson(request.getData().get("ruleEngineData"), Map.class);
                JsonNode actualObj = mapper.readTree(jsonString);
                OrderExecutionEventData orderExecutionEventData = mapper.treeToValue(actualObj, OrderExecutionEventData.class);
                Debug.debugObject("orderExecutionEventData", orderExecutionEventData);

                String userAccountId = orderExecutionEventData.getInitiatorUserId().toString();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String startDateTime = format.format(promotionEffectiveMapped.get(0).getStartEffectiveDate());
                String endDateTime = format.format(promotionEffectiveMapped.get(0).getEndEffectiveDate());

                System.out.println("========= startDateTime "+startDateTime);
                HashMap<String, Object> requestData = new HashMap<>();
                requestData.put("stockAccountId", promotionEffectiveMapped.get(0).getStockAccountId());
                requestData.put("startDate", startDateTime); //"2019-01-01 15:24:49.178+07"
                requestData.put("endDate", endDateTime); //"2019-04-01 15:24:49.178+07"

                try {
                     JSONObject getBagInfo = (JSONObject) callToCoreReward(userAccountId, requestData);
                     maxRank =  getBagInfo.get("maxRank").toString();
                     reward =   getBagInfo.get("rewardTime").toString();

                     System.out.println("===== maxRank: "+maxRank+ "=====reward: "+reward);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                orderExecutionEventData.setCommandName(promotionEffectiveMapped.get(0).getPromotionType());
                orderExecutionEventData.setProductRef1(isGetPromotion);
                orderExecutionEventData.setProductRef3(maxRank);
                orderExecutionEventData.setProductRef4(reward);
                orderExecutionEventData.setRefOrderId(promotionEffectiveMapped.get(0).getStockAccountId());
                Debug.debugObject("orderExecutionEventData before RE", orderExecutionEventData);
                ruleEngineService.doAction(RabbitMQConfig.CONFIG_DATA, orderExecutionEventData);
            }
            HashMap<String,Object> data = new HashMap<>();
            data.put("isGetPromotion",isGetPromotion);
            response.setData(data);
            response.setStatus("T");
            response.setErrorCode("S00001");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("F");
            response.setErrorCode("E00001");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return response;
    }
    
    private JSONObject callToCoreReward(String userAccountId, HashMap<String, Object> requestData){
    	String url = BeanUtils.getBean(RedisService.class).get("count_lucky_bag_url");
		System.out.println("fullUrl: " + url);
		Map<String,Object> request = new HashMap<>();
		request.put("accountId", userAccountId);
		request.put("data", requestData);

        Debug.debugObject("requestData", request);	
		try {
			
			SendRequest sendRequest = new SendRequest(url,request);
			JSONObject res = sendRequest.SendRequest();
			System.out.println("=================== res: "+ res);
			JSONObject response = res.getJSONObject("data");
			return	response;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
    }
}
