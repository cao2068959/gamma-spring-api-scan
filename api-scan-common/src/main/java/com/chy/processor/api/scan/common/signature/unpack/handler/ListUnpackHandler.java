package com.chy.processor.api.scan.common.signature.unpack.handler;

import com.chy.processor.api.scan.common.TypeUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;


public class ListUnpackHandler implements UnpackHandler {

    @Override
    public boolean isHandle(Type origin) {
        Class aClass = TypeUtils.typeToClass(origin);
        if (aClass == null){
            return false;
        }

        if (List.class.getName().equals(aClass.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<String> typeFlag(Type origin) {
        return Optional.of("List");
    }

    @Override
    public Optional<Type> afterType(Type origin) {
        Type genericType = TypeUtils.getGenericType(origin);
        return Optional.ofNullable(genericType);
    }

}
