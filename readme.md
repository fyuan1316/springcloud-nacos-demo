# springcloud-nacos-demo

## nacos

nacos 版本要求 2.0+

本地模式启动 nacos 默认端口为 8848

```bash
# nacos bin 目录下执行
bash startup.sh -p embedded -m standalone

```
## demo 启动
本地启动 provider (18082) 和 consumer (18083)

```bash
cd provider-example && mvn clean spring-boot:run

cd consumer-example && mvn clean spring-boot:run 

# 指定启动端口
mvn spring-boot:run -Dspring-boot.run.arguments=--port=11000
```

## nacos 配置设置

namespace: public

group: DEFAULT_GROUP

### Data-Id: application.yaml

```yaml
feign: 
  client: 
    config: 
      default: 
        connectTimeout: 2000 # 连接超时
        readTimeout: 1500    # 超时
    refresh-enabled: true    # 开启后可以动态更新参数，默认不开启
  sentinel: 
    enabled: true            # 开关
server: 
  port: 18084
spring: 
  main: 
    allow-circular-references: true  # Fix spring-boot 2.6+

```


### Data-Id: service-consumer-flowrule

```json
[{
	"resource": "GET:http://service-provider/echo/{str}",
	"controlBehavior": 0,
	"count": 3,
	"grade": 1,
	"limitApp": "default",
	"strategy": 0
}]
```


### Data-Id: service-consumer-degraderule

```json
[{
		"resource": "GET:http://service-provider/test",
		"count": 0.5,
		"grade": 1,
		"timeWindow": 30
	},
	{
		"resource": "GET:http://service-provider",
		"count": 0.5,
		"grade": 1,
		"timeWindow": 10
	},
	{
		"resource": "GET:http://service-provider/sleep",
		"count": 20.0,
		"grade": 0,
		"timeWindow": 30
	},
	{
		"resource": "GET:http://service-provider/divide",
		"count": 0.5,
		"grade": 1,
		"timeWindow": 30
	}
]
```