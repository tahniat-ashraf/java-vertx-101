package com.priyam.java_vertx_4.dynamo;

import io.reactivex.Observable;
import io.reactivex.Single;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;


/**
 * @author Tahniat Ashraf Priyam
 * @since 3/13/21
 */

public class KeyValueRepository extends AbstractDynamoRepository<KeyValue> {


  public KeyValueRepository(String tableName, DynamoDbEnhancedAsyncClient dynamoDbEnhancedClient) {
    super(tableName, dynamoDbEnhancedClient, KeyValue.class);

  }

  public Observable<KeyValue> getAllKeyValues() {

    return Observable.fromPublisher(findAllItems().items());

  }

  public Observable<KeyValue> saveKeyValue(KeyValue keyValue) {
    return Observable.fromFuture(saveItem(keyValue))
      .map(aVoid -> keyValue);
  }

  public Single<KeyValue> getKeyValue(String key) {

    return Single.fromFuture(getItemWithPartitionKey(key));
  }

}

