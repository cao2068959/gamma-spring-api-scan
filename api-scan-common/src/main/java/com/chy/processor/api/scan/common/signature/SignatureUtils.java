package com.chy.processor.api.scan.common.signature;


import com.chy.processor.api.scan.common.signature.unpack.UnpackExecutor;
import com.chy.processor.api.scan.common.signature.unpack.UnpackResult;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class SignatureUtils {

    private static UnpackExecutor unpackExecutor = new UnpackExecutor();

    public static String createSignatureByMethod(Method origin) {
        Parameter[] parameters = origin.getParameters();
        TypeWapper methodCollect = new TypeWapper(origin.getName() + "-Method");
        for (Parameter parameter : parameters) {
            Type parameterizedType = parameter.getParameterizedType();
            methodCollect.addFields(parameterizedType, parameter.getName());
        }
        Type genericReturnType = origin.getGenericReturnType();
        methodCollect.addFields(genericReturnType, "$returnField");

        SimpleClassStructure signatureByClass = createSignatureByClass(methodCollect, null);
        String formatString = signatureByClass.formatString();
        //System.out.println(formatString);
        return  MD5Utils.stringToMD5(formatString);
    }

    public static SimpleClassStructure createSignatureByClass(TypeWapper type, Set<String> repeat) {
        if (isSkip(type)) {
            return new SimpleClassStructure();
        }

        if (repeat == null) {
            repeat = new HashSet<>();
        } else {
            repeat = new HashSet<>(repeat);
        }
        String typeName = type.getTypeName();
        if (repeat.contains(typeName)) {
            return new SimpleClassStructure();
        }
        repeat.add(typeName);
        if (!type.exsitClassType()) {
            return new SimpleClassStructure();
        }

        SimpleClassStructure result = new SimpleClassStructure();
        for (FieldWapper field : type.getFields()) {
            UnpackResult unpack = unpackExecutor.unpack(field.getType());
            Optional<Type> finalType = unpack.getFinalType();
            String fieldName = field.getName();
            if (!finalType.isPresent()) {
                result.addBaseTypeField(unpack.getSimpleTypeName(), fieldName);
            } else {
                TypeWapper typeWapper = new TypeWapper(finalType.get());
                SimpleClassStructure fileClassStructure = createSignatureByClass(typeWapper, repeat);
                result.addComplexTypeField(unpack.getSimpleTypeName(), fieldName, fileClassStructure);
            }
        }

        result.sort();
        return result;
    }

    private static boolean isSkip(TypeWapper type) {
        String typeName = type.getTypeName();
        if (typeName.startsWith("java.")) {
            return true;
        }
        Class aClass = type.getClassType();
        if (aClass == null) {
            return false;
        }
        return false;
    }

    private static Class realClassType(Class<?> type) {
        return type;
    }


    public static Optional<String> arrayTypeHandle(Class origin, Function<Class, Optional<String>> converFun) {
        boolean isArray = false;
        if (origin.isArray()) {
            origin = origin.getComponentType();
            isArray = true;
        }
        Optional<String> result = converFun.apply(origin);
        if (isArray) {
            result = result.map(v -> v + "[]");
        }
        return result;
    }

}
