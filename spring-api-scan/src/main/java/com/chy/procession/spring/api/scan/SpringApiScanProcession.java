package com.chy.procession.spring.api.scan;


import com.chy.gamma.common.processor.Processor;
import com.chy.procession.spring.api.scan.handle.SpringControllerHandleEngine;
import com.chy.processor.api.scan.common.net.ApiSignatureFrom;
import com.chy.processor.api.scan.common.net.EndpointTopologyClient;
import com.chy.processor.api.scan.common.net.ProjectApiSignatureFrom;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

public class SpringApiScanProcession implements Processor<ApiScanProperty> {

    ApiScanProperty property;

    private SpringControllerHandleEngine springControllerHandleEngine = new SpringControllerHandleEngine();

    private EndpointTopologyClient endpointTopologyClient;

    public SpringApiScanProcession() {

    }


    @Override
    public void setProperty(ApiScanProperty property) {
        this.property = property;
        endpointTopologyClient = new EndpointTopologyClient(property.getHost());
    }

    @Override
    public void processor(Class originClass) {

        ControllerType controllerType = getControllerType(originClass);
        if (controllerType == null) {
            return;
        }

        springControllerHandleEngine.handle(controllerType, originClass, null);
    }

    private ControllerType getControllerType(Class originClass) {
        RestController restController = AnnotationUtils.findAnnotation(originClass, RestController.class);
        if (restController != null) {
            return ControllerType.REST;
        }
        Controller controller = AnnotationUtils.findAnnotation(originClass, Controller.class);
        if (controller != null) {
            return ControllerType.NORMAL;
        }
        return null;
    }


    @Override
    public void finishProcessor() {
        List<ApiSignatureFrom> apiSignatureItemFroms = springControllerHandleEngine.getMappingResultList().stream()
                .map(as -> new ApiSignatureFrom(as.getMethodType(), as.getUrl(), as.getSignature()))
                .collect(Collectors.toList());
        ProjectApiSignatureFrom projectApiSignatureFrom = ProjectApiSignatureFrom.builder().appName(property.getAppName())
                .commitId(property.getCommitId()).ref(property.getRef())
                .apiSignature(apiSignatureItemFroms).build();
        try {
            endpointTopologyClient.send(projectApiSignatureFrom);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
