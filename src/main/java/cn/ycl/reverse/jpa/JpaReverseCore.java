package cn.ycl.reverse.jpa;

import cn.ycl.reverse.out.JpaTableTemplate;
import cn.ycl.reverse.vo.FieldDTO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class JpaReverseCore{
    private String schema;
    private File outPath;//输出路径
    private  Connection connection;
    private DatabaseMetaData metaData;

    public JpaReverseCore(String schema, File outPath,Connection connection) throws Exception {
        this.schema = schema;
        this.outPath = outPath;
        this.connection = connection;
        this.metaData = connection.getMetaData();
    }

    public abstract Set<String> getNeedReverseTables();
    public abstract JpaTableTemplate getTemplate(String tableName,Map<String, FieldDTO> fieldDTOMap);
    public Map<String, FieldDTO> reverseAnalyse(String tableName) throws Exception {
        //获取连接所有的表
        Map<String, FieldDTO> fieldMap = null;
        ResultSet tableRet = metaData.getTables(null,schema,tableName,new String[]{"TABLE","VIEW"});
        if(tableRet.next()){
            fieldMap = new HashMap<>();
            String columnName;
            String columnType;
            ResultSet colRet = metaData.getColumns(null,schema, tableName,"%");
            while(colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                int length = colRet.getInt("COLUMN_SIZE");
                int scale = colRet.getInt("DECIMAL_DIGITS");
                boolean nullable = colRet.getInt("NULLABLE")==0?false:true;
                fieldMap.put(columnName,new FieldDTO(columnName,columnType,length,scale,nullable,false));
            }
            //找主键
            ResultSet primyKey = metaData.getPrimaryKeys(null,null,tableName);
            while (primyKey.next()){
                String primyKeyName =  primyKey.getString("COLUMN_NAME");
                fieldMap.get(primyKeyName).setPrimaryKey(true);
            }
        }else {
            throw new Exception("找不到表名" + tableName);
        }
        return fieldMap;
    }

    public JpaReverseCore(File outPath){
        this.outPath = outPath;
    }

    public void create() throws Exception {
        try {
            //获取要逆向的表名
            for (String tableName : getNeedReverseTables()) {
                //解析出字段和字段属性
                Map<String, FieldDTO> fieldDTOMap = reverseAnalyse(tableName);
                //获得对应的表生成模板
                JpaTableTemplate template = getTemplate(tableName, fieldDTOMap);
                //将生成的java文件写到磁盘
                FileWriter fileWriter = new FileWriter(new File(outPath, template.getClassName(tableName) + ".java"));
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(template.createJpaJavaFile());
                bw.close();
            }
        }finally {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }
    }

    public String getSchema() {
        return schema;
    }

    public File getOutPath() {
        return outPath;
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseMetaData getMetaData() {
        return metaData;
    }
}
