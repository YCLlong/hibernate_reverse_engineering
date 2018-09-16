package cn.ycl.reverse.vo;

public class FieldDTO {
    private String fieldName;//字段名
    private String fieldType;//字段类型
    private int length;//长度
    private int scale;//精确对
    private boolean nullable;//是否可空
    private boolean  primaryKey;//是否是主键

    public FieldDTO(String fieldName, String fieldType, int length, int scale, boolean nullable, boolean primaryKey) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.length = length;
        this.scale = scale;
        this.nullable = nullable;
        this.primaryKey = primaryKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
