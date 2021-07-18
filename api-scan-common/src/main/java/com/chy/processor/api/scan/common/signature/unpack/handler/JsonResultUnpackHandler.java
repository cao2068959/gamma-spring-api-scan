package com.chy.processor.api.scan.common.signature.unpack.handler;


import com.chy.processor.api.scan.common.TypeUtils;

import java.lang.reflect.Type;
import java.util.Optional;

public class JsonResultUnpackHandler implements UnpackHandler {

    @Override
    public boolean isHandle(Type origin) {
        Class aClass = TypeUtils.typeToClass(origin);
        return "com.xingren.common.data.JsonResult".equals(aClass.getTypeName());
    }

    @Override
    public Optional<String> typeFlag(Type origin) {
        return Optional.of("JsonResult");
    }

    @Override
    public Optional<Type> afterType(Type origin) {
        Type genericType = TypeUtils.getGenericType(origin);
        return Optional.ofNullable(genericType);
    }
}
