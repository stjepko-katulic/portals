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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class MainController {

  @GetMapping(path = {"/", "/index"})
  public ModelAndView index() throws IOException {
    IPortal indexModelVijesti = new IndexModelVijesti();
    IPortal indexModelSport = new IndexModelSport();

    ModelAndView modelAndView = new ModelAndView();
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

    IPortal netModelVijesti = new NetModelVijesti(stranica);
    IPortal netModelSport = new NetModelSport(stranica);

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

    IPortal narodModelVijesti = new NarodModelVijesti(stranica);
    IPortal narodModelSport = new NarodModelSport(stranica);

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

    IPortal dnevnoModelVijesti = new DnevnoModelVijesti(stranica);
    IPortal dnevnoModulSport = new DnevnoModelSport(stranica);

    getModelAndView(dnevnoModelVijesti, modelAndView, "Vijesti");
    getModelAndView(dnevnoModulSport, modelAndView, "Sport");
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

    IPortal repModelVijesti = new RepModelVijesti(stranica);
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

    IPortal ictModelVijesti = new IctModelVijesti(stranica);
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
