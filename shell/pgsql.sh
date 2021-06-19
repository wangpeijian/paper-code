#!/bin/sh
java -Xmx4096m -Xmx4096m -jar /Users/wangpeijian/work/my/code/paper-code/target/paper-0.0.1-SNAPSHOT.jar --spring.profiles.active=pgsql --server.port=80

lsof -i:8080


java -Xmx4096m -Xmx4096m -jar /home/paper-0.0.1-SNAPSHOT.jar --spring.profiles.active=pgsql --server.port=80