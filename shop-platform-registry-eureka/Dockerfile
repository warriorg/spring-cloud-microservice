FROM openjdk:17-alpine

MAINTAINER warriorg

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS="" \
    PORT=8761 \
    CONFIG_PORT=8888 \
    PROFILES="default"

ADD /build/libs/*.jar /registry.jar

RUN apk update && apk add curl && rm -rf /var/cache/apk/*

HEALTHCHECK --interval=5s --timeout=30s CMD curl -f http://localhost:$PORT/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /registry.jar --spring.profiles.active=$PROFILES"]

EXPOSE $PORT