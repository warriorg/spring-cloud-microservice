FROM openjdk:17-alpine

MAINTAINER warriorg

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS="" \
    PORT=8401 \
    CONFIG_PORT=8888 \
    AUTH_PORT=8301 \
    PROFILES="default"

ADD /build/libs/*.jar /shop-coupon-template.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /shop-coupon-template.jar --spring.profiles.active=$PROFILES"]

EXPOSE $PORT