package com.priyam.java_vertx_4;

import com.priyam.java_vertx_4.dynamo.KeyValueController;
import com.priyam.java_vertx_4.dynamo.KeyValueServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticleX extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

//    vertx.deployVerticle(new Subscriber1X());
//    vertx.deployVerticle(new Subscriber2X());
//    vertx.deployVerticle(new P2PConsumer1X());
//    vertx.deployVerticle(new P2PSenderX());
//    vertx.deployVerticle(new PublisherX());
//    vertx.deployVerticle(new Router1());
//    vertx.deployVerticle(new StudentCrudVerticle());
//    vertx.deployVerticle("com.priyam.java_vertx_4.client.DepartmentApiWebClient");
    vertx.deployVerticle(new KeyValueServiceVerticle())
      .onSuccess(s -> vertx.deployVerticle(new KeyValueController()))
      .onSuccess(s -> startPromise.complete());

  }
}
