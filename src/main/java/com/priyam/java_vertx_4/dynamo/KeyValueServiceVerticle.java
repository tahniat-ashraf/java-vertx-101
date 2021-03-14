package com.priyam.java_vertx_4.dynamo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * @author Tahniat Ashraf Priyam
 * @since 3/13/21
 */

public class KeyValueServiceVerticle extends AbstractVerticle {

  public static final String GET_LIST_ADDRESS = "GET_LIST_KEY_VAL";
  public static final String SAVE_ADDRESS = "SAVE_KEY_VAL";
  public static final String GET_ADDRESS = "GET_KEY_VAL";
  public static final String DELETE_ADDRESS = "DELETE_KEY_VAL";
  private KeyValueRepository keyValueRepository;
  private DynamoConfiguration dynamoConfiguration;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    dynamoConfiguration = new DynamoConfiguration();
    keyValueRepository = new KeyValueRepository("dev-paybill-key-value", dynamoConfiguration.getDynamoDBEnhancedClient());

    vertx
      .eventBus()
      .consumer(KeyValueServiceVerticle.GET_ADDRESS, this::getKeyValue);

    vertx
      .eventBus()
      .consumer(KeyValueServiceVerticle.GET_LIST_ADDRESS, this::getKeyValues);


    vertx
      .eventBus()
      .consumer(KeyValueServiceVerticle.DELETE_ADDRESS, this::deleteKeyValue);

    vertx
      .eventBus()
      .consumer(KeyValueServiceVerticle.SAVE_ADDRESS, this::saveKeyValue);

  }

  private <T> void saveKeyValue(Message<T> tMessage) {
    var jsonObjectX = (JsonObject) tMessage.body();
    var newKeyValue = jsonObjectX.mapTo(KeyValue.class);
    keyValueRepository.saveKeyValue(newKeyValue)
      .subscribe(() -> tMessage.reply(jsonObjectX),
        Throwable::printStackTrace);
  }

  private <T> void deleteKeyValue(Message<T> tMessage) {
    keyValueRepository.deleteKeyValue(KeyValue.builder().key(tMessage.body().toString()).build())
      .subscribe(keyValue -> {
        var jsonObject = JsonObject.mapFrom(keyValue);
        tMessage.reply(jsonObject);
      });
  }

  private <T> void getKeyValues(Message<T> tMessage) {
    keyValueRepository.getAllKeyValues()
      .items()
      .subscribe(keyValue -> {
        System.out.println("keyValue = " + keyValue);
        var jsonObject = JsonObject.mapFrom(keyValue);
        tMessage.reply(jsonObject);
      });
  }

  private <T> void getKeyValue(Message<T> tMessage) {
    keyValueRepository.getKeyValue(tMessage.body().toString())
      .subscribe(keyValue -> {
        var jsonObject = JsonObject.mapFrom(keyValue);
        tMessage.reply(jsonObject);
      });
  }


}
