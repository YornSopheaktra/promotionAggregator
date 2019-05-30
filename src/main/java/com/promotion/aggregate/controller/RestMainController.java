package com.promotion.aggregate.controller;

import com.promotion.aggregate.component.CacheInitializer;
import com.promotion.aggregate.service.PromotionAggregateService;
import com.promotion.aggregate.service.PromotionService;
import com.tmc.frmk.core.annotation.Loggable;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Promotion Aggregation", description = "Promotion Aggregation API Documents")
@RequestMapping("/promotion_aggregator")
public class RestMainController {
    @Autowired
    private PromotionAggregateService promotionAggregateService;

    @Autowired
    private PromotionService PromotionService;

    @Loggable
    private Logger log;

    @Autowired
    private CacheInitializer cacheInitializer;

    @PostMapping("/promotion")
    public ResponseDTO promotionAggregator(@RequestBody RequestDTO requestDTO) {
        return PromotionService.run(requestDTO);
    }

    @PostMapping("/check_promotion")
    public ResponseDTO checkPromotion(@RequestBody RequestDTO requestDTO) {
        return promotionAggregateService.run(requestDTO);
    }

    @GetMapping(value = "/clear", produces = MediaType.TEXT_HTML_VALUE)
    public String clearCache() {
        log.info("do clear cache");
        cacheInitializer.init();
        return "done";
    }
}
