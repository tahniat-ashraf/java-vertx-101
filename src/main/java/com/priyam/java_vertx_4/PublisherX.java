package com.priyam.java_vertx_4;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2/26/21
 */

public class PublisherX extends AbstractVerticle {

  public static final String ADDRESS = "Publisher";

  @Override
  public void start(Promise<Void> startPromise) throws Exception {


    Router router = Router.router(vertx);


    router
      .route("/hello")
      .handler(routingContext -> {

        var obj = new JsonObject();
        obj.put("FROM", "PUBLISHER");

        vertx.eventBus()
          .publish(PublisherX.ADDRESS, obj);
        var response = routingContext.response();
        response.putHeader("content-type", "text/plain");
        response.end("Published event from PublisherX");
      });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080);


//        httpServerRequest.response()
//          .end("Published Obj :: " + obj);

  }

}
