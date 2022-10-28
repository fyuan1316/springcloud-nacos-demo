FROM build-harbor.alauda.cn/ops/openjdk:8-ubuntu21.10
VOLUME /tmp
#ARG AGENT_FILE=agent
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#COPY ${AGENT_FILE} agent
#ENTRYPOINT ["java","-javaagent:agent.jar","-Dfemas_namespace_id=ns-zka5j75d","-Dfemas_group_id=v2.0.12","-Dskywalking.agent.service_name=femas-springcloud-consumer","-Dskywalking.collector.backend_service=10.6.128.6:11800","-jar","/app.jar"]
