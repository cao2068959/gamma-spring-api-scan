package com.chy.processor.api.scan.common;


import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeUtils {

    public static Class typeToClass(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type rawType = parameterizedType.getRawType();
            return typeToClass(rawType);
        } else if (type instanceof GenericArrayType){
            GenericArrayType genericArrayType = (GenericArrayType) type;
            return typeToClass(genericArrayType.getGenericComponentType());
        }
        return Object.class;
    }

    public static Type getGenericType(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                return actualTypeArguments[0];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
