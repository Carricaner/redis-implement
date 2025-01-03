FROM amazoncorretto:17

ARG PROJECT_NAME
ARG VERSION

ENV BUILD_JAR_NAME=${PROJECT_NAME}-${VERSION}.jar

WORKDIR /redis-impl

COPY ./build/libs/$BUILD_JAR_NAME .

RUN chmod +x $BUILD_JAR_NAME

EXPOSE 8081

CMD java -jar "$BUILD_JAR_NAME" --spring.config.location=classpath:/dev/ --spring.profiles.active=dev
