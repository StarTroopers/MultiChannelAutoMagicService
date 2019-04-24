package com.fanniemae.starapp.controllers.appinfo;


import com.fanniemae.starapp.domains.AppInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/app-info")
@ApiIgnore
public class AppInfoController {

    private static final Logger LOGGER = LogManager.getLogger(AppInfoController.class);


    @GetMapping("/status")
    public AppInfo getAppInfoStatus(){

        LOGGER.info("Getting the status of App!");
        AppInfo info = new AppInfo();
        info.setName("Star Trooper");
        info.setStatus("Running!");
        info.setType("Demo");

        return info;
    }

}
