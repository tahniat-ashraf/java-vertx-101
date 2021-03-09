package com.priyam.java_vertx_4;

import com.priyam.java_vertx_4.crud.student.StudentCrudVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticleX extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    vertx.deployVerticle(new Subscriber1X());
    vertx.deployVerticle(new Subscriber2X());
    vertx.deployVerticle(new P2PConsumer1X());
    vertx.deployVerticle(new P2PSenderX());
    vertx.deployVerticle(new PublisherX());
    vertx.deployVerticle(new Router1());
    vertx.deployVerticle(new StudentCrudVerticle());
  }
}
