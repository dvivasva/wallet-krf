package com.dvivasva.wallet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletDto {
    private String id;
    private String document;
    private String numberPhone;
    private String email;
    private String numberAccount;
}
