package com.priyam.java_vertx_4.crud.student;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/9/21
 */

public class StudentCrudVerticle extends AbstractVerticle {

  private List<Student> students;

  private void populateStudentList() {
    students = new ArrayList<>();

    students.add(new Student("Tahniat", "Ashraf", 32));
    students.add(new Student("Shaheen", "Afridi", 24));

  }

  @Override
  public void start() throws Exception {

    populateStudentList();

    var router = Router.router(vertx);

    router
      .route("/student*")
      .handler(BodyHandler.create());

    router
      .route(HttpMethod.POST, "/studentCreate")
      .handler(this::createStudent);

    router
      .route("/studentList")
      .handler(this::getStudentList);

    router
      .route(HttpMethod.PUT, "/studentUpdate")
      .handler(this::updateStudent);

    router
      .route(HttpMethod.DELETE, "/studentDelete")
      .handler(this::deleteStudent);

    router
      .route(HttpMethod.GET, "/studentGetDetailsByFirstName")
      .handler(this::getDetailsByFirstName);

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(4568);


  }

  private void getDetailsByFirstName(RoutingContext routingContext) {

    var firstName = routingContext.queryParam("firstName").get(0);
    Student s = null;

    for (Student student : students) {
      if (student.getFirstName().equalsIgnoreCase(firstName)) {
        s = student;
      }
    }

    routingContext
      .response()
      .setStatusCode(s==null?404:200)
      .putHeader("content-type", "application/json")
      .end(Json.encodePrettily(s));
  }

  private void deleteStudent(RoutingContext routingContext) {

    var firstName = routingContext.queryParam("firstName").get(0);
    boolean deleted = false;
    for (Student s : students) {
      if (s.getFirstName().equalsIgnoreCase(firstName)) {
        students.remove(s);
        deleted = true;
      }

      routingContext.response()
        .setStatusCode(deleted == true ? 204 : 404)
        .end();

    }
  }

  private void updateStudent(RoutingContext routingContext) {

    var jsonObject = routingContext.getBodyAsJson();
    var student = jsonObject.mapTo(Student.class);

    boolean updated = false;
    for (Student s : students) {
      if (s.getFirstName().equalsIgnoreCase(student.getFirstName())) {
        s.setLastName(student.getLastName());
        s.setAge(student.getAge());
        updated = true;
      }
    }

    routingContext.response()
      .setStatusCode(updated == true ? 204 : 404)
      .putHeader("content-type", "application/json")
      .end(Json.encodePrettily(student));

  }

  private void getStudentList(RoutingContext routingContext) {

    routingContext
      .response()
      .putHeader("content-type", "application/json")
      .end(Json.encodePrettily(students));
  }

  private void createStudent(RoutingContext routingContext) {

    Student newStudent = null;
    try {
      System.out.println("students = " + students);
      JsonObject jsonObject = routingContext.getBodyAsJson();
      System.out.println("jsonObject = " + jsonObject);
//      var bodyAsString = routingContext.getBodyAsString();
//      System.out.println("bodyAsString = " + bodyAsString);
//      newStudent = Json.decodeValue(routingContext.getBodyAsString(), Student.class);
      newStudent = jsonObject.mapTo(Student.class);
      students.add(newStudent);
    } catch (Exception e) {
      e.printStackTrace();
    }


    routingContext
      .response()
      .setStatusCode(201)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(newStudent));
  }
}
