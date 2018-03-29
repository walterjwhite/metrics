package com.walterjwhite.metrics.modules.prometheus;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrometheusHttpServer {
  protected final HttpServer httpServer;

  @Inject
  public PrometheusHttpServer(final PrometheusMeterRegistry prometheusMeterRegistry)
      throws IOException {
    httpServer = HttpServer.create(new InetSocketAddress(8181), 0);

    httpServer.createContext(
        "/prometheus",
        httpExchange -> {
          String response = prometheusMeterRegistry.scrape();
          httpExchange.sendResponseHeaders(200, response.length());
          OutputStream os = httpExchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
        });

    new Thread(httpServer::start).run();
  }
}
