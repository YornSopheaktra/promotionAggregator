FROM openjdk:8-slim
MAINTAINER kimchheng <kimchheng.kao@ascendcorp.com>
COPY @project.build.finalName@.jar @project.build.finalName@.jar
#ENV VAULT_HOST=http://192.168.168.112:8200
#ENV VAULT_TOKEN=b9a8acd9-9599-d238-be25-a82be0d48c96
#ENV REDIS_HOST=192.168.168.112
#VOLUME /opt/tomcat/logs
ENV TZ=Asia/Phnom_Penh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#RUN mkdir -p /opt/tomcat/logs && chmod 777 /opt/tomcat/logs
LABEL release-date="@timestamp@"
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/@project.build.finalName@.jar"]
