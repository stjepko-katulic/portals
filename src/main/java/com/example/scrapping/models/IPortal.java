package com.example.scrapping.models;

import java.util.List;

public interface IPortal {
  public List<String> getNasloviClanaka();
  public List<String> getSazetciClanaka();
  public List<String> getLinkoviSlikeClanaka();
  public List<String> getLinkoviNaClanke();
  public List<String> getVremenaObjave();
}