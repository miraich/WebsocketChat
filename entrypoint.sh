#!/bin/bash
set -e

echo "Generating RSA key pair..."
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
echo "RSA keys generated."

exec java -jar target/WebsocketChat-0.0.1-SNAPSHOT.jar
