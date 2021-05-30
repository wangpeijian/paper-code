#!/bin/sh
java -jar ../target/paper-0.0.1-SNAPSHOT.jar --spring.profiles.active=mysql --config.product-zipf=0.9

lsof -i:8080