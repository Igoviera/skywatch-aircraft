package com.skywatch.aircraft.services;

import com.skywatch.aircraft.model.AircraftData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AircraftProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AircraftProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC = "skywatch-telemetry";

    @Value("${airship.id:EMB-195-E2}")
    private String aircraftId;

    private double currentFuel = 100;
    private double currentAltitude = 35000;
    private boolean modoPouso = false;

    public AircraftProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 2000)
    public void sendFlightData() {

        if (currentAltitude > 0) {
            double consumption = 0.01 + (Math.random() * 0.04);
            currentFuel -= consumption;
            if (currentFuel <= 0) {
                currentFuel = 0;
            }
        }

        if (modoPouso) {
            currentAltitude -= 500;
            if (currentAltitude <= 0) {
                currentAltitude = 0;
                modoPouso = false;
            }
        } else {
            currentAltitude = 35000 + (Math.random() * 100);
        }

        AircraftData data = new AircraftData(
                aircraftId, //"EMB-195-E2",
                currentAltitude,
                850.0,
                currentFuel,
                System.currentTimeMillis()
        );

        LOGGER.info("Enviando telemetria: {}", data);
        kafkaTemplate.send(TOPIC, data);

    }

    @KafkaListener(topics = "skywatch-commands", groupId = "aircraft-group")
    public void ouvirComando(ConsumerRecord<String, String> record) {

        if (record.key() != null && record.key().equalsIgnoreCase(aircraftId)) {
            LOGGER.info("Iniciando descida...");
            this.modoPouso = true;

        } else {
            LOGGER.error("Ignorando comando: a chave " + record.key() + " nao sou eu (" + this.aircraftId + ")");
        }
    }

}
