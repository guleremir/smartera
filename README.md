# Smartera Project
## Kullanılan Teknolojiler
  ```
  - Spring Boot
  - Spring Data JPA
  - RDBMS (MYSQL)
  - Postman
  - Lombok
  ```

## SQL Tabloları İçin
  ```
  CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
  ```
  ```
CREATE TABLE order_products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
  ```
  ```
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE
);
  ```
  ```
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    perm INT
);

  ```
## Application.properties Ayarları
  ```
    spring.datasource.username=tobeto
    spring.datasource.password=tobeto123!
  ```
  ```
    MySQL Workbench'de tanımlı olan tobeto isimli user tüm yetkilere sahiptir.
  ```
