#!/bin/bash

export KAFKA_OPTS="-Djava.security.auth.login.config=/etc/kafka/conf/kafka_client_jaas.conf"
export KAFKA_BIN_DIR="/usr/hdp/current/kafka-broker/bin/"
export SITE_ZOOKEEPER="noeyy0zl.noe.edf.fr:2181,noeyyet7.noe.edf.fr:2181"

TOPICS="spring-surveillance:spring-metrics"
IFS=":"
TOPIC_AR=($TOPICS)

for (( i=0; i<${#TOPIC_AR[@]}; i++ ));
do
        ${KAFKA_BIN_DIR}/kafka-topics.sh --delete --topic ${TOPIC_AR[$i]} --zookeeper ${SITE_ZOOKEEPER}
done;