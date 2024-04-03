# Smartera Project
## Kullanılan Teknolojiler
  ```
  - Spring Boot
  - Spring Data JPA
  - RDBMS (MYSQL)
  - Postman
  - Lombok
  ```

## Proje Özeti
  ```
    - Kullanıcıları, ürünleri ve siparişleri bulunan bir API.
    - Kullanıcı oluşturulurken verilen perm değişkeni, kullanıcının siperiş oluşturup oluşturamama yetkisini belirtmektedir.
    - Kullanıcı oluştururken bu alan 1 veya 0 olarak verilmektedir.
    - Yetkisi olan kullanıcı order oluşturabilmektedir.
    - Oluşturulan order'ın daha sonrasında içerisine ürün girişi yapılabilmektedir.
  ```

## Projeyi Çalıştırma
```
  Projeyi çalıştırmak için doğrudan zip dosyası olarak indirebilirsiniz.
  İndirmiş olduğunuz zip dosyasını klasörden çıkartıp IDE'nizde import ediniz.
  Daha sonrasında proje içerisinde bulunan application.properties dosyasındaki, spring.jpa.hibernate.ddl-auto=none
  komutunu isterseniz create olarak değiştirebilirsiniz.
  Aşağıda bu komutu değiştirmeden veritabanı tablolarını oluşturacağınız SQL komutları verilmiştir.
  Hata almanız durumunda application.properties dosyasında yer alan;
    -spring.datasource.username=tobeto
    -spring.datasource.password=tobeto123!
  bölümlerini veritabanınıza uygun olarak güncelleyin.
  Son olarak Run as Spring Boot Application komutu ile projeyi çalıştırabilirsiniz.
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
