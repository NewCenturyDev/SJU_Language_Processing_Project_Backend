FROM python:3.10-slim
ARG JAR_FILE=target/*.jar

RUN apt-get update && apt-get install -y openjdk-17-jdk openjdk-17-jre
RUN java --version
ENV JAVA_HOME /opt/jdk17
ENV PATH=$PATH:$JAVA_HOME/bin
ENV JAVA_VERSION=17.0.1

COPY ./requirements.txt requirements.txt
RUN pip3 install --upgrade pip
RUN pip3 install -r /requirements.txt

COPY build/libs/SJU_Language_Processing-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
