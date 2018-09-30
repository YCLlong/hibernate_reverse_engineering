package cn.ycl.reverse.out.template;

import cn.ycl.reverse.constant.OracleFieldConvert;
import cn.ycl.reverse.jpa.IFieldConvertable;
import cn.ycl.reverse.out.JpaTableTemplate;
import cn.ycl.reverse.utils.StandReNameUtils;
import cn.ycl.reverse.vo.FieldDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class HibernateTableTemplate extends JpaTableTemplate {
    private IFieldConvertable convertable;
    public HibernateTableTemplate(String tableName,String schemaName, Map<String, FieldDTO> fieldDTOMap) {
        super(tableName,schemaName,fieldDTOMap);
        convertable = new OracleFieldConvert();
    }

    public String getFieldType(FieldDTO field){
        return convertable.convert(field);
    }
    @Override
    public StringBuilder getImportStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("import javax.persistence.Column;").append(LINE_SEPARATOR);
        sb.append("import javax.persistence.Entity;").append(LINE_SEPARATOR);
        sb.append("import javax.persistence.Table;").append(LINE_SEPARATOR);

        for(FieldDTO temp : getFieldDTOMap().values()){
            if("DATE".equals(temp.getFieldType())){
                sb.append("import java.util.Date;").append(LINE_SEPARATOR);
                break;
            }
        }

        for(FieldDTO temp : getFieldDTOMap().values()){
            if(temp.isPrimaryKey()){
                sb.append("import javax.persistence.GeneratedValue;").append(LINE_SEPARATOR);
                sb.append("import javax.persistence.Id;").append(LINE_SEPARATOR);
                sb.append("import org.hibernate.annotations.GenericGenerator;").append(LINE_SEPARATOR);
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
        return TAB + "private " + getFieldType(field) + " " + StandReNameUtils.reName(field.getFieldName()) + ";";
    }

    @Override
    public StringBuilder getConstructorStr(Map<String, FieldDTO> fieldDTOMap) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbThis = new StringBuilder();
        sb.append(TAB).append("public ").append(getClassName(getTableName())).append("(");
        Collection c = fieldDTOMap.values();
        List<FieldDTO> list = new ArrayList<FieldDTO>();
        list.addAll(c);
        sb.append(list.get(0).getFieldType()).append(" ").append(list.get(0).getFieldName());
        sbThis.append(TAB).append(TAB).append("this.").append(list.get(0).getFieldName()).append(" = ").append(list.get(0).getFieldName());
        for(int i = 1;i<list.size(); i++){
            sb.append(", ").append(list.get(i).getFieldType()).append(" ").append(list.get(i).getFieldName());
            sbThis.append(LINE_SEPARATOR).append(TAB).append(TAB).append("this.").append(list.get(i).getFieldName()).append(" = ").append(list.get(i).getFieldName());
        }
        sb.append(") {").append(LINE_SEPARATOR);
        sb.append(sbThis);
        sb.append(LINE_SEPARATOR).append(TAB).append("}").append(LINE_SEPARATOR);
        return sb;
    }

    @Override
    public StringBuilder getSetStr(FieldDTO field) {
        String fieldName =  StandReNameUtils.reName(field.getFieldName());
        String fieldType = getFieldType(field);
        StringBuilder sb = new StringBuilder();
        sb.append(TAB).append("public void " + StandReNameUtils.reName("set_" + field.getFieldName()) + "(" +  fieldType + " " + fieldName + ") {").append(LINE_SEPARATOR);
        sb.append(TAB).append(TAB).append("this." + fieldName + " = " + fieldName).append(";").append(LINE_SEPARATOR);
        sb.append(TAB).append("}").append(LINE_SEPARATOR);
        return sb;
    }

    @Override
    public StringBuilder getGetStr(FieldDTO field) {
        String fieldName =  StandReNameUtils.reName(field.getFieldName());
        String fieldType = getFieldType(field);
        StringBuilder sb = new StringBuilder();
        if(field.isPrimaryKey()){
            sb.append(TAB).append("@GenericGenerator(name = \"generator\", strategy = \"uuid.hex\")").append(LINE_SEPARATOR);
            sb.append(TAB).append("@Id").append(LINE_SEPARATOR);
            sb.append(TAB).append("@GeneratedValue(generator = \"generator\")").append(LINE_SEPARATOR);
        }


        if(field.getFieldType().equals("NUMBER")){
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
        return StandReNameUtils.classReName("_" + tableName);
    }
}
