#!/bin/bash

export KAFKA_OPTS="-Djava.security.auth.login.config=/etc/kafka/conf/kafka_client_jaas.conf"
export KAFKA_BIN_DIR="/usr/hdp/current/kafka-broker/bin/"
export SITE_ZOOKEEPER="noeyy0zl.noe.edf.fr:2181,noeyyet7.noe.edf.fr:2181"

${KAFKA_BIN_DIR}/kafka-topics.sh --list --zookeeper ${SITE_ZOOKEEPER}