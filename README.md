# ShopDemo API

A Spring boot API for my demo project.

## Installation

Open with IntelliJ IDEA as Gradle project and hit the "Run" button.

H2 DataBase and default script is already configured and ready to run.

## API Usage

The REST API to the Shop Demo app is described below.

All API are start with

`http://localhost:8080/api/`

## Account
### Owner Account: `admin` - `123567`
### Owner Account: `customer1` - `123456`

### Sign up

`POST`

     http://localhost:8080/api/signup/
     Request body: {username , password , role}

     Role will be Customer by default

### Log in

`POST`

     http://localhost:8080/api/login/
     Request body: {username , password}

## Items

### Get list of Items

`GET`

     http://localhost:8080/api/items/

### Get Item by Id

`GET`

     http://localhost:8080/api/items/{id}

### Search Item by name

`GET`

     http://localhost:8080/api/items/search/
     Request param: search : string

### Create a new Item

`POST`

     http://localhost:8080/api/items/
     Request body: ItemDto

### Update an Item

`PUT`

     http://localhost:8080/api/items/
     Request Body: ItemDto

### Delete Item

`DELETE`

     http://localhost:8080/api/items/{id}

## Carts

### Get Cart by Customer

`GET`

     http://localhost:8080/api/carts/{id}

### Add Cart Detail

`POST`

     http://localhost:8080/api/carts/
     Request body: {customerId, itemId, quantity}

### Update Cart Detail

`PUT`

     http://localhost:8080/api/carts/
     Request body: {cartDetailId, quantity}

### Delete Cart Detail

`DELETE`

     http://localhost:8080/api/carts/{id}

## Orders

### Get list of Orders

`GET`

     http://localhost:8080/api/orders/

### Get Orders by Customer ID

`GET`

     http://localhost:8080/api/orders/{id}

### Create Order by Customer ID

`POST`

     http://localhost:8080/api/orders/
     Request body: customerId : int

### Get latest Order of Customer

`GET`

     http://localhost:8080/api/orders/latestOrder/{customerId}

### Confirm Order

`PUT`

     http://localhost:8080/api/orders/
     Request body: OrderId : int
