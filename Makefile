ConsumerIMG ?= build-harbor.alauda.cn/test/asm/spring-nacos-demo-consumer:changename
ProviderIMG ?= build-harbor.alauda.cn/test/asm/spring-nacos-demo-provider:changename
SentinelIMG ?= build-harbor.alauda.cn/test/asm/spring-nacos-demo-dashboard:latest
ConsumerPath=./consumer-example
ProviderPath=./provider-example
DashboardPath=./sentinel-dashboard



build-consumer:
	pushd consumer-example && mvn clean package && popd
	docker buildx build -f ${ConsumerPath}/Dockerfile --progress auto --push --platform=linux/amd64,linux/arm64 --build-arg JAR_FILE=target/*.jar --build-arg image_registry=build-harbor.alauda.cn/asm --builder=mybuilder -t ${ConsumerIMG} ${ConsumerPath}


build-provider:
	pushd provider-example && mvn clean package && popd
	docker buildx build -f ${ProviderPath}/Dockerfile --progress auto --push --platform=linux/amd64,linux/arm64 --build-arg JAR_FILE=target/*.jar --build-arg image_registry=build-harbor.alauda.cn/asm --builder=mybuilder -t ${ProviderIMG} ${ProviderPath}

build-dashboard:
	docker buildx build -f ${DashboardPath}/Dockerfile --progress auto --push --platform=linux/amd64,linux/arm64 --build-arg image_registry=build-harbor.alauda.cn/asm --builder=mybuilder -t ${SentinelIMG} ${DashboardPath}

