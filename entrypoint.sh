#!/bin/bash

set -e

CERTS_DIR="/app/certs"

echo "Creating certificates directory..."
mkdir -p $CERTS_DIR

echo "Generating RSA key pair..."
openssl genrsa -out $CERTS_DIR/keypair.pem 2048
openssl rsa -in $CERTS_DIR/keypair.pem -pubout -out $CERTS_DIR/public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in $CERTS_DIR/keypair.pem -out $CERTS_DIR/private.pem

rm $CERTS_DIR/keypair.pem

echo "RSA keys generated in $CERTS_DIR:"
ls -la $CERTS_DIR/

exec java -jar target/WebsocketChat-0.0.2-SNAPSHOT.jar
