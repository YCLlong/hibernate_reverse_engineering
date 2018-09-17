package cn.ycl.reverse.jpa.impl;

import cn.ycl.reverse.jpa.JpaReverseCore;
import cn.ycl.reverse.out.JpaTableTemplate;
import cn.ycl.reverse.out.template.HibernateTableTemplate;
import cn.ycl.reverse.vo.FieldDTO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HibernateReverse extends JpaReverseCore {
    public HibernateReverse(String schema, File outPath, Connection connection) throws Exception {
        super(schema, outPath, connection);
    }

    @Override
    public Set<String> getNeedReverseTables() {
        //读取配置文件
        Set<String> strings = new HashSet<>();
        strings.add("tb_jpa");
        strings.add("tb_jpa_copy");
        return strings;
    }

    @Override
    public JpaTableTemplate getTemplate(String tableName, Map<String, FieldDTO> fieldDTOMap) {
        return new HibernateTableTemplate(tableName,getSchema(),fieldDTOMap);
    }

    public static void main(String[] args) throws Exception {
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jpa?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
        String username="root";
        String pwd = "Y024459";
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url,username,pwd);
        HibernateReverse hibernateReverse = new HibernateReverse("",new File("C:\\Users\\ycl123\\Desktop\\out"),connection);
        hibernateReverse.create();
        System.out.println("=================end===================");
    }
}
