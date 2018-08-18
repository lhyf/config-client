# 自定义配置中心客户端程序

**maven坐标**

```
    <groupId>org.lhyf</groupId>
    <artifactId>config-client</artifactId>
    <version>1.0-beta</version>
```


**使用方式**

1. 搭建[配置中心服务端](https://github.com/lhyf/config-admin)环境,并在服务端加入配置
2. 在需要使用配置中心的项目中导入客户端依赖
3. 在项目yml配置文件中,配置客户端
```$xslt
lhyf:
  config:
    appid: appid
    environment: dev
    namespace: common,mysql,redis,other
    request:
      prefix: http://ip:prot/api/config

```

其中
1. appid 为管理平台中配置的appid(项目id)
2. environment 为当前的开发环境
3. namespace 为项目中配置的逻辑分组,类似于配置文件.mysql一个单独的配置文件,
Redis一个单独的配置文件等等
4. prefix 配置中心服务器端请求地址

**说明**
1. 通过客户端获取: 获取配置将会从指定的appi下的指定环境下的,指定namespace下获取,若namespace没有配置,则直接获取指定appid下的,指定环境下的所有namespace下的配置
2. 通过 url 获取(主要针对于无法使用java客户端的项目,即可以通过请求连接获取配置项)
 ①. 获取某个namespace下的配置: http://ip:prot/api/config/appid/environment/namespace
 ②. 获取环境下所有namespace 的配置 http://ip:prot/api/config/appid/environment
返回格式为包含各个配置项形式的json串