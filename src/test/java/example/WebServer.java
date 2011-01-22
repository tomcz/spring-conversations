package example;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new WebAppContext("src/main/webapp", "/example"));
        server.start();
    }
}
