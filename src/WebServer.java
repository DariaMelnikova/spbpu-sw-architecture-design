import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import model.PerformanceModel;

public class WebServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new GetUpcomingPerformanceHandler());
        server.setExecutor(null);
        server.start();
    }

    static class GetUpcomingPerformanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            PerformanceModel model = new PerformanceModel();
            String response = model.getAllUpcomingPerformancesWithAvailableTickets().toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
