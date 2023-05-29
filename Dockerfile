# 该镜像需要依赖的基础镜像
FROM openjdk:17-oracle
VOLUME /tmp
ADD target/last_stand-3.0.6.jar /last_stand-3.0.6.jar
RUN bash -c 'touch /last_stand-3.0.6.jar'
## 声明服务运行在8010端口
EXPOSE 8010
ENTRYPOINT ["java","-jar","/last_stand-3.0.6.jar"]
# 指定维护者的名字
MAINTAINER Chenhong

