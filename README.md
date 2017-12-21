Local install instructions On windows
-------------------------------------

Start Zookeeper
---------------
 => Open a terminal
 => type command : zkserver


Start Kafka
-----------
 => Open a new command prompt in the location D:\Software\kafka_2.10-0.10.1.0\bin\windows
 => type command : kafka-server-start.bat ..\..\config\server.properties


Create Kafka topic
------------------
 => Open a new command prompt in the location D:\Software\kafka_2.10-0.10.1.0\bin\windows
 => kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic_name


Create Kafka Producer
---------------------
 => Open a new command prompt in the location D:\Software\kafka_2.10-0.10.1.0\bin\windows
 => kafka-console-producer.bat --broker-list localhost:9092 --topic topic_name


Create Kafka Consumer
---------------------
 => Open a new command prompt in the location D:\Software\kafka_2.10-0.10.1.0\bin\windows
 => kafka-console-consumer.bat --zookeeper localhost:2181 --topic topic_name