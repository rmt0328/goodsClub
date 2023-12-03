# SpringCloud 项目

## 一、项目功能

### 1、后台管理系统功能

![](http://s5316urxq.bkt.clouddn.com/SPZX/1.png)

### 2、前台用户系统功能

![](http://s5316urxq.bkt.clouddn.com/SPZX/2.png)



## 二、项目技术

### 1、表的关系

#### （1）权限相关的表关系

![](http://s5316urxq.bkt.clouddn.com/SPZX/3.png)

#### （2）商品相关的表关系

- product  商品基本信息表
- product_sku 商品sku表，一个商品包含多个sku
- product_details 商品详情表，商品详情图片



#### （3）订单相关的表关系

- order_info 订单基本信息表
- order_item 订单项表，一个订单里面包含多个订单项



### 2、前端技术

- Element-Admin：Vue3 + Element Plus
- ES6：模板字符串、箭头函数...
- NPM
- Axios



### 3、后端技术

![](http://s5316urxq.bkt.clouddn.com/SPZX/4.png)

![](http://s5316urxq.bkt.clouddn.com/SPZX/5.png)


## 三、快速上手

### Minio图片存储

1）在spzx-manager中的 `application-dev.yml` 中修改如下配置为你自己的，注意账号密码以及ip，我用的是本地的ip：

```
minio:
  endpointUrl: http://127.0.0.1:9000
  accessKey: minioadmin
  secreKey: minioadmin
  bucketName: spzx-bucket
```

2）cmd进入到Monio所在目录

3）启动Minio，输入minio.exe server D:\minio\data，账号密码都是minioadmin

![](http://s5316urxq.bkt.clouddn.com/SPZX/6.png)

### MySQL 数据库

1）在spzx-manager中的 `application-dev.yml` 中修改如下配置为你自己的，注意账号密码以及ip：

```yml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.10.100:3306/db_spzx?characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8
    username: root
    password: root
```

2）执行 `db_spzx.sql` 中的数据库语句，自动创建库表（sql文件已经放在仓库里面了）

### Redis

1）在spzx-manager中的 `application-dev.yml` 中修改如下 Redis 配置为你自己的：

```yml
# Redis的相关配置
data:
  redis:
    host: 192.168.10.100
    port: 6379
```

2）在spzx-server-gateway中的 `application-dev.yml` 中修改如下 Redis 配置为你自己的：

```yml
# Redis的相关配置
data:
  redis:
    host: 192.168.10.100
    port: 6379
```

### Nacos

1）在spzx-server-gateway中的 `application-dev.yml` 中修改如下 nacos配置为你自己的，我用的是本机的，根据实际情况修改即可：

```yml
cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
```

2）启动

进入nacos目录下的bin目录cmd窗口输入**startup.cmd -m standalone**即可

### 注意：

**service-cart，service-order，service-pay，service-product，service-user**模块中的 `application-dev.yml` 中都要修改如下（**mysql，redis，nacos**）配置为你自己的：

```yml
cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
  # 配置数据库连接信息
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.10.100:3306/db_spzx?characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8
    username: root
    password: root
  # Redis的相关配置
  data:
    redis:
      host: 192.168.10.100
      port: 6379
```

如上配置修改完成之后，分别启动如下模块，后端即可正确运行，如果有报错的话，需要针对报错检查排查原因

![](http://s5316urxq.bkt.clouddn.com/SPZX/7.png)