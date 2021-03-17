package com.priyam.java_vertx_4;

import com.priyam.java_vertx_4.dynamo.KeyValueController;
import com.priyam.java_vertx_4.dynamo.KeyValueServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticleX extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    vertx.deployVerticle(new KeyValueServiceVerticle());
    vertx.deployVerticle(new KeyValueController());

  }
}
