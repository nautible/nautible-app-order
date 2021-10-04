# 動作確認用テストデータ
## 注文作成
```
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{ \"orderDate\": \"2021-01-01\", \"orderPrice\": \"10000\", \"delivery\": { \"price\": \"1000\",\"deliveryNumber\": \"0000000001\", \"planDate\": \"2021-01-01\", \"name\": \"hoe\",\"postCode\": \"123-4567\", \"address1\": \"tokyo\", \"address2\": \"W100\", \"tel\": \"03-1111-2222\"  }, \"payment\": { \"id\": \"1\", \"paymentType\": \"CREDIT\" }, \"products\": [{ \"id\": \"1\", \"price\": \"100\",\"count\": \"10\"},{ \"id\": \"2\", \"price\": \"200\",\"count\": \"20\"}]}" http://localhost:8080/order/
```

## 検索

```
curl -H "Accept: application/json" -X GET http://localhost:8080/order/findByCustomerId/1/
```

## ID指定データ取得

```
curl -H "Accept: application/json" -X GET http://localhost:8080/order/O0000000001/
```

## データ削除

```
curl -H "Accept: application/json" -X DELETE http://localhost:8080/order/O0000000001/
```

## Dynamodb（localstack)のデータ確認

- 事前にプロファイルを作成する「/.aws/config」

```
[profile localstack]
region = ap-northeast-1
output = json
```
- 事前にプロファイルを作成する「/.aws/credentials」

```
[localstack]
aws_access_key_id = test-key
aws_secret_access_key = test-secret
```

- Orderテーブルの取得

```
aws dynamodb scan --table-name=Order --profile localstack --endpoint-url=http://localhost:4566
```

## Cosmondb(Monogodb)のデータ確認

- http://localhost:8081/ にアクセス
