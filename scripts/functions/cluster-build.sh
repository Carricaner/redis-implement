checked="\xE2\x9C\x85"  # ✅
bell="\xF0\x9F\x94\x94" # 🔔

build_redis_cluster() {
  if docker ps | grep -q "redis-cluster-"; then
    echo "[INFO] ${checked} The Redis cluster is running~"
  else
    cd ./redis-cluster
    sh start.sh
    cd ..
    echo "[INFO] ${bell} A Redis cluster created!"
  fi
}
