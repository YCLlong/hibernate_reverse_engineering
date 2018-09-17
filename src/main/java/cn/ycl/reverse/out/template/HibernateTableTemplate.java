package cn.ycl.reverse.out.template;

import cn.ycl.reverse.constant.OracleFieldType;
import cn.ycl.reverse.out.JpaTableTemplate;
import cn.ycl.reverse.utils.StandReNameUtils;
import cn.ycl.reverse.vo.FieldDTO;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;

import java.util.Map;

public class HibernateTableTemplate extends JpaTableTemplate {
    public HibernateTableTemplate(String tableName,String schemaName, Map<String, FieldDTO> fieldDTOMap) {
        super(tableName,schemaName,fieldDTOMap);
    }

    @Override
    public StringBuilder getImportStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("import javax.persistence.Column;").append(LINE_SEPARATOR);
        sb.append("import javax.persistence.Entity;").append(LINE_SEPARATOR);
        sb.append("import javax.persistence.GeneratedValue;").append(LINE_SEPARATOR);
        sb.append("import javax.persistence.Id;").append(LINE_SEPARATOR);
        sb.append("import javax.persistence.Table;").append(LINE_SEPARATOR);
        sb.append("import org.hibernate.annotations.GenericGenerator;").append(LINE_SEPARATOR);
        for(FieldDTO temp : getFieldDTOMap().values()){
            if("DATE".equals(temp.getFieldType().toUpperCase())){
                sb.append("import java.util.Date;").append(LINE_SEPARATOR);
                break;
            }
        }
        return sb;
    }

    @Override
    public StringBuilder getClassStr(String tableName,String schemaName) {
        StringBuilder sb = new StringBuilder();
        sb.append("@Entity").append(LINE_SEPARATOR);
        sb.append("@Table(name = \"" + tableName +"\", schema = \"" + schemaName +"\")").append(LINE_SEPARATOR);
        sb.append("public class " + tableName + " implements java.io.Serializable");
        return sb;
    }

    @Override
    public String getPropertyStr(FieldDTO field) {
        return "    private " + OracleFieldType.getOracleFieldType(field.getFieldType()) + " " + StandReNameUtils.reName(field.getFieldName());
    }

    @Override
    public StringBuilder getConstructorStr(Map<String, FieldDTO> fieldDTOMap) {
        StringBuilder sb = new StringBuilder();
        for(FieldDTO field:fieldDTOMap.values()){

        }
        return sb;
    }

    @Override
    public StringBuilder getSetStr(FieldDTO field) {
        String fieldName =  StandReNameUtils.reName(field.getFieldName());
        String fieldType = OracleFieldType.getOracleFieldType(field.getFieldType());
        StringBuilder sb = new StringBuilder();
        sb.append(TAB).append("public void " + StandReNameUtils.reName("set_" + field.getFieldName()) + "(" +  fieldType + " " + fieldName + ") {").append(LINE_SEPARATOR);
        sb.append(TAB).append(TAB).append("this." + fieldName + " = " + fieldName).append(";").append(LINE_SEPARATOR);
        sb.append(TAB).append("}").append(LINE_SEPARATOR);
        return sb;
    }

    @Override
    public StringBuilder getGetStr(FieldDTO field) {
        String fieldName =  StandReNameUtils.reName(field.getFieldName());
        String fieldType = OracleFieldType.getOracleFieldType(field.getFieldType());
        StringBuilder sb = new StringBuilder();
        if(field.getFieldType().toUpperCase().equals(OracleFieldType.NUMBER)){
            sb.append(TAB).append("@Column(name = \""+field.getFieldName()+"\", precision = "+field.getLength()+", scale = "+field.getScale()+")").append(LINE_SEPARATOR);
        }else{
            sb.append(TAB).append("@Column(name = \"" + field.getFieldName() + "\", length = " + field.getLength() + ")").append(LINE_SEPARATOR);
        }
        sb.append(TAB).append("public " + fieldType + " " + StandReNameUtils.reName("get_" + field.getFieldName())).append("() ").append("{").append(LINE_SEPARATOR);
        sb.append(TAB).append(TAB).append("return this." + fieldName + ";").append(LINE_SEPARATOR);
        sb.append(TAB).append("}").append(LINE_SEPARATOR);
        return sb;
    }

    @Override
    public String getClassName(String tableName) {
        return StandReNameUtils.reName("_" + tableName);
    }
}
