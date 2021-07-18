package com.chy.procession.spring.api.scan;

import com.chy.gamma.common.profile.Param;
import lombok.Data;

@Data
public class ApiScanProperty {

    @Param
    private String ref;
    @Param
    private String commitId;
    @Param(nullable = true)
    private String appName;
    @Param("endpoint.topology.host")
    private String host;



}
