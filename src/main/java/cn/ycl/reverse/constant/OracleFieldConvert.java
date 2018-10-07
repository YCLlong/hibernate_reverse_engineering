package cn.ycl.reverse.constant;

import cn.ycl.reverse.jpa.IFieldConvertable;
import cn.ycl.reverse.vo.FieldDTO;

/**
 * @Auther: ycl
 * @Date: 2018/9/30 16:41
 * @Description:
 */
public class OracleFieldConvert implements IFieldConvertable {
    @Override
    public String convert(FieldDTO field) {
        field.setFieldType(field.getFieldType().toUpperCase());
        //Date类型
        if(field.getFieldType().equals("VARCHAR2") || field.getFieldType().equals("VARCHAR")){
            return "String";
        }

        //NUMBERL数值类型
        if(field.getFieldType().equals("NUMBER")){
            //根据长度来
            if(field.getLength() <= 9){
                if(field.getScale() == 0) {
                    return "Integer";
                }else {
                    return "Float";
                }
            }else {
                if(field.getScale() == 0) {
                    return "Long";
                }else {
                    return "Double";
                }
            }
        }

        if(field.getFieldType().equals("DATE") || field.getFieldType().equals("TIME_STAMP")){
            return "Date";
        }

        return "未定义类型";
    }
}
