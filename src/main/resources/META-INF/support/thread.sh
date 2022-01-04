#!/bin/bash

source .env

curl -i -u ${account}:${password} http://localhost:8080/apis/status/thread
