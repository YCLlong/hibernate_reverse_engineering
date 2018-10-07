package cn.ycl.test;

import java.sql.*;

public class DataBaseMetaDataDemo {
    public static void main(String[] args) throws Exception {
        /*String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jpa?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
        String username="root";
        String pwd = "Y024459";*/
        String driverClass = "oracle.jdbc.OracleDriver";
        String url = "jdbc:oracle:thin:@192.168.66.201:1521:db";
        String username = "bss";
        String pwd = "bss";
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, pwd);
        DatabaseMetaData metaData = connection.getMetaData();
        //获取连接所有的表
        ResultSet tableRet = metaData.getTables(null,"%","TB_MONEY",new String[]{"TABLE","VIEW"});
        //3. 提取表的名字。
        while(tableRet.next()){
            String tableName = tableRet.getString("TABLE_NAME");
            System.out.println("======================" + tableName + "=========================");
            ResultSet primyKey = metaData.getPrimaryKeys(null,null,tableName);
            while (primyKey.next()){
                System.out.println(primyKey.getString("COLUMN_NAME"));
            }
            //4. 提取表内的字段的名字和类型
            String columnName;
            String columnType;
            String sqlDataType;
            ResultSet colRet = metaData.getColumns(null,"%", tableName,"%");
            while(colRet.next()) {

                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                sqlDataType = colRet.getString("SQL_DATA_TYPE");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
                System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ nullable + "   " + sqlDataType);
            }
        }
        connection.close();
    }
}
