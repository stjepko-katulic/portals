package com.example.scrapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class ScrappingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ScrappingApplication.class, args);
  }

//  @Override
//  public void run(String... args) throws Exception {
//
//
//
//  }
}
