package com.example.scrapping.controllers;

import com.example.scrapping.models.Counter;
import com.example.scrapping.models.IndexModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {
  @GetMapping(path = "/")
  public ModelAndView index() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    IndexModel indexModel = new IndexModel();

    modelAndView.addObject("listaNaslova", indexModel.getNasloviClanaka());
    modelAndView.addObject("listaSazetaka", indexModel.getSazetciClanaka());
    modelAndView.addObject("linkoviClanci", indexModel.getLinkoviNaClanke());
    modelAndView.addObject("linkoviNaSlile", indexModel.getLinkoviSlikeClanaka());
    modelAndView.addObject("listaVremenaObjave", indexModel.getVremenaObjave());

    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;

  }
}
