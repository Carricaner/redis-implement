checked="\xE2\x9C\x85"  # ✅
bell="\xF0\x9F\x94\x94" # 🔔

setup_env() {
  # Create an .env file
  envPath="$PWD/.env"
  if [ -f "$envPath" ]; then
    echo "[INFO] ${checked} $envPath exists."
  else
    cp "$PWD/template.env" "$envPath"
    echo "[INFO] ${bell} $envPath created!."
  fi

  # To check if the docker network exist
  if docker network ls | grep -q "redis-network"; then
    echo "[INFO] ${checked} The docker network exists"
  else
    docker network create redis-network
    echo "[INFO] ${bell} The docker network created!"
  fi
}
