package handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import java.nio.file.*;

public class RootHandler implements HttpHandler {

    private static final String ROOTDIR = "web/";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            String requestedPath = exchange.getRequestURI().getPath();
            
            Path path = FileSystems.getDefault().getPath(ROOTDIR + requestedPath);

            File file = new File(path.toString()).getCanonicalFile();
            if (file.isFile()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                serveFile(exchange, path);

            } else if (Files.isDirectory(path)) {
                path = FileSystems.getDefault().getPath(ROOTDIR + requestedPath + "index.html");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                serveFile(exchange, path);

            } else {
                path = FileSystems.getDefault().getPath(ROOTDIR + "HTML/404.html");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                serveFile(exchange, path);

            }
        }
        catch (IOException e) {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

    public void serveFile(HttpExchange exchange, Path path) throws IOException {
        
        Files.copy(path, exchange.getResponseBody());
        exchange.getResponseBody().close();
    }

}
