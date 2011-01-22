package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("main");

        Server server = new Server(8080);
        server.setHandler(new WebAppContext("src/main/webapp", "/example"));
        server.setStopAtShutdown(true);
        server.start();
    }
}
