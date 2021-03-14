package com.priyam.java_vertx_4.dynamo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/14/21
 */

public class KeyValueController extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    Router router = Router.router(vertx);

    router
      .route()
      .handler(BodyHandler.create());

    router.route()
      .handler(LoggerHandler.create(LoggerFormat.DEFAULT));

    router
      .route(HttpMethod.GET, "/keyValues")
      .handler(this::getKeyValues);

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(6678);

  }

  private void getDemoReply(RoutingContext routingContext) {


    vertx
      .eventBus()
      .request(KeyValueDynamoVerticle.GET_LIST_ADDRESS, null, messageAsyncResult -> {


        routingContext.response().end(Json.encodePrettily(messageAsyncResult.result().body().toString()));
      });
  }


  private void getKeyValues(RoutingContext routingContext) {

    vertx.eventBus()
      .request(KeyValueDynamoVerticle.GET_LIST_ADDRESS, new JsonObject(), messageAsyncResult -> {

        System.out.println("messageAsyncResult.result().body() = " + messageAsyncResult.result().body());

        routingContext.response().end(messageAsyncResult.result().body().toString());
      });
  }
}
