package cn.ycl.reverse.xml;

import cn.ycl.reverse.vo.XmlConfigVo;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: ycl
 * @Date: 2018/9/26 08:26
 * @Description:
 */
public class ConfigManger {
    private static final File configFilePath = new File("config.xml");
    private static final Logger log = Logger.getLogger(ConfigManger.class);
    private static ConfigManger instance;
    private XmlConfigVo config = null;
    private ConfigManger(){
        //不存在就写一个配置模板
        if(!configFilePath.exists()){
            try {
                createTemplate();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("创建配置文件失败");
            }
        }else{
            //读取配置文件的值
            SAXReader reader = new SAXReader();
            Document document = null;
            try {
                document = reader.read(configFilePath);
            } catch (DocumentException e) {
                e.printStackTrace();
                log.error("读取配置文件失败");
            }
            config = parseConfig(document);
        }
    }
    public static ConfigManger getInstance(){
      if(instance == null) {
          synchronized (ConfigManger.class) {
              if (instance == null) {
                  instance = new ConfigManger();
                  return instance;
              }
          }
      }
      return instance;
    }

    /**
     * 创建配置文件模版
     * @throws Exception
     */
    private void createTemplate() throws Exception {
        Document doc = DocumentHelper.createDocument();
        //增加根节点
        Element config = doc.addElement("config");
        Element outPath = config.addElement("outPath");
        outPath.addAttribute("value","");
        Element dataBase = config.addElement("data-base");
        Element driverClass = dataBase.addElement("driver-class");
        driverClass.addAttribute("value","");
        Element url = dataBase.addElement("url");
        url.addAttribute("value","");
        Element user = dataBase.addElement("userName");
        user.addAttribute("value","");
        Element password = dataBase.addElement("password");
        password.addAttribute("value","");
        Element schema = dataBase.addElement("schema");
        schema.addAttribute("value","");

        Element tables = config.addElement("tables");
        tables.addElement("table").addAttribute("name","");
        tables.addElement("table").addAttribute("name","");

        /*Element fieldTypeConvert = config.addElement("fieldType-convert");
        Element type1 = fieldTypeConvert.addElement("type").addAttribute("key","").addAttribute("value","");
        Element type2 = fieldTypeConvert.addElement("type").addAttribute("key","").addAttribute("value","");*/

       /*
       //为子节点添加属性
        book1.addAttribute("id", "001");
        //为元素添加内容
        title1.setText("Harry Potter");
        author1.setText("J K. Rowling");
        */
        //实例化输出格式对象
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置输出编码
        format.setEncoding("UTF-8");
        //生成XMLWriter对象，构造函数中的参数为需要输出的文件流和格式
        XMLWriter writer = new XMLWriter(new FileOutputStream(configFilePath), format);
        //开始写入，write方法中包含上面创建的Document对象
        writer.write(doc);
    }

    /**
     * 解析配置文件
     * @param document
     * @return
     */
    private XmlConfigVo parseConfig(Document document){
        XmlConfigVo config = new XmlConfigVo();
        Element root = document.getRootElement();
        //输出路径
        String outPath = root.element("outPath").attribute("value").getValue();
        config.setOutPath(new File(outPath));

        Element dataBaseElement = root.element("data-base");
        String driverClass = dataBaseElement.element("driver-class").attribute("value").getValue();
        String url = dataBaseElement.element("url").attribute("value").getValue();
        String userName = dataBaseElement.element("user").attribute("value").getValue();
        String password = dataBaseElement.element("password").attribute("value").getValue();
        String schema = dataBaseElement.element("schema").attribute("value").getValue();
        config.setDriverClass(driverClass);
        config.setUrl(url);
        config.setUserName(userName);
        config.setPassword(password);
        config.setSchema(schema);

        List<Element> tableElements = root.element("tables").elements("table");
        Set<String> tableSet = new HashSet<>();
        for(Element temp:tableElements){
            String tableName = temp.attribute("name").getValue();
            if(tableName != null && !tableName.trim().equals("")){
                tableSet.add(tableName);
            }
        }
        config.setTables(tableSet);
        return config;
    }

    public XmlConfigVo getConfig() {
        return config;
    }

    public static void main(String[] args){
        ConfigManger.getInstance().getConfig();
    }
}
