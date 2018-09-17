package cn.ycl.reverse.constant;

public enum OracleFieldType {
    VARCHAR2("VARCHAR2","String"),
    DATE("DATE","Date"),
    NUMBER("NUMBER","NUMBER"),
    INTEGER("INTEGER","Integer"),
    DOUBLE("DOUBLE","Double"),
    LONG("LONG","Long");

    private String metaFieldType;
    private String oracleFieldType;
    private OracleFieldType(String metaFieldType,String oracleFieldType){
        this.metaFieldType = metaFieldType;
        this.oracleFieldType = oracleFieldType;
    }

    public static String getOracleFieldType(String metaFieldType){
        metaFieldType = metaFieldType.toUpperCase();
        for(OracleFieldType temp:OracleFieldType.values()){
            if(metaFieldType.equals(temp.metaFieldType)){
                return temp.oracleFieldType;
            }
        }
        return "未定义";
    }
}
