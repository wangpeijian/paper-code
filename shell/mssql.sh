#!/bin/sh

lsof -i:80

nohup java -Xmx4096m -Xmx4096m -jar /home/paper-0.0.1-SNAPSHOT.jar --spring.profiles.active=mssql --server.port=80 &