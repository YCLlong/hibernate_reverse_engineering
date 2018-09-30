package cn.ycl.reverse.main;

import cn.ycl.reverse.jpa.impl.HibernateReverse;
import cn.ycl.reverse.vo.XmlConfigVo;
import cn.ycl.reverse.xml.ConfigManger;

import java.io.File;

/**
 * @Auther: ycl
 * @Date: 2018/9/26 08:22
 * @Description:
 */
public class JpaReverseStarter {
    public static void main(String[] args) throws Exception {
        XmlConfigVo config = ConfigManger.getInstance().getConfig();
        HibernateReverse hibernateReverse = new HibernateReverse(config);
        hibernateReverse.create();
        System.out.println("=================成功=================");
        System.out.println("文件路径：" + config.getOutPath() + "\\");
        System.out.println("==========================Author:ycl==");
    }
}
