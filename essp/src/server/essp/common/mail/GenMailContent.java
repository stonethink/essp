package server.essp.common.mail;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import java.io.StringWriter;
import java.util.Date;
import java.util.ArrayList;
import server.framework.common.BusinessException;
import java.util.Properties;
import com.wits.util.comDate;

/**
 *此类用于根据不同的模版生成MAIL内容
 *author:Robin.Zhang
 */
public class GenMailContent {

    public String addressee;
    public final String addresser = "ESSP";//"<a href=\"http://essp.wh.wistronits.com/essp\">ESSP</a>";

    /**
     *此方法生成每封邮件发送的内容
     * 参数：vmFile　Velocity使用的模版文件
     * 　　　cb 发送内容的Bean
     * 返回：以String的形式返回要发送的内容
     */

    public String generateMailContent(String vmFile, ContentBean cb) {

        String mailContent = null;
        try {

            VelocityEngine ve = new VelocityEngine();
            Properties p = new Properties();
            p.setProperty("file.resource.loader.path", ".");
            p.setProperty("runtime.log.logsystem.log4j.category", "rootCategory");

            ve.init(p);

            Template t = ve.getTemplate(vmFile, "utf-8");
            VelocityContext vContext = new VelocityContext();

            vContext.put("addressee", cb.getUser());
            vContext.put("addresser", this.addresser);
            vContext.put("date", comDate.dateToString(new Date()));

            ArrayList temp = new ArrayList();
            temp = cb.getMailcontent();
            vContext.put("content", temp);

            StringWriter writer = new StringWriter();
            t.merge(vContext, writer);
            mailContent = writer.toString();
        } catch (Throwable tx) {
            String msg = "Generator mail content exception by Velocity!";
            throw new BusinessException("", msg, tx);
        }

        return mailContent;
    }


}
