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

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private final WalletService walletService;
    private final Sender sender;

    @KafkaListener(topics = Topic.REQUEST_BUY, groupId = "group_id_wallet")
    public void consumeFormGateway(String param) {
        logger.info("Has been published an insert payment from service gateway-mobile : " + param);
        var value = new RequestBuyBootCoin();
        try {
            value = JsonUtils.convertFromJsonToObject(param, RequestBuyBootCoin.class);
            if (value.getPayMode().equals("Yunki")) {
                logger.info("search wallet");
                var find = walletService.findByNumberPhone(value.getNumber());
                find.doOnNext(p -> {
                    sender.sendNumberAccountOrigin(p.getNumberAccount());

                }).subscribe();
                logger.info("send messages to account -->");

            } else {
                logger.info("do nothing");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = Topic.RESPONSE_ACCOUNT_TO_WALLET, groupId = "group_id_wallet")
    public void consumeFormAccount(String param) {
        logger.info("Has been published an response from service account-krf : " + param);
        sender.sendAccountToPayment(param);

    }


}
