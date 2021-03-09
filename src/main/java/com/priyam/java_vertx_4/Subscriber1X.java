package com.priyam.java_vertx_4;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

/**
 * @author Tahniat Ashraf Priyam
 * @since 2/26/21
 */

public class Subscriber1X extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    vertx.eventBus()
      .consumer(PublisherX.ADDRESS, message -> {

        var body = message.body();
        System.out.println("body = Subscriber1" + body);

        var obj = new JsonObject();
        obj.put("FROM", "Subscriber1");
        message.reply(obj);

      });

  }
}
