package cn.ycl.reverse.out;

import cn.ycl.reverse.vo.FieldDTO;

import java.util.Map;

public abstract class JpaTableTemplate {
    public static final  String LINE_SEPARATOR = System.getProperty("line.separator");
    private String tableName;
    private Map<String, FieldDTO> fieldDTOMap;
    private StringBuilder setGetSb;//setGet
    private StringBuilder propertySb;//property
    private StringBuilder jpaJavaContent;
    public JpaTableTemplate(String tableName,Map<String, FieldDTO> fieldDTOMap){
        this.tableName = tableName;
        this.fieldDTOMap = fieldDTOMap;
    }

    public abstract StringBuilder getImportStr();
    public abstract StringBuilder getClassStr(String tableName);
    public abstract StringBuilder getConstructorStr();
    public abstract StringBuilder getPropertyStr(FieldDTO field);
    public abstract StringBuilder getSetStr(FieldDTO field);
    public abstract StringBuilder getGetStr(FieldDTO field);
    public abstract String getTableName();
    /**
     * 生成逆向工程的java类的内容
     * @return
     */
    public String createJpaJavaFile(){
        jpaJavaContent.append(getImportStr()).append(LINE_SEPARATOR);
        jpaJavaContent.append(getClassStr(tableName)).append(" {").append(LINE_SEPARATOR);

        for(FieldDTO fieldDTO:fieldDTOMap.values()) {
            propertySb.append(getPropertyStr(fieldDTO)).append(LINE_SEPARATOR);
            setGetSb.append(getSetStr(fieldDTO)).append(LINE_SEPARATOR);
            setGetSb.append(getGetStr(fieldDTO)).append(LINE_SEPARATOR);
        }

        jpaJavaContent.append(propertySb).append(LINE_SEPARATOR);
        jpaJavaContent.append(getConstructorStr()).append(LINE_SEPARATOR);
        jpaJavaContent.append(setGetSb).append(LINE_SEPARATOR);
        jpaJavaContent.append("}");
        return  jpaJavaContent.toString();
    }
}
