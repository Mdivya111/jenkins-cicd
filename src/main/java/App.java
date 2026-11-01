import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8081), 0
        );

        server.createContext("/", exchange -> {

            String response = "Hello! Your application is successfully deployed through Jenkins.";

            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8"
            );

            byte[] responseBytes = response.getBytes("UTF-8");

            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        });

        server.start();

        System.out.println("Application is running on port 8081");

        // Keep the Java application running.
        Thread.currentThread().join();
    }
}
