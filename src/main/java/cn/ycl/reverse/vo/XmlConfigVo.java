package cn.ycl.reverse.vo;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: ycl
 * @Date: 2018/9/26 08:13
 * @Description:
 */
public class XmlConfigVo {
    private File outPath;
    private Set<String> tables;
    private String driverClass;
    private String url;
    private String user;
    private String password;
    private String schema;
    private Map<String,String> fieldTypeConvert;

    public File getOutPath() {
        return outPath;
    }

    public void setOutPath(File outPath) {
        this.outPath = outPath;
    }

    public Set<String> getTables() {
        return tables;
    }

    public void setTables(Set<String> tables) {
        this.tables = tables;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Map<String, String> getFieldTypeConvert() {
        return fieldTypeConvert;
    }

    public void setFieldTypeConvert(Map<String, String> fieldTypeConvert) {
        this.fieldTypeConvert = fieldTypeConvert;
    }
}
