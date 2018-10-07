package cn.ycl.reverse.jpa.impl;

import cn.ycl.reverse.vo.FieldDTO;

import java.util.Map;

/**
 * 字段转换
 */
public interface IFieldConvertable {
    String getFieldType(FieldDTO fieldDTO);
}
