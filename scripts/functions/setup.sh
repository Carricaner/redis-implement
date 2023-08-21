checked="\xE2\x9C\x85"  # ✅
bell="\xF0\x9F\x94\x94" # 🔔

setup_env() {
  # To check if the docker network exist
  if docker network ls | grep -q "redis-network"; then
    echo "[INFO] ${checked} The docker network exists"
  else
    docker network create redis-network
    echo "[INFO] ${bell} The docker network created!"
  fi
}
