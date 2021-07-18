package com.chy.processor.api.scan.common.signature.unpack.handler;


import com.chy.processor.api.scan.common.TypeUtils;

import java.lang.reflect.Type;
import java.util.Optional;

public class ArrayUnpackHandler implements UnpackHandler {

    @Override
    public boolean isHandle(Type origin) {
        Class aClass = TypeUtils.typeToClass(origin);
        if (aClass == null) {
            return false;
        }
        return aClass.isArray();
    }

    @Override
    public Optional<String> typeFlag(Type origin) {
        return Optional.ofNullable(origin.getTypeName());
    }

    @Override
    public Optional<Type> afterType(Type origin) {
        Class aClass = TypeUtils.typeToClass(origin);
        if (aClass == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(aClass.getComponentType());
    }
}
