run_app() {
  ./gradlew clean build
  docker compose up -d
}