### Kafka Commands


Create a topic named products.commands
```
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic products.commands --partitions 1 --replication-factor 1
```

Create a topic named products.replies
```
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic products.replies --partitions 1 --replication-factor 1
```

List all topics
```
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Visualize the messages in the products.commands topic
```aiignore
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic products.commands --from-beginning
```

Visualize the messages in the products.replies topic
```aiignore
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic products.replies --from-beginning
```