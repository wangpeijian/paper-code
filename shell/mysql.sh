#!/bin/sh
java -Xms1024m -Xmx1024m -jar /Users/wangpeijian/work/my/code/paper-code/target/paper-0.0.1-SNAPSHOT.jar --spring.profiles.active=mysql --config.product-zipf=0.6

lsof -i:8080