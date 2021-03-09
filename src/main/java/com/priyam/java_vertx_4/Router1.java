package com.priyam.java_vertx_4;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/8/21
 */

public class Router1 extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router= Router.router(vertx);

    router
      .route("/shorno/:age/:fname")
      .handler(routingContext -> {

        var age = routingContext.pathParam("age");
        System.out.println("age = " + age);
        var fname=routingContext.pathParam("fname");
        System.out.println("fname = " + fname);
        var spouse = routingContext.queryParam("spouse");
        System.out.println("spouse = " + spouse);

        routingContext.response().end("age : "+age+"; fname : "+fname);
      });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(4567);
  }

}
