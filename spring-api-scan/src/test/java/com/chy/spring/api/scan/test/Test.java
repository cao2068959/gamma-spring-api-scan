package com.chy.spring.api.scan.test;


import com.chy.gamma.embed.GammaContainer;
import com.chy.procession.spring.api.scan.SpringApiScanProcession;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {

        Map<String, String> config = new HashMap<>();
        config.put("endpoint.topology.host","127.0.0.1:8080");
        config.put("ref","master");
        config.put("commitId","1234567");
        config.put("appName","1234567");
        config.put("scan.package","com.chy,com.chy2,com.example");

        GammaContainer gammaContainer =
                new GammaContainer("/Users/hengyuan/IdeaProjects/work/springmvcdemo/target/springmvcdemo-0.0.1-SNAPSHOT.jar", config);
        gammaContainer.start(new SpringApiScanProcession());
    }


}
