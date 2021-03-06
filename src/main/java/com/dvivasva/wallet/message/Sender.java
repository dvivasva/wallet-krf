package com.dvivasva.wallet.message;

import com.dvivasva.wallet.entity.RequestBuyBootCoin;
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
    private final KafkaTemplate<String, RequestBuyBootCoin> requestBuyBootCoinKafkaTemplateKafka;

    public void sendRequestBuyBootCoinToAccount(RequestBuyBootCoin finalValue) {
        requestBuyBootCoinKafkaTemplateKafka.send(Topic.FIND_ACCOUNT_ORIGIN,finalValue);
        logger.info("Messages successfully pushed on topic: " + Topic.FIND_ACCOUNT_ORIGIN);
    }
    public void sendRequestBuyBootCoinToPayment(RequestBuyBootCoin finalValue) {
        requestBuyBootCoinKafkaTemplateKafka.send(Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_WALLET_PARSE,finalValue);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_WALLET_PARSE);
    }
}
