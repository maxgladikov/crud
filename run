#!/bin/bash

./gradlew clean && ./gradlew war
cp /home/max/dev/java/aston_hw2/crud/build/libs/crud.war /home/max/dev/java/aston_hw2/webapps/
docker compose -f "/home/max/dev/java/aston_hw2/docker-compose.yml" up
