package com.chy.procession.spring.api.scan.handle;


import com.chy.gamma.common.utils.LogUtils;
import com.chy.procession.spring.api.scan.ControllerType;
import com.chy.procession.spring.api.scan.mapping.AnnotationMappingContext;
import com.chy.procession.spring.api.scan.mapping.MappingItemResult;
import com.chy.procession.spring.api.scan.mapping.MappingResult;
import com.chy.processor.api.scan.common.signature.SignatureUtils;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;


import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class SpringControllerHandleEngine {

    Logger logger = LogUtils.getLogger("SpringControllerHandleEngine");
    AnnotationMappingContext annotationMappingContext = new AnnotationMappingContext();

    @Getter
    Set<ApiSignature> mappingResultList = new HashSet<>();

    public void handle(ControllerType controllerType, Class originClass, RequestMapping classRequestMapping) {

        if (classRequestMapping == null || !originClass.isInterface()) {
            RequestMapping requestMapping = AnnotationUtils.getAnnotation(originClass, RequestMapping.class);
            if (requestMapping != null){
                classRequestMapping = requestMapping;
            }
        }

        Class[] interfaces = originClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class anInterface : interfaces) {
                handle(controllerType, anInterface, classRequestMapping);
            }
        }

        Method[] methods = originClass.getDeclaredMethods();
        for (Method method : methods) {
            MappingResult mappingResult = annotationMappingContext.handle(method, classRequestMapping);
            if (mappingResult == null) {
                continue;
            }

            String signatureByMethod = SignatureUtils.createSignatureByMethod(method);
            for (MappingItemResult itemResult : mappingResult.getData()) {
                ApiSignature apiSignature = new ApiSignature(itemResult.getMethodType(), itemResult.getUrl(),
                        signatureByMethod);
                logger.debug(originClass + " ----> " + apiSignature);
                mappingResultList.add(apiSignature);
            }
        }
    }

}
