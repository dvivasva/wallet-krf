package com.dvivasva.wallet.message;

import com.dvivasva.wallet.entity.RequestBuyBootCoin;
import com.dvivasva.wallet.service.WalletService;
import com.dvivasva.wallet.utils.JsonUtils;
import com.dvivasva.wallet.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final WalletService walletService;
    private final Sender sender;

    @KafkaListener(topics = Topic.REQUEST_BUY, groupId = "group_id_wallet")
    public void consumeFormGateway(String param) {
        logger.info("Has been published an insert payment from service gateway-krf : " + param);
        var value = new RequestBuyBootCoin();
        try {
            value = JsonUtils.convertFromJsonToObject(param, RequestBuyBootCoin.class);
            if (value.getPayMode().equals("Yunki")) {
                logger.info("search wallet");
                var find = walletService.findByNumberPhone(value.getNumber());

                RequestBuyBootCoin finalValue = value;
                find.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist wallet")))
                        .doOnNext(p -> {

                            finalValue.setNumber(p.getNumberAccount());
                            sender.sendRequestBuyBootCoinToAccount(finalValue);
                        }).subscribe();
                logger.info("send messages to account -->");

            } else {
                logger.info("do nothing");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = Topic.RESPONSE_REQUEST_BUY_BOOT_COIN_WALLET, groupId = "group_id_wallet")
    public void consumeFormAccount(String param) {
        logger.info("Has been published an insert payment from service gateway-mobile : " + param);
        var value = new RequestBuyBootCoin();
        try {
            value = JsonUtils.convertFromJsonToObject(param, RequestBuyBootCoin.class);
            logger.info("search wallet");
            var find = walletService.findByNumberAccount(value.getNumber());
            RequestBuyBootCoin finalValue = value;
            find.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist wallet")))
                    .doOnNext(p -> {
                        finalValue.setNumber(p.getNumberPhone());
                        sender.sendRequestBuyBootCoinToPayment(finalValue);
                    }).subscribe();
            logger.info("send messages to payment -->");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
