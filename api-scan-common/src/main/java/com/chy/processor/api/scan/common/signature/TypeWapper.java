package com.chy.processor.api.scan.common.signature;


import com.chy.processor.api.scan.common.TypeUtils;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TypeWapper {

    @Getter
    Class classType;
    Type type;
    @Getter
    List<FieldWapper> fields;
    @Getter
    String typeName;

    public TypeWapper(Type type) {
        this.type = type;
        this.classType = TypeUtils.typeToClass(type);
        typeName = type.getTypeName();
        if (classType != null) {
            fields = Arrays.stream(classType.getDeclaredFields()).map(FieldWapper::new).collect(Collectors.toList());
        }
    }

    public TypeWapper(String typeName) {
        this.typeName = typeName;
        fields = new ArrayList<>();
        this.classType = TypeWapper.class;
    }

    public boolean exsitClassType(){
        return classType != null;
    }

    public void addFields(Type type, String name) {
        FieldWapper fieldWapper = new FieldWapper(type, name);
        fields.add(fieldWapper);
    }


}
