import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import java.util.Date;

public class Runner {

    static {
        System.setProperty("PORT", "8080");
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.factory.vertx();

        HttpServerOptions httpServerOptions = new HttpServerOptions()
                .setMaxInitialLineLength(4096 * 4)
                .setCompressionSupported(true)
                .setMaxHeaderSize(8192 * 2);

        Router router = Router.router(vertx);
        HttpServer httpServer = vertx.createHttpServer(httpServerOptions);

        router.get("/test/").blockingHandler(context -> {
            System.out.println(new Date() + ": One hit");

            context.response()
                    .setStatusCode(200)
                    .end("ahihi");
        });

        httpServer.requestHandler(router::accept).listen(Integer.parseInt(System.getenv("PORT")), "0.0.0.0");
        System.out.println("Listen on port: " + System.getenv("PORT"));
    }
}
