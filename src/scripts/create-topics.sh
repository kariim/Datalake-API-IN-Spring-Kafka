#!/bin/bash

export KAFKA_OPTS="-Djava.security.auth.login.config=/etc/kafka/conf/kafka_client_jaas.conf"
export KAFKA_BIN_DIR="/usr/hdp/current/kafka-broker/bin/"
export SITE_ZOOKEEPER="noeyy0zl.noe.edf.fr:2181,noeyyet7.noe.edf.fr:2181"
export RETENTION_MS=604800000

PREFIX="fr.edf.dsp.dlk.api.spring"
TOPICS="defaut-metrics:osi2-dsc-surveillance:osi2-fgai-surveillance:osi2-mat-surveillance:osi2-mmd-surveillance:osi2-wrh-surveillance:osi1-nucleaire-surveillance:osi1-plateausdin-surveillance:osi1-plateausint-surveillance:osi1-ris-surveillance:osi1-o2-surveillance:osi1-thit-surveillance:osi1-plateaurenouveau-surveillance"
IFS=":"
TOPIC_AR=($TOPICS)

for (( i=0; i<${#TOPIC_AR[@]}; i++ ));
do

        ${KAFKA_BIN_DIR}/kafka-topics.sh --create --topic "$PREFIX-${TOPIC_AR[$i]}" --zookeeper ${SITE_ZOOKEEPER} --partitions 3 --replication-factor 2 --config retention.ms=${RETENTION_MS}
done;
