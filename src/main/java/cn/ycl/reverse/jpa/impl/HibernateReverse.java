package cn.ycl.reverse.jpa.impl;

import cn.ycl.reverse.jpa.JpaReverseCore;
import cn.ycl.reverse.out.JpaTableTemplate;
import cn.ycl.reverse.out.template.HibernateTableTemplate;
import cn.ycl.reverse.vo.FieldDTO;
import cn.ycl.reverse.vo.XmlConfigVo;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HibernateReverse extends JpaReverseCore {
    XmlConfigVo config;
    public HibernateReverse(XmlConfigVo config) throws Exception {
        super(config);
        this.config = config;
    }

    @Override
    public Set<String> getNeedReverseTables() {
        return config.getTables();
    }

    @Override
    public JpaTableTemplate getTemplate(String tableName, Map<String, FieldDTO> fieldDTOMap) {
        return new HibernateTableTemplate(tableName,getSchema(),fieldDTOMap);
    }
}
