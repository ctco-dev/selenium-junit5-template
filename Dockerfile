FROM maven

RUN mkdir -p /opt/app
WORKDIR /opt/app

ADD pom.xml /opt/app/

COPY pom.xml .
COPY src src/

RUN mvn verify -DskipTests

CMD mvn test -P $PROFILE