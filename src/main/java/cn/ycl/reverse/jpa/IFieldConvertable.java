package cn.ycl.reverse.jpa;

import cn.ycl.reverse.vo.FieldDTO;

/**
 * @Auther: ycl
 * @Date: 2018/9/30 16:40
 * @Description:
 */
public interface IFieldConvertable {
    String convert(FieldDTO field);
}
