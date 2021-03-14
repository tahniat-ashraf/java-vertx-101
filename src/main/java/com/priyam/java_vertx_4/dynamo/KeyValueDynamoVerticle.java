package com.priyam.java_vertx_4.dynamo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/13/21
 */

public class KeyValueDynamoVerticle extends AbstractVerticle {

  public static final String GET_LIST_ADDRESS = "GET_LIST_KEY_VAL";
  public static final String SAVE_ADDRESS = "SAVE_KEY_VAL";
  public static final String GET_ADDRESS = "GET_KEY_VAL";
  private KeyValueRepository keyValueRepository;
  private DynamoConfiguration dynamoConfiguration;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("start()");
    dynamoConfiguration = new DynamoConfiguration();
    keyValueRepository = new KeyValueRepository("dev-paybill-key-value", dynamoConfiguration.getDynamoDBEnhancedClient());

    System.out.println("inside keyValDynamoRepo :: init :: complete");


    try {
      vertx
        .eventBus()
        .consumer(KeyValueDynamoVerticle.GET_LIST_ADDRESS, message -> {

          System.out.println("start() call landed inside keyValDynamoRepo ");

          keyValueRepository.getKeyValue("PAYBILL_BKASH_LOGO")
            .subscribe(keyValue -> {
              System.out.println("keyValue = " + keyValue);
              JsonObject jsonObject=new JsonObject();
              jsonObject.put("keyValue", Json.encodePrettily(keyValue));
              message.reply(jsonObject);
            });



        });
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }


}
