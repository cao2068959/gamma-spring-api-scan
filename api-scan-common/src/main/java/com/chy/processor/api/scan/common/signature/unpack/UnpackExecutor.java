package com.chy.processor.api.scan.common.signature.unpack;


import com.chy.processor.api.scan.common.TypeUtils;
import com.chy.processor.api.scan.common.signature.unpack.handler.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UnpackExecutor {

    List<UnpackHandler> unpackHandlers;

    public UnpackExecutor() {
        this.unpackHandlers = new ArrayList<>();
        unpackHandlers.add(new ListUnpackHandler());
        unpackHandlers.add(new ArrayUnpackHandler());
        unpackHandlers.add(new JsonResultUnpackHandler());
        unpackHandlers.add(new OptionalUnpackHandler());
    }

    public UnpackResult unpack(Type origin) {
        StringBuilder typeNameBuilder = new StringBuilder();
        Optional<Type> finalType = doExecUnpackHandlers(origin, typeNameBuilder);

        if (!finalType.isPresent()) {
            return new UnpackResult(formatTypeName(typeNameBuilder));
        }

        //检查是否是基础类型
        Optional<String> baseType = converBaseType(finalType.get());
        //如果是基础类型 那么就直接返回了不处理了
        if (baseType.isPresent()) {
            typeNameBuilder.append(':').append(baseType.get());
            return new UnpackResult(formatTypeName(typeNameBuilder));
        }

        typeNameBuilder.append(':').append(finalType.get().getTypeName());
        return new UnpackResult(formatTypeName(typeNameBuilder), finalType);
    }

    private String formatTypeName(StringBuilder typeNameBuilder) {
        if (typeNameBuilder.length() > 1 && typeNameBuilder.charAt(0) == ':') {
            return typeNameBuilder.substring(1);
        }
        return typeNameBuilder.toString();
    }

    private Optional<Type> doExecUnpackHandlers(Type origin, StringBuilder typeName) {
        for (UnpackHandler unpackHandler : unpackHandlers) {
            if (!unpackHandler.isHandle(origin)) {
                continue;
            }
            Optional<String> typeFlag = unpackHandler.typeFlag(origin);
            Optional<Type> optionalType = unpackHandler.afterType(origin);
            typeFlag.ifPresent(v -> typeName.append(':').append(v));
            if (optionalType.isPresent()) {
                return doExecUnpackHandlers(optionalType.get(), typeName);
            } else {
                return optionalType;
            }
        }
        return Optional.of(origin);
    }


    /**
     * 如果类型是 基本类型或者是包装类 那么就转义出对应的 字符， 如果不是基本类型，那么就直接返回空的Optional
     *
     * @return
     */
    public static Optional<String> converBaseType(Type type) {
        Class origin = TypeUtils.typeToClass(type);
        if (origin == null) {
            return Optional.empty();
        }
        if (origin.equals(String.class)) {
            return Optional.of("string");
        }

        if (origin.isPrimitive()) {
            return Optional.of(origin.getName());
        }

        if (origin.equals(Integer.class)) {
            return Optional.of("int");
        }

        if (origin.equals(Byte.class)) {
            return Optional.of("byte");
        }

        if (origin.equals(Long.class)) {
            return Optional.of("long");
        }

        if (origin.equals(Double.class)) {
            return Optional.of("double");
        }

        if (origin.equals(Float.class)) {
            return Optional.of("float");
        }

        if (origin.equals(Character.class)) {
            return Optional.of("char");
        }

        if (origin.equals(Short.class)) {
            return Optional.of("short");
        }

        if (origin.equals(Boolean.class)) {
            return Optional.of("boolean");
        }
        return Optional.empty();
    }


}
