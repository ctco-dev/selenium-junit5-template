FROM maven

RUN mkdir -p /opt/app
WORKDIR /opt/app

ADD pom.xml /opt/app/
ADD settings.xml /root/.m2/settings.xml

COPY settings.xml /usr/share/maven/ref/
COPY pom.xml .
COPY settings.xml .
COPY src src/

RUN mvn verify -DskipTests

CMD mvn test -P $PROFILE -D qa.password=$PASS -D qa.user=$USER