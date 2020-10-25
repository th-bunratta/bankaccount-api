# Author's name: Tharathorn Bunrattanasathian
# Student ID: 6110546011

FROM maven:3.6-jdk-8
WORKDIR /usr/bankaccount-api
COPY pom.xml .
RUN mvn dependency:resolve
COPY src/main ./src/main
RUN mvn compile
EXPOSE 8091
CMD ["mvn", "spring-boot:run"]
