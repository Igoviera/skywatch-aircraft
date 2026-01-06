package com.skywatch.aircraft.services;

import com.skywatch.aircraft.enums.AircraftStatus;
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

    private double currentFuel = 9.0;
    private double currentAltitude = 35000;
    private AircraftStatus aircraftStatus = AircraftStatus.EM_VOO;

    public AircraftProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 2000)
    public void sendFlightData() {

        if (currentAltitude > 0 && aircraftStatus != AircraftStatus.POUSO) {
            double consumption = 0.01 + (Math.random() * 0.04);
            this.currentFuel -= consumption;
            if (currentFuel <= 0) {
                this.currentFuel = 0;
            }
        }

        switch (aircraftStatus) {
            case EM_VOO -> currentAltitude = 3500 + (Math.random() * 100);
            case APROXIMACAO -> {
                currentAltitude -= 500;
                if (currentAltitude <= 0) {
                    currentAltitude = 0;
                    aircraftStatus = AircraftStatus.POUSO;
                    LOGGER.info("Aeronave pousou");
                }
            }
            case POUSO -> currentAltitude = 0;
        }

        AircraftData data = new AircraftData(
                aircraftId, //"EMB-195-E2",
                currentAltitude,
                850.0,
                currentFuel,
                System.currentTimeMillis(),
                aircraftStatus
        );

        LOGGER.info("Enviando telemetria: {}", data);
        kafkaTemplate.send(TOPIC, aircraftId, data);

    }

    @KafkaListener(topics = "skywatch-commands", groupId = "aircraft-${airship.id}")
    public void ouvirComando(ConsumerRecord<String, String> record) {

        if ("POUSO".equals(record.value()) && aircraftId.equalsIgnoreCase(record.key())) {
            LOGGER.info("Iniciando descida...");
            this.aircraftStatus = AircraftStatus.APROXIMACAO;
        }
    }
}
