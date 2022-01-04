#!/bin/bash

if [ "$1" = "" ] || [ "$2" = "" ]; then
	echo "usage: $0 zone server"
	exit
fi

source .env

echo "{\"zone\":\"$1\", \"server\":\"$2\"}"

curl -i -u ${account}:${password} -X POST \
	--header "Content-Type: application/json" \
	--header "Accept: application/json" \
	--header "Accept: application/stream+json" \
	-d "{\"zone\":\"$1\", \"server\":\"$2\"}" http://localhost:8080/apis/edns/analyze
