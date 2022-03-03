package com.dvivasva.wallet.message;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, WalletDto> walletDtoKafkaTemplate;

    public void sendNumberAccountOrigin(String value) {
        kafkaTemplate.send(Topic.FIND_ACCOUNT_ORIGIN,value);
        logger.info("Messages successfully pushed on topic: " + Topic.FIND_ACCOUNT_ORIGIN);
    }

    public void sendAccountToPayment(String value) {
        kafkaTemplate.send(Topic.RESPONSE_ACCOUNT_FROM_WALLET,value);
        logger.info("send messages to payment-->");
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_ACCOUNT_FROM_WALLET);
    }
    public void responseWallet(WalletDto value) {
        walletDtoKafkaTemplate.send(Topic.RESPONSE_WALLET,value);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_WALLET);
    }
}
