package com.chy.processor.api.scan.common.signature.unpack.handler;


import java.lang.reflect.Type;
import java.util.Optional;

public interface UnpackHandler {

    boolean isHandle(Type origin);

    Optional<String> typeFlag(Type origin);

    Optional<Type> afterType(Type origin);

}
