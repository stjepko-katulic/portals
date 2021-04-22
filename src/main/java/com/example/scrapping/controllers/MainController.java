package com.example.scrapping.controllers;

import com.example.scrapping.models.Counter;
import com.example.scrapping.models.IIndex;
import com.example.scrapping.models.IndexModelSport;
import com.example.scrapping.models.IndexModelVijesti;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {
  @GetMapping(path = "/")
  public ModelAndView indexScrapping() throws IOException {
    IIndex indexModelVijesti = new IndexModelVijesti();
    IIndex indexModelSport = new IndexModelSport();

    ModelAndView modelAndView = new ModelAndView();
    getModelAndView(indexModelVijesti, modelAndView, "Vijesti");
    getModelAndView(indexModelSport, modelAndView, "Sport");

    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;

  }

  private ModelAndView getModelAndView(IIndex index, ModelAndView modelAndView, String attributeSuffix) {

    modelAndView.addObject("listaNaslova" + attributeSuffix, index.getNasloviClanaka());
    modelAndView.addObject("listaSazetaka" + attributeSuffix, index.getSazetciClanaka());
    modelAndView.addObject("linkoviClanci" + attributeSuffix, index.getLinkoviNaClanke());
    modelAndView.addObject("linkoviNaSlike" + attributeSuffix, index.getLinkoviSlikeClanaka());
    modelAndView.addObject("listaVremenaObjave" + attributeSuffix, index.getVremenaObjave());

    return modelAndView;
  }
}
