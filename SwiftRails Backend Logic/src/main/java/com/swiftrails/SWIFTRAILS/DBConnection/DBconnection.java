package com.swiftrails.SWIFTRAILS.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import jakarta.activation.DataSource;
import jakarta.annotation.PostConstruct;

@Component
public class DBconnection {
    
    @Value("${spring.datasource.driverName}")
    private String driverClassName;
    
    @Value("${spring.datasource.connectionString}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    private Connection con;
    
//    @Autowired
//    private DataSource dataSource;

    @PostConstruct
    public void init() {
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Success!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
}