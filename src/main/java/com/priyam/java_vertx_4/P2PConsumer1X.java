package com.priyam.java_vertx_4;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2/26/21
 */

public class P2PConsumer1X extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    vertx.eventBus()
      .consumer(P2PSenderX.ADDRESS, message -> {

        var body = message.body();
        System.out.println("body = P2PConsumer1" + body);

        var obj = new JsonObject();
        obj.put("FROM", "P2PConsumer1");
        message.reply(obj);

      });

  }
}
