package com.example.scrapping.controllers;

import com.example.scrapping.models.Counter;
import com.example.scrapping.models.IPortal;
import com.example.scrapping.models.dnevno.DnevnoModelSport;
import com.example.scrapping.models.dnevno.DnevnoModelVijesti;
import com.example.scrapping.models.ict.IctModelVijesti;
import com.example.scrapping.models.index.IndexModelSport;
import com.example.scrapping.models.index.IndexModelVijesti;
import com.example.scrapping.models.narod.NarodModelSport;
import com.example.scrapping.models.narod.NarodModelVijesti;
import com.example.scrapping.models.net.NetModelSport;
import com.example.scrapping.models.net.NetModelVijesti;
import com.example.scrapping.models.rep.RepModelVijesti;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {

  @GetMapping(path = "/")
  public ModelAndView indexScrapping() throws IOException {
    IPortal indexModelVijesti = new IndexModelVijesti();
    IPortal indexModelSport = new IndexModelSport();

    ModelAndView modelAndView = new ModelAndView();
    getModelAndView(indexModelVijesti, modelAndView, "Vijesti");
    getModelAndView(indexModelSport, modelAndView, "Sport");

    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;
  }


  @GetMapping("/narod")
  public ModelAndView narodScrapping() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    IPortal narodModelVijesti = new NarodModelVijesti();
    IPortal narodModelSport = new NarodModelSport();

    getModelAndView(narodModelVijesti, modelAndView, "Vijesti");
    getModelAndView(narodModelSport, modelAndView, "Sport");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");
    return modelAndView;
  }

  @GetMapping("/dnevno")
  public ModelAndView dnevnoScrapping() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    IPortal dnevnoModelVijesti = new DnevnoModelVijesti();
    IPortal dnevnoModulSport = new DnevnoModelSport();

    getModelAndView(dnevnoModelVijesti, modelAndView, "Vijesti");
    getModelAndView(dnevnoModulSport, modelAndView, "Sport");
    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;
  }

  @GetMapping("/net")
  public ModelAndView netScrapping() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    IPortal netModelVijesti = new NetModelVijesti();
    IPortal netModelSport = new NetModelSport();
    getModelAndView(netModelVijesti, modelAndView, "Vijesti");
    getModelAndView(netModelSport, modelAndView, "Sport");

    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;
  }

  @GetMapping("/rep")
  public ModelAndView repScrapping() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    IPortal repModelVijesti = new RepModelVijesti();
    getModelAndView(repModelVijesti, modelAndView, "Vijesti");

    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;
  }

  @GetMapping("/ict")
  public ModelAndView ictScrapping() throws IOException {
    ModelAndView modelAndView = new ModelAndView();
    IPortal ictModelVijesti = new IctModelVijesti();
    getModelAndView(ictModelVijesti, modelAndView, "Vijesti");

    modelAndView.addObject("counter", new Counter());
    modelAndView.setViewName("index");

    return modelAndView;
  }


  private ModelAndView getModelAndView(IPortal portal, ModelAndView modelAndView, String attributeSuffix) {
    modelAndView.addObject("listaNaslova" + attributeSuffix, portal.getNasloviClanaka());
    modelAndView.addObject("listaSazetaka" + attributeSuffix, portal.getSazetciClanaka());
    modelAndView.addObject("linkoviClanci" + attributeSuffix, portal.getLinkoviNaClanke());
    modelAndView.addObject("linkoviNaSlike" + attributeSuffix, portal.getLinkoviSlikeClanaka());
    modelAndView.addObject("listaVremenaObjave" + attributeSuffix, portal.getVremenaObjave());

    return modelAndView;
  }
}
