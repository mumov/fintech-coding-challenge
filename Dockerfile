FROM openjdk:11
MAINTAINER Mihhail Umov <mihhail.umov@gmail.com>
ADD target/quotesSystem-1.0.0.jar /usr/share/myservice/myservice.jar
ENTRYPOINT java -jar -Dspring.profiles.active=docker /usr/share/myservice/myservice.jar
