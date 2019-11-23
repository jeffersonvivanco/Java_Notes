package programming;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PlayTomcat {

    private static Logger logger = LoggerFactory.getLogger(PlayTomcat.class);

    public static void main(String[] args) throws Exception{
        String staticFilesPath = "src/main/resources";
        Tomcat tomcat = new Tomcat();

        int PORT = 8080;
        tomcat.setPort(PORT);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(staticFilesPath).getAbsolutePath());
        logger.info("Configuring app with basedir: " + new File("./" + staticFilesPath).getAbsolutePath());

        WebResourceRoot resources = new StandardRoot(ctx);
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer();
    }
}
