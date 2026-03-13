```
docker exec -it kafka \
  kafka-topics \
    --create \
    --topic user-events \
    --bootstrap-server kafka:29092 \
    --partitions 3 \
    --replication-factor 1
```

```
python -m venv .venv
source .venv/bin/activate.fish
pip install kafka-python
python events.py
```

```
docker exec -it clickhouse \
  clickhouse-client -u prezdev --password supersecret -d testdb

SET allow_experimental_kafka_engine = 1;

CREATE TABLE IF NOT EXISTS user_events_raw
(
    raw         String,          -- JSON completo tal cual llega desde Kafka
    ingested_at DateTime DEFAULT now()
)
ENGINE = MergeTree()
ORDER BY ingested_at;


CREATE TABLE IF NOT EXISTS user_events_kafka_raw
(
    raw String      -- una sola columna que contendrá TODO el JSON
)
ENGINE = Kafka
SETTINGS
    kafka_broker_list = 'kafka:29092',
    kafka_topic_list = 'user-events',
    kafka_group_name = 'clickhouse_consumer_user_events_raw',
    kafka_format = 'JSONAsString',
    kafka_num_consumers = 1;

CREATE MATERIALIZED VIEW IF NOT EXISTS mv_user_events_raw
TO user_events_raw
AS
SELECT
    raw
FROM user_events_kafka_raw;

```