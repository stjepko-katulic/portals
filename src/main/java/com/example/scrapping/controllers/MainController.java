package com.example.scrapping.controllers;

import com.example.scrapping.models.Counter;
import com.example.scrapping.models.IPortal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {

  @Autowired
  @Qualifier(value = "indexModelVijesti")
  IPortal indexModelVijesti;

  @Autowired
  @Qualifier(value = "indexModelSport")
  IPortal indexModelSport;

  @Autowired
  @Qualifier(value = "netModelVijesti")
  IPortal netModelVijesti;

  @Autowired
  @Qualifier(value = "netModelSport")
  IPortal netModelSport;

  @Autowired
  @Qualifier(value = "narodModelVijesti")
  IPortal narodModelVijesti;

  @Autowired
  @Qualifier(value = "narodModelSport")
  IPortal narodModelSport;

  @Autowired
  @Qualifier(value = "dnevnoModelVijesti")
  IPortal dnevnoModelVijesti;

  @Autowired
  @Qualifier(value = "dnevnoModelSport")
  IPortal dnevnoModelSport;

  @Autowired
  @Qualifier(value = "repModelVijesti")
  IPortal repModelVijesti;

  @Autowired
  @Qualifier(value = "ictModelVijesti")
  IPortal ictModelVijesti;

  @GetMapping(path = {"/", "/index"})
  public ModelAndView index() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    indexModelVijesti.createElements("index");
    indexModelSport.createElements("index");

    getModelAndView(indexModelVijesti, modelAndView, "Vijesti");
    getModelAndView(indexModelSport, modelAndView, "Sport");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @GetMapping(path={"/net", "net/{stranica}"})
  public ModelAndView netScrapping(@PathVariable(required = false) String stranica) throws IOException {
    ModelAndView modelAndView = new ModelAndView();

    if (stranica == null) {
      stranica  = "1";
    }

    netModelVijesti.createElements(stranica);
    netModelSport.createElements(stranica);
    getModelAndView(netModelVijesti, modelAndView, "Vijesti");
    getModelAndView(netModelSport, modelAndView, "Sport");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }


  @GetMapping(path={"/narod", "/narod/{stranica}"})
  public ModelAndView narodScrapping(@PathVariable(required = false) String stranica) throws IOException {
    ModelAndView modelAndView = new ModelAndView();

    if (stranica == null) {
      stranica  = "1";
    }

    narodModelVijesti.createElements(stranica);
    narodModelVijesti.createElements(stranica);
    getModelAndView(narodModelVijesti, modelAndView, "Vijesti");
    getModelAndView(narodModelSport, modelAndView, "Sport");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @GetMapping(path={"/dnevno", "/dnevno/{stranica}"})
  public ModelAndView dnevnoScrapping(@PathVariable(required = false) String stranica) throws IOException {
    ModelAndView modelAndView = new ModelAndView();

    if (stranica == null) {
      stranica  = "1";
    }

    dnevnoModelVijesti.createElements(stranica);
    dnevnoModelSport.createElements(stranica);
    getModelAndView(dnevnoModelVijesti, modelAndView, "Vijesti");
    getModelAndView(dnevnoModelSport, modelAndView, "Sport");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }


  @GetMapping(path={"/rep", "/rep/{stranica}"})
  public ModelAndView repScrapping(@PathVariable(required = false) String stranica) throws IOException {
    ModelAndView modelAndView = new ModelAndView();

    if (stranica == null) {
      stranica  = "1";
    }

    repModelVijesti.createElements(stranica);
    getModelAndView(repModelVijesti, modelAndView, "Vijesti");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @GetMapping(path={"/ict", "/ict/{stranica}"})
  public ModelAndView ictScrapping(@PathVariable(required = false) String stranica) throws IOException {
    ModelAndView modelAndView = new ModelAndView();

    if (stranica == null) {
      stranica  = "1";
    }

    ictModelVijesti.createElements(stranica);
    getModelAndView(ictModelVijesti, modelAndView, "Vijesti");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }


  private ModelAndView getModelAndView(IPortal portal, ModelAndView modelAndView, String attributeSuffix) {
    modelAndView.addObject("listaNaslova" + attributeSuffix, portal.getNasloviClanaka());
    modelAndView.addObject("listaSazetaka" + attributeSuffix, portal.getSazetciClanaka());
    modelAndView.addObject("linkoviClanci" + attributeSuffix, portal.getLinkoviNaClanke());
//    modelAndView.addObject("linkoviNaSlike" + attributeSuffix, portal.getLinkoviSlikeClanaka());
    modelAndView.addObject("listaVremenaObjave" + attributeSuffix, portal.getVremenaObjave());
    return modelAndView;
  }
}
