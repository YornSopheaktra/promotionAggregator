package com.promotion.aggregate.component;

import com.te.common.cache.CacheMessageCode;
import com.te.common.cache.CacheSysSmsMessage;
import com.te.common.cache.CacheSystemPreferences;
import com.te.common.servlet.ConfigServlet;
import com.tmc.frmk.core.annotation.Loggable;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CacheInitializer {

    @Autowired
    private ApplicationContext context;
    @Loggable
    private Logger log;

    @PostConstruct
    public void init() {
        if (ConfigServlet.ctx == null) {
            log.info("init servlet context");
            ConfigServlet.ctx = context;
        }
        log.info("Loading Cache SysMessage .....");
        CacheMessageCode.load(context);

        log.info("Loading Cache SysSmsMessage .....");
        CacheSysSmsMessage.load(context);

        log.info("Loading Cache SysPreference .....");
        CacheSystemPreferences.loadCacheSystemPreference(context);
    }

}