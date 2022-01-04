#!/bin/bash


echo -n "Are you sure you want to re-scoring: [y/N]: "
read ans

if [ "$ans" = "Y" ] || [ "$ans" = "y" ]; then
	source .env

	curl -i -u ${account}:${password} -X POST \
		--header "Content-Type: application/json" \
		--header "Accept: application/json" \
		--header "Accept: text/event-stream" \
		--header "Accept: application/stream+json" \
		http://localhost:8080/apis/scoring
else
	echo "skip."
fi
