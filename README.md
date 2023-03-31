# Simple Solr Query Service
Simple solr querying app with some data and postgres

# Infra Services

## Run solr 
```
docker run -d -p 8983:8983 --name demo_solar solr solr-precreate demo
```

## Download and Run postgres 
```
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads 
[No username or password is used from the application to connect ]
```

## Create database
```
Log into postgres database and create database with command

CREATE DATABASE predictspring; 
```
# Application Services

## Package all Application Services
```
From root of app folder
mvn -pl indexer,ingester,query package
```


## Ingester Service

### Build the project

```
cd ingester
mvn clean install 
```

### Run the application

```
cd ingester
mvn spring-boot:run
```

### Sample POST request to upload a file
Sample Data
```
data/Product_feed.tsv
```

Request
```
curl -XPOST http://localhost:8080/v1/upload -F file=@Product_feed.tsv
```
Response
```
{
  "message" : "Uploaded the file successfully: Product_feed.tsv",
  "fileName" : "Product_feed.tsv",
  "downloadUri" : "/v1/files/e16f0daf-75b1-48a5-bbc1-af1a47e8a6ed",
  "size" : 81273316,
  "fileReceivedTimeStamp" : "2023-03-31T23:50:36.650+00:00"
}
```

### Sample GET request a file info with file id

Request
```
curl -XGET http://localhost:8080/v1/files/e16f0daf-75b1-48a5-bbc1-af1a47e8a6ed
```
Response
```
{
  "id": "e16f0daf-75b1-48a5-bbc1-af1a47e8a6ed",
  "indexed": false,
  "timestamp": "Mar 31, 2023 4:50:36 PM",
  "name": "Product_feed.tsv",
  "type": "application/octet-stream"
}
```

### Sample GET request to fetch file contents file {id}

Request
```
curl -XGET http://localhost:8080/v1/files/059484a0-b3d6-4c5a-83b4-d859cd42e5ca?data=true
```
Response
```
entire tsv file
```

## Indexer Service

### Run the application
```
cd indexer
mvn spring-boot:run
```

## Query Service

### Run the application
```
cd query
mvn spring-boot:run
```
Request
```
curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"sku_id":"55609029"}'
```
Response
```
[ {
  "sku_id" : "55609029",
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_006.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : "04134293",
  "class_id" : "0413",
  "color" : "RINSE",
  "color_code" : "006",
  "color_family" : "GREYISH",
  "size" : "0",
  "size_id" : "0000",
  "department_id" : "055",
  "dissection_code" : 45,
  "redline" : "true",
  "tax_code" : "531015",
  "vendor" : "94399",
  "list_price" : "52.94",
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color_category" : "false,false,false,false",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : "04134291,04134292",
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : "274,40,202,215",
  "quantity_sold" : "0",
  "average_rating" : "4.25",
  "id" : "2a856df5-974a-4356-9dc5-cc995ddaf253",
  "_version_" : 1761921777251909632
} ]
```

```
Sample Queries
```
```
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"size":"12","color":"GREY STUNNER"}' && echo "done1" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"color":"GREY STUNNER", "size_id":"0","department_id":"55", "title": "7th Aue Bootcut Pant - Tall"}' && echo "done" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"color":"GREY STUNNER", "size_id":"0","department_id":"55"}' && echo "done" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"color":"GREY STUNNER","sku_id":"55609029"}' && echo "done" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"sku_id":"55609029"}' && echo "done" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"size":"12"}' && echo "done2" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"color":"GREY STUNNER"}' && echo "done3" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"color":"RINSE"}' && echo "done4" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"handling_code":"F2ABUL13"}' && echo "done4" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"title":"7th Avenue Bootcut Pant - Tall"}' && echo "done4" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"redline":"TRUE","color":"GREY STUNNER"}' && echo "done6" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"sku_id":"55609032","color":"GREY STUNNER"}' && echo "done7" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"sku_id":"55609032","color":"GREYISH"}' && echo "done7" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"sku_id":"55609032","handling_code":"F2ABUL13"}' && echo "done9" &
  #curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"handling_code":"XXXXXX"}' && echo "done9" &
```
