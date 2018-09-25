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
        strings.add("TB_ORG");
        strings.add("TB_ORG_HIS");
        return strings;
    }

    @Override
    public JpaTableTemplate getTemplate(String tableName, Map<String, FieldDTO> fieldDTOMap) {
        return new HibernateTableTemplate(tableName,getSchema(),fieldDTOMap);
    }

    public static void main(String[] args) throws Exception {
        String driverClass = "oracle.jdbc.OracleDriver";
        String url = "jdbc:oracle:thin:@192.168.66.201:1521:db";
        String username="bss";
        String pwd = "bss";
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url,username,pwd);
        HibernateReverse hibernateReverse = new HibernateReverse("BSS",new File("C:\\Users\\ZJCA\\Desktop\\OUT"),connection);
        hibernateReverse.create();
        System.out.println("=================end===================");
    }
}
