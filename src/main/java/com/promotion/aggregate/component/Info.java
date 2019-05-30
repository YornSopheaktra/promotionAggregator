package com.promotion.aggregate.component;

import com.te.common.cache.CacheSystemPreferences;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.util.HashMap;

@RestController
@Api(value = "Promotion Aggregation", description = "Promotion Aggregation API Documents")
@RequestMapping("/services")
public class Info {

    @Autowired
    private ServletContext context;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object info() {
        String[] path = context.getRealPath("/").split("/");
        String contextName = path[path.length - 1];
        String version = "N/A";
        if (contextName.contains("##")) {
            version = contextName.split("##")[1];
            contextName = contextName.split("##")[0];
        }
        HashMap<String, Object> response = new HashMap();
        response.put("version", version);
        response.put("contextName", contextName);
        response.put("dbConnection", true);
        response.put("preference", CacheSystemPreferences.sysPres.keySet().size());
        return response;
    }
}
