#!/bin/bash

if [ "$1" = "" ]; then
	echo "usage: $0 zone"
	exit
fi

source .env

curl -i -u ${account}:${password} -X POST \
	--header "Content-Type: application/json" \
	--header "Accept: application/json" \
	--header "Accept: text/event-stream" \
	--header "Accept: application/stream+json" \
	http://localhost:8080/apis/push/$1/zone
