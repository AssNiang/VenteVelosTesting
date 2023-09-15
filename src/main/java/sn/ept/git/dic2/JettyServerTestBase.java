package sn.ept.git.dic2;
import org.eclipse.jetty.server.Server;

public class JettyServerTestBase {

    private static Server server;

    public static void startServer() throws Exception {
        server = new Server(8081); // Spécifiez le port que vous souhaitez utiliser
        server.start();
    }

    public static void stopServer() throws Exception {
        if (server != null && server.isRunning()) {
            server.stop();
        }
    }
}
