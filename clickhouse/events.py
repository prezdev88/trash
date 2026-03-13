import json
import time
from datetime import datetime, timezone
from kafka import KafkaProducer

KAFKA_BOOTSTRAP_SERVERS = ["localhost:9092"]
TOPIC_NAME = "user-events"

MESSAGES_PER_BATCH = 20000
SLEEP_SECONDS = 3


def main():
    print(f"Conectando a Kafka en {KAFKA_BOOTSTRAP_SERVERS}...")
    producer = KafkaProducer(
        bootstrap_servers=KAFKA_BOOTSTRAP_SERVERS,
        value_serializer=lambda v: json.dumps(v).encode("utf-8"),
    )

    counter = 0
    print(f"Enviando {MESSAGES_PER_BATCH} mensajes cada {SLEEP_SECONDS} segundos al tópico '{TOPIC_NAME}'...")

    try:
        while True:
            start = time.time()
            futures = []

            for i in range(MESSAGES_PER_BATCH):
                counter += 1
                event = {
                    "userId": f"user-test-{counter}",
                    "timestamp": datetime.now(timezone.utc).isoformat(),
                    "message": f"mensaje de prueba #{counter}",
                }
                future = producer.send(TOPIC_NAME, value=event)
                futures.append(future)

            # Verificamos que al menos el primer mensaje del lote se haya enviado bien
            try:
                record_md = futures[0].get(timeout=10)
                print(
                    f"[OK] Primer mensaje del lote enviado: "
                    f"topic={record_md.topic}, partition={record_md.partition}, offset={record_md.offset}"
                )
            except Exception as e:
                print(f"[ERROR] al enviar el primer mensaje del lote: {e!r}")

            producer.flush()
            elapsed = time.time() - start
            print(
                f"Lote enviado: {MESSAGES_PER_BATCH} mensajes "
                f"en {elapsed:.2f} segundos (total enviados={counter})"
            )

            time.sleep(SLEEP_SECONDS)

    except KeyboardInterrupt:
        print("\nInterrumpido por el usuario. Cerrando producer...")
    finally:
        producer.flush()
        producer.close()
        print("Listo, producer cerrado.")


if __name__ == "__main__":
    main()
