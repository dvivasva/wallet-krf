package com.dvivasva.wallet.controller;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<WalletDto> create(
            @RequestBody final Mono<WalletDto> wallet) {
        return walletService.create(wallet);
    }

    @GetMapping("/{numberPhone}")
    public Mono<WalletDto> findByNumberPhone(@PathVariable final String numberPhone) {
        return walletService.findByNumberPhone(numberPhone);
    }


}
