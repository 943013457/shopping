create table login(
	user varchar(30) not null,
	password varchar(100) not null,
	registertime datetime,
	register_ip varchar(50),
	phone varchar(30),
	email varchar(50),
	state tinyint not null,
	salt varchar(100),
	primary key(user)
);
create table u_role(
	role_id int not null auto_increment,
	role_name varchar(15),
	primary key(role_id)
);
create table u_permission(
	permission_id int auto_increment,
	permission_name varchar(15),
	primary key(permission_id)
);
create table role_permission(
	role_id int,
	permission_id int,
	CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES u_role(role_id),
	CONSTRAINT permission_fk FOREIGN KEY (permission_id) REFERENCES u_permission(permission_id)
);
-- 商品分类
CREATE TABLE category (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(64) not null,
  PRIMARY KEY (id)
);
-- 每种商品属性
CREATE TABLE property (
  id int NOT NULL AUTO_INCREMENT,
  category_id int,
  name varchar(255),
  PRIMARY KEY (id),
  CONSTRAINT fk_property_category FOREIGN KEY (category_id) REFERENCES category (id)
);
-- 产品表
create table product(
	id int not null auto_increment,
	name varchar(128),
	subTitle varchar(255),
	originalPrice float,
	promotePrice float,
	stock int,
	sales int,
	category_id int,
	createTime datetime,
	primary key(id),
	CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category (id)
);
-- 商品属性值
CREATE TABLE property_value (
  id int NOT NULL AUTO_INCREMENT,
  property_id int,
  product_id int,
  value varchar(255),
  PRIMARY KEY (id),
  CONSTRAINT fk_propertyvalue_property FOREIGN KEY (property_id) REFERENCES property (id),
  CONSTRAINT fk_propertyvalue_product FOREIGN KEY (product_id) REFERENCES product (id)
);
-- 商品图片
CREATE TABLE product_image (
  id int NOT NULL AUTO_INCREMENT,
  product_id int,
  image varchar(255),
  location varchar(10),
  PRIMARY KEY (id),
  CONSTRAINT fk_productimage_product FOREIGN KEY (product_id) REFERENCES product (id)
);
-- 商品评价
CREATE TABLE review (
  id int NOT NULL AUTO_INCREMENT,
  content varchar(2048) ,
  username varchar(30),
  product_id int,
  createDate datetime,
  PRIMARY KEY (id),
  CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT fk_review_user FOREIGN KEY (username) REFERENCES login (user)
);
-- 订单表
CREATE TABLE order_table (
  id varchar(100) NOT NULL,
  address varchar(255),
  receiver varchar(255),
  phone varchar(50),
  userMessage varchar(255),
  createDate datetime,
  payDate datetime,
  deliveryDate datetime,
  confirmDate datetime,
  username varchar(30),
  state varchar(64),
  PRIMARY KEY (id),
  CONSTRAINT fk_order_user FOREIGN KEY (username) REFERENCES login (user)
);
-- 我的订单
CREATE TABLE order_item (
order_id varchar(100),
  username varchar(30) NOT NULL,
  product_id int,
  number int,
  PRIMARY KEY (order_id),
  CONSTRAINT fk_orderitem_user FOREIGN KEY (username) REFERENCES login (user),
  CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES product (id),
  CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES order_table (id)
);

--购物车
create table trolley(
	user varchar(30) not null,
	product_id int not null,
	number int not null,
  CONSTRAINT fk_trolley_login FOREIGN KEY (user) REFERENCES login (user),
  CONSTRAINT fk_trolley_product FOREIGN KEY (product_id) REFERENCES product (id)
)