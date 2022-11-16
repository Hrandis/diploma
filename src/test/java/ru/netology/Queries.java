package ru.netology;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import ru.netology.data.LoanInfo;
import ru.netology.data.PurchaseInfo;

import java.sql.DriverManager;

public class Queries {
    String loanData = "SELECT created, status FROM credit_request_entity;";
    String purchaseData = "SELECT amount, created, status FROM payment_entity;";
    QueryRunner runner = new QueryRunner();
    String postgreSQL = "jdbc:postgresql://localhost:5432/pdb";
    String mySQL = "jdbc:mysql://localhost:3306/mdb";
    String login = "hrandis";
    String password = "hrandispass";


    @SneakyThrows
    public void postgreSQLLoanInfo() {
        try(var conn = DriverManager.getConnection(postgreSQL, login, password);){
            var result = runner.query(conn, loanData, new BeanListHandler<>(LoanInfo.class));
            System.out.println(result);
        }
    }
    @SneakyThrows
    public void postgreSQLPurchaseInfo() {
        try(var conn = DriverManager.getConnection(postgreSQL, login, password);){
            var result = runner.query(conn, purchaseData, new BeanListHandler<>(PurchaseInfo.class));
            System.out.println(result);
        }
    }
    @SneakyThrows
    public void mySQLLoanInfo() {
        try(var conn = DriverManager.getConnection(mySQL, login, password);){
            var result = runner.query(conn, loanData, new BeanListHandler<>(LoanInfo.class));
            System.out.println(result);
        }
    }
    @SneakyThrows
    public void mySQLPurchaseInfo() {
        try(var conn = DriverManager.getConnection(mySQL, login, password);){
            var result = runner.query(conn, purchaseData, new BeanListHandler<>(PurchaseInfo.class));
            System.out.println(result);
        }
    }
}
