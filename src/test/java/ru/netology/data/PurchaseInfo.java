package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//class for SQL result set converting
public class PurchaseInfo {
    private int amount;
    private String created;
    private String status;
}
