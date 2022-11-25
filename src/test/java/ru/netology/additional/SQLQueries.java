package ru.netology.additional;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import ru.netology.data.RequestInfo;

import java.sql.DriverManager;
import java.util.List;

//SQL queries for checking request data
public class SQLQueries {

    @SneakyThrows
    //querying data about requests from DB
    public void RequestInfo() {
        String SQLData = "SELECT created, status FROM credit_request_entity;";
        QueryRunner runner = new QueryRunner();
        var jdbcValue = System.getProperty("jdbc");
        var loginValue = System.getProperty("login");
        var passwordValue = System.getProperty("password");
        try (var conn = DriverManager.getConnection(jdbcValue, loginValue, passwordValue);) {
            var result = runner.query(conn, SQLData, new BeanListHandler<>(RequestInfo.class));
            System.out.println(result);
        }
    }

//    @SneakyThrows
//    //querying data about loan requests from PostgreSQL
//    public void postgreSQLLoanInfo() {
//        try (var conn = DriverManager.getConnection(postgreSQL, login, password);) {
//            var result = runner.query(conn, loanData, new BeanListHandler<>(RequestInfo.class));
//            System.out.println(result);
//        }
//    }
//
//    @SneakyThrows
//    //querying data about purchase requests from PostgreSQL
//    public void postgreSQLPurchaseInfo() {
//        try (var conn = DriverManager.getConnection(postgreSQL, login, password);) {
//            var result = runner.query(conn, purchaseData, new BeanListHandler<>(PurchaseInfo.class));
//            System.out.println(result);
//        }
//    }
//
//    @SneakyThrows
//    //querying data about loan requests from MYSQL
//    public void mySQLLoanInfo() {
//        try (var conn = DriverManager.getConnection(mySQL, login, password);) {
//            var result = runner.query(conn, loanData, new BeanListHandler<>(RequestInfo.class));
//            System.out.println(result);
//        }
//    }
//
//    @SneakyThrows
//    //querying data about purchase requests from MYSQL
//    public void mySQLPurchaseInfo() {
//        try (var conn = DriverManager.getConnection(mySQL, login, password);) {
//            var result = runner.query(conn, purchaseData, new BeanListHandler<>(PurchaseInfo.class));
//            System.out.println(result);
//        }
//    }
}
