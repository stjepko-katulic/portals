package com.example.scrapping.models;

import java.io.IOException;
import java.util.List;

public interface IPortal {
  public List<String> getNasloviClanaka();
  public List<String> getSazetciClanaka();
  public List<String> getLinkoviNaClanke();
  public List<String> getVremenaObjave();
  public void createElements(String stranica) throws IOException;
}
