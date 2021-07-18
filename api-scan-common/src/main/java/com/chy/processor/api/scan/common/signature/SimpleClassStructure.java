package com.chy.processor.api.scan.common.signature;


import com.chy.gamma.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SimpleClassStructure {

    List<FieldDescribe> fields = new ArrayList<>();

    public void addBaseTypeField(String type, String name) {
        FieldDescribe fieldDescribe = new FieldDescribe(type, name);
        fields.add(fieldDescribe);
    }

    public void addComplexTypeField(String type, String name, SimpleClassStructure classStructure) {
        FieldDescribe fieldDescribe = new FieldDescribe(type, name);
        fieldDescribe.setValue(classStructure);
        fields.add(fieldDescribe);
    }

    public void sort() {
        fields.sort((a1, a2) -> {
            return a1.compare(a1, a2);
        });
    }


    public String formatString() {
        return linePrinf(doFormatString(""));
    }

    protected List<String> doFormatString(String fisrtLine) {
        List<String> lineData = new ArrayList<>();
        if (fields.size() == 0) {
            lineData.add(fisrtLine);
            return lineData;
        }
        if (StringUtils.isEmpty(fisrtLine)) {
            lineData.add("{");
        } else {
            lineData.add(fisrtLine + ":{");
        }

        for (FieldDescribe field : fields) {
            List<String> fieldStringList = field.formatString();
            for (int i = 0; i < fieldStringList.size(); i++) {
                String fildLineData = fieldStringList.get(i);
                if (i == 0) {
                    lineData.add(tabFlag(1) + fildLineData);
                } else {
                    lineData.add(tabFlag(2) + fildLineData);
                }
            }
        }
        lineData.add("}");
        return lineData;
    }

    private String linePrinf(List<String> datas) {
        StringBuffer result = new StringBuffer();
        for (String data : datas) {
            result.append(data + linefeed());
        }
        return result.toString();
    }

    private String tabFlag(int num) {
        String result = "";
        for (int i = 0; i < num; i++) {
            result = result + "  ";
        }
        return result;
    }

    private String linefeed() {
        return "\r\n";
    }

    @Getter
    @Setter
    class FieldDescribe implements Comparator<FieldDescribe> {
        String classType;
        String name;
        SimpleClassStructure value;

        public FieldDescribe(String classType, String name) {
            this.classType = classType;
            this.name = name;
        }


        public String typeAndName() {
            return classType + " " + name;
        }

        public List<String> formatString() {
            String typeAndName = typeAndName();
            if (value == null) {
                List<String> result = new ArrayList(1);
                result.add(typeAndName);
                return result;
            }

            return value.doFormatString(typeAndName);
        }


        @Override
        public int compare(FieldDescribe o1, FieldDescribe o2) {
            if ("$returnField".equals(o1.getName())) {
                return 1;
            }
            return o1.typeAndName().compareTo(o2.typeAndName());
        }
    }

}
