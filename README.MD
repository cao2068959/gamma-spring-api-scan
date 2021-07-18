# 1. 简介

本项目是基于`gamma`的一个插件

[gamma传送门](https://github.com/cao2068959/gamma)

本项目是用来扫描一个spring的项目中有多少个`Api`接口，然后收集
这个`API`接口的`入参`/`出参`信息统计后通过`http`发送给后端服务

## 1.1 使用场景

在微服务背景下 服务之间有大量的`RPC`，那么当下游服务接口更变后没有及时通知
到对应的上游服务，就会导致上游接口报错

那么可以设计一个系统来记录所有服务接口的变更记录，如果发现有服务的接口发生了变更，将会自动通知对应上游服务
的负责人

那么基于上述场景，本插件的作用就是用于采集接口信息，然后把采集到的信息发送给对应的服务端。


# 2 架构设计

本插件将会扫描对应项目里所有的class文件，然后查找对应的`springMVC`的接口注解
`@RequestMapping` / `@GetMapping` / `@PostMapping` ...... 等来判断这是不是一个接口

同时，也会去检查对应打了注解的方法的`入参`、`返回值`的数据结构状况，来生成一个唯一的hash值，用于标记对应接口是否变更过

这里可以正确解析对应的包装类`Optional<User>` 以及 `List<User>`这样的包装类，会去检查`User`对象的数据结构，而不是外包装类型

当接口`入参`、`返回值` 新增、删除、修改字段名称 对应的`hash`值才会改变，而调整接口字段顺序是不会更变`hash`值的
如:

```java

public class User {
    String name;
    int age;
}

```

```java
public class User {
    int age;
    String name;
}
```
上述改变将不会更变hash值


# 3. 快速使用

在`/dist`目录下有编译后的插件

或者`clone`仓库后执行
```shell script
mvn clean package
```
后生成对应的插件

放入`gamma`中执行命令 ([gamma使用方式](https://github.com/cao2068959/gamma))
```shell script
java -jar gamma-bootstrap.jar source=你要扫描的jar路径
```
# 4. 参数配置
提供以下参数

```java
endpoint.topology.host=收集结束后发送给的服务域名(必填)
ref=本次采集的项目分支(必填)
commitId=本次采集的项目的commitId(必填)
appName=应用名称(必填)
```


# 5.嵌入式执行
如果不想使用插件这么麻烦，那么可以使用`gamma`的嵌入执行模式来执行

需要先安装`gamma-embed`模块
```java
    @org.junit.Test
    public void test() {
        Map<String, String> config = new HashMap<>();
        config.put("endpoint.topology.host","127.0.0.1:8080");
        config.put("ref","master");
        config.put("commitId","1234567");
        config.put("appName","1234567");
        config.put("scan.package","com.chy,com.chy2,com.example");

        GammaContainer gammaContainer =
                new GammaContainer("你要扫描的jar路径", config);
        gammaContainer.start(new SpringApiScanProcession());
    }
```

# 6. 接收端
当采集完成后将会发送请求 `post ${endpoint.topology.host}/api/signature` 到服务端

### requestBody
以json的形式发送
```java
@Data
@Builder
public class ProjectApiSignatureFrom {

    String commitId; 
    String appName;
    String ref;
    List<ApiSignatureFrom> apiSignature;

}
```
这里 `ApiSignatureFrom` 中存放了所有接口的信息
```java
@Data
@Builder
public class ApiSignatureFrom {

    String methodType;
    String url;
    String signature;
}
```

这里的 `signature`就将是接口的签名了， 是一个 `32`位的hash值, 如果接口的`入参`/`返回值`改变这里将会改变