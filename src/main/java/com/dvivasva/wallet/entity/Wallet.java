package com.dvivasva.wallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("wallet-krf")
public class Wallet {
    @Id
    private String id;
    private String document;
    private String numberPhone;
    private String email;
    private String numberAccount;
}
