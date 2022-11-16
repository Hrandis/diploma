package ru.netology.data;

import lombok.Value;

@Value
public class PurchaseInfo {
    private int amount;
    private String created;
    private String status;
}
