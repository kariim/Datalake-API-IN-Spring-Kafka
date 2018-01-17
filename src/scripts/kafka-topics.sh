#!/bin/bash

export KAFKA_OPTS="-Djava.security.auth.login.config=/etc/kafka/conf/kafka_client_jaas.conf"
export KAFKA_BIN_DIR="/usr/hdp/current/kafka-broker/bin/"

zookeeper_urls="noeyy0zl.noe.edf.fr:2181,noeyyet7.noe.edf.fr:2181"
topic_name="fr.edf.dsp.dlk.api.dev-osi2-mmd-surveillance"

${KAFKA_BIN_DIR}/kafka-topics.sh \
  --zookeeper $zookeeper_urls \
  --topic $topic_name \
  --describe