//package com.priyam.java_vertx_4;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Promise;
//import io.vertx.core.json.JsonObject;
//
///**
// * @author Tahniat Ashraf Priyam
// * @since 2/26/21
// */
//
//public class P2PSenderX extends AbstractVerticle {
//
//  public static final String ADDRESS = "P2PSenderX";
//
//  @Override
//  public void start(Promise<Void> startPromise) throws Exception {
//
//
//    vertx.createHttpServer()
//      .requestHandler(httpServerRequest -> {
//
//        var obj = new JsonObject();
//        obj.put("FROM", "P2PSender");
//
//        vertx.eventBus()
//          .request(P2PSenderX.ADDRESS, obj, messageAsyncResult -> {
//
//            var result = messageAsyncResult.result();
//            System.out.println("result :: P2PSender= " + result.body());
//          });
//
////        httpServerRequest.response()
////          .end("Published obj " + obj);
//
//      }).listen(8082);
//  }
//}
