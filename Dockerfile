FROM openjdk:23-jdk AS BUILD
ADD https://dlcdn.apache.org/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz .
RUN tar xzvf apache-maven-3.9.11-bin.tar.gz -C /opt/ && \
    ln -s /opt/apache-maven-3.9.11 /opt/maven
ENV MAVEN_HOME /opt/maven
ENV PATH $MAVEN_HOME/bin:$PATH
COPY . /build
WORKDIR /build
RUN mvn clean package -Dspring.profiles.active=test

FROM openjdk:23-jdk
ADD https://launchpad.net/ubuntu/'+'archive/primary/'+'sourcefiles/openssl/3.0.8-1ubuntu1.1/openssl_3.0.8.orig.tar.gz .
RUN tar xzvf openssl_3.0.8.orig.tar.gz
WORKDIR /app
COPY --from=BUILD /build/target/WebsocketChat-0.0.2-SNAPSHOT.jar ./target/WebsocketChat-0.0.2-SNAPSHOT.jar
COPY --from=BUILD /build/entrypoint.sh ./target/entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["./target/entrypoint.sh"]