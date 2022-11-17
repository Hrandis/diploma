package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//class for SQL result set converting
public class LoanInfo {
    private String created;
    private String status;
}
