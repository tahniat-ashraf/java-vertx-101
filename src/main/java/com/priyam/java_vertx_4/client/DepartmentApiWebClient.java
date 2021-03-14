package com.priyam.java_vertx_4.client;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/11/21
 */

public class DepartmentApiWebClient extends AbstractVerticle {

  private WebClient webClient;

  @Override
  public void start() throws Exception {
    this.webClient = WebClient.create(vertx);
    Router router = Router.router(vertx);


    router
      .route(HttpMethod.GET, "/departments")
      .handler(this::getDepartments);

    router
      .route(HttpMethod.POST, "/departments")
      .handler(this::saveDepartment);

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(4569);
  }

  private void saveDepartment(RoutingContext routingContext) {

    webClient
      .post(3000, "localhost", "/departments")
      .send()
      .onSuccess(response -> {
        var i = response.statusCode();
        System.out.println("statusCode = " + i);
        routingContext.response().end("Deparment has been created successfully!");
      })
      .onFailure(throwable -> {
        throwable.printStackTrace();
      });
  }

  private void getDepartments(RoutingContext routingContext) {

    webClient
      .get(3000, "localhost", "/departments")
      .send()
      .onSuccess(response -> {
        System.out.println("Received response :: http status code :: " + response.statusCode());
        var departments = Arrays.asList(response.bodyAsJson(Department[].class));
        System.out.println("departments = " + departments);
        routingContext.response().end(Json.encodePrettily(departments));
      })
      .onFailure(throwable -> {
        System.out.println("Something went wrong :: " + throwable.getMessage());
      });

  }

  private void getDepartmentById(RoutingContext routingContext) {


    webClient
      .get(3000, "localhost", "/departments")
      .addQueryParam("id", "1")
      .send()
      .onSuccess(response -> {
        var statusCode = response.statusCode();
        System.out.println("statusCode = " + statusCode);
        var department = response.bodyAsJson(Department.class);
        routingContext.response().end(Json.encodePrettily(department));
      })
      .onFailure(throwable -> {
        throwable.printStackTrace();
      });
  }
}
