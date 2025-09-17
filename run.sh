#!/usr/bin/env bash
set -euo pipefail

# --- Taşınabilir milisaniye zamanı ---
now_ms() {
  if command -v gdate >/dev/null 2>&1; then
    # coreutils date (brew install coreutils)
    gdate +%s%3N
  else
    # macOS/BSD date %N desteklemez -> python3 ile ölç
    python3 - <<'PY'
import time
print(int(time.time() * 1000))
PY
  fi
}

# Başlangıç zamanı (ms)
START_MS="$(now_ms)"
DURATION_MS=0

# Çıktıları temizle
: > success.txt
: > error.txt

BASE_URL="http://localhost:8085/api/financial-summary"
CONCURRENCY=1000
TOTAL=1000   # 1..TOTAL arası

# xargs içindeki tekil hatalar tüm scripti düşürmesin, süre her durumda yazılsın
set +e
seq 1 "$TOTAL" | xargs -n1 -I{} -P"$CONCURRENCY" bash -c '
id="$1"
url="'"$BASE_URL"'/$id"

body_file="$(mktemp)"
err_file="$(mktemp)"
http_code=""   # ön tanım

# HTTP isteği (TOPLAM timeout 60 sn)
http_code="$(curl -sS --max-time 60 -o "$body_file" -w "%{http_code}" "$url" 2>"$err_file")"
curl_exit=$?

if [ $curl_exit -ne 0 ]; then
  {
    printf "%s\t%s\tcurl_exit=%d\t" "$id" "$url" "$curl_exit"
    if [ -s "$err_file" ]; then cat "$err_file"; elif [ -s "$body_file" ]; then cat "$body_file"; fi
    echo
  } >> error.txt
  rm -f "$body_file" "$err_file"
  exit 0
fi

if [[ "$http_code" =~ ^2[0-9][0-9]$ ]]; then
  {
    printf "%s\t%s\t%s\t" "$id" "$url" "$http_code"
    cat "$body_file"
    echo
  } >> success.txt
else
  {
    printf "%s\t%s\t%s\t" "$id" "$url" "$http_code"
    cat "$body_file"
    echo
  } >> error.txt
fi

rm -f "$body_file" "$err_file"
' bash {}
set -e

# Bitiş zamanı ve süre
END_MS="$(now_ms)"
DURATION_MS=$(( END_MS - START_MS ))

# Saniye cinsinden yaz (noktalı biçim)
awk -v ms="$DURATION_MS" 'BEGIN { printf "Tüm istekler tamamlandı. Geçen süre: %.3f saniye\n", ms/1000 }'
