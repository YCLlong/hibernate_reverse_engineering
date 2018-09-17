package cn.ycl.reverse.out;

import cn.ycl.reverse.vo.FieldDTO;

import java.util.Map;

public abstract class JpaTableTemplate {
    public static final  String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final  String TAB = "    ";
    private String tableName;
    private String schemaName;
    private Map<String, FieldDTO> fieldDTOMap;
    private StringBuilder setGetSb = new StringBuilder();//setGet
    private StringBuilder propertySb = new StringBuilder();//property
    private StringBuilder jpaJavaContent = new StringBuilder();
    public JpaTableTemplate(String tableName,String schemaName,Map<String, FieldDTO> fieldDTOMap){
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.fieldDTOMap = fieldDTOMap;
    }

    public abstract StringBuilder getImportStr();
    public abstract StringBuilder getClassStr(String tableName,String schemaName);
    public abstract String getPropertyStr(FieldDTO field);
    public abstract StringBuilder getConstructorStr(Map<String, FieldDTO> fieldDTOMap);
    public abstract StringBuilder getSetStr(FieldDTO field);
    public abstract StringBuilder getGetStr(FieldDTO field);
    public abstract String getClassName(String tableName);
    /**
     * 生成逆向工程的java类的内容
     * @return
     */
    public String createJpaJavaFile(){
        jpaJavaContent.append(getImportStr()).append(LINE_SEPARATOR);
        jpaJavaContent.append(getClassStr(tableName,schemaName)).append(" {").append(LINE_SEPARATOR);

        for(FieldDTO fieldDTO:fieldDTOMap.values()) {
            propertySb.append(getPropertyStr(fieldDTO)).append(LINE_SEPARATOR);
            setGetSb.append(getGetStr(fieldDTO)).append(LINE_SEPARATOR);
            setGetSb.append(getSetStr(fieldDTO)).append(LINE_SEPARATOR);
        }

        jpaJavaContent.append(propertySb).append(LINE_SEPARATOR);
        jpaJavaContent.append(getConstructorStr(fieldDTOMap)).append(LINE_SEPARATOR);
        jpaJavaContent.append(setGetSb).append(LINE_SEPARATOR);
        jpaJavaContent.append("}");
        return  jpaJavaContent.toString();
    }

    public Map<String, FieldDTO> getFieldDTOMap() {
        return fieldDTOMap;
    }
}
