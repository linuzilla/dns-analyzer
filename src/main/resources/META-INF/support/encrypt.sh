#!/bin/bash

if [ "$1" = "" ]; then
	echo "usage: $0 plainTextPassword"
	exit
fi

source .env

curl -i -u ${account}:${password} http://localhost:8080/apis/misc/encode/$1
