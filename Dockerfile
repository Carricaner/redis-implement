# Use Amazon Corretto as the base image
FROM amazoncorretto:17

# Auguments
ARG PROJECT_NAME
ARG VERSION

# Set env
ENV BUILD_JAR_NAME=$PROJECT_NAME-$VERSION

# Set the working directory in the container
WORKDIR /app

# Copy the files
COPY /build/libs/$BUILD_JAR_NAME.jar .

# Grant execution permissions to the Gradle wrapper
RUN chmod +x $BUILD_JAR_NAME.jar

# Specify the command to run the application
CMD java -jar /app/$BUILD_JAR_NAME.jar
