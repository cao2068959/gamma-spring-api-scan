package com.chy.processor.api.scan.common.signature;


import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

@Getter
public class FieldWapper {

    Field field;
    Type type;
    String name;


    public FieldWapper(Field field) {
        this.field = field;
        type = field.getGenericType();
        name = field.getName();
    }

    public FieldWapper(Type field, String name) {
        type = field;
        this.name = name;
    }

}
