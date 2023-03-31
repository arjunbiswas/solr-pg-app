# Simple Solr Query Service

Simple solr app with postgres

# Infra Service

## Run solr

```
docker run -d -p 8983:8983 --name apache_solr5 solr solr-precreate ex_core5
```

## Download and Run postgres

```
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads 
[No username or password is used from the application to connect ]
```

## Create database

```
CREATE DATABASE predictspring; 
```

## All Services

## Package all Services

```
From root of app folder
mvn -pl indexer,ingester,query package
```

# Ingester Service

## Build the project

```
cd ingester
mvn clean install 
```

## Run the application

```
cd ingester
mvn spring-boot:run
```

## Sample POST request to upload a file

Request

```
curl -XPOST http://localhost:8080/v1/upload -F file=@Product_feed.tsv
```

Response

```
{
  "message" : "Uploaded the file successfully: Product_feed.tsv",
  "fileName" : "Product_feed.tsv",
  "downloadUri" : "/v1/files/401798af-1b13-4d6f-baa2-15d9387ec3da",
  "size" : 81273316,
  "fileReceivedTimeStamp" : "2023-03-27T10:54:06.569+00:00"
}
```

## Sample GET request a file info with file id

Request

```
curl -XGET http://localhost:8080/v1/files/059484a0-b3d6-4c5a-83b4-d859cd42e5ca
```

Response

```
{
  "id": "059484a0-b3d6-4c5a-83b4-d859cd42e5ca",
  "indexed": false,
  "timestamp": "Mar 27, 2023 3:49:42 AM",
  "name": "Product_feed.tsv",
  "type": "application/octet-stream"
}
```

## Sample GET request to fetch file contents file {id}

Request

```
curl -XGET http://localhost:8080/v1/files/059484a0-b3d6-4c5a-83b4-d859cd42e5ca?data=true
```

Response

```
entire tsv file
```

# Indexer Service

## Run the application

```
cd indexer
mvn spring-boot:run
```

# Query Service

## Run the application

```
cd query
mvn spring-boot:run
```

Request

```
curl -XPOST -H "Content-Type: application/json" http://localhost:8082/v1/query -d '{"color":"GREY STUNNER", "size_id":"0","quantity_sold":"0","department_id":"55", "title": "7th Aue Bootcut Pant - Tall"}'
```

Response

```
[ {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "8d6e2897-d29b-43a5-8357-130f80732d08",
  "_version_" : 1761513886944591872
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "bee17efe-2db4-44f2-bbc9-d31d72078d24",
  "_version_" : 1761514279352139776
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "7c8eeaad-d43c-40ec-b55f-a4ef9c525c98",
  "_version_" : 1761515005299130368
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "38a37151-cd08-40a4-b1c3-0489cf0ca677",
  "_version_" : 1761515710560862208
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "7d14040f-b831-40a5-9583-365b4ee614d1",
  "_version_" : 1761516477363519488
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "31bc7000-f380-402f-8a34-ada4f722c2c7",
  "_version_" : 1761517711076098048
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "66e3d5fb-3074-4345-a88f-72cf10755a66",
  "_version_" : 1761518536815017984
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "7bfb9276-f059-497c-9d9f-e92cbc8497e0",
  "_version_" : 1761522093057376257
}, {
  "sku_id" : 55631440,
  "image" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781.jpg",
  "additional_image_link" : "http://images.nyandcompany.com/is/image/NewYorkCompany/productdetaildefault/7th-Avenue-Bootcut-Pant-Tall_04134293_781_av2.jpg",
  "style_id" : 4134293,
  "class_id" : 413,
  "color" : "GREY STUNNER",
  "color_code" : 781,
  "color_family" : "Natural",
  "size_id" : 0,
  "department_id" : 55,
  "dissection_code" : 22,
  "redline" : true,
  "tax_code" : 531015,
  "vendor" : 94399,
  "list_price" : 52.94,
  "currency" : "USD",
  "title" : "7th Avenue Bootcut Pant - Tall",
  "link" : "http://uat.nyandcompany.com/7th-Avenue-Bootcut-Pant-Tall/A-prod33884",
  "prod_description" : "7th Avenue Bootcut Pant - Tall Polished and professional, these bootcut pants enhance your shape with a slimming fit through the hip and thigh. Features front slash pockets and back welt pockets.<br><br><p>overview</p><ol><li>Zip front with hook-and-eye closure.</li><li>Belt loops.</li><li>Front slash pockets with coin pocket..</li><li>Back welt pockets.</li><li>Darts below back waistband.</li><li>Available in Average & Petite.</li></ol><br><p>fit & sizing</p><ol><li>Bootcut.</li><li>Full length.</li><li>Sits just below waist.</li><li>Slimming through hip & thigh.</li><li>Inseam: 34-1/2 inches.</li></ol><br><p>fabric & care</p><ol><li>78% Polyester, 20% Rayon, 2% Spandex.</li><li>Machine wash or Dry clean.</li><li>Imported.</li></ol>",
  "featured_color" : true,
  "featured_color_category" : "true,true,true,true",
  "handling_code" : "F2ABUL13",
  "proportion" : "Tall",
  "proportion_products" : 413429104134292,
  "product_category" : "Petite-Tall-Plus > Tall > All-Tall,Clearance > Redlines-Pants,Petite-Tall-Plus > Tall > Pants,Apparel > Pants > Tall-Pants",
  "sort_order" : 27440202215,
  "quantity_sold" : 0,
  "average_rating" : 4.25,
  "id" : "0210d972-1c87-4f34-8705-aa59b442e459",
  "_version_" : 1761522154222911491
} ]
```
