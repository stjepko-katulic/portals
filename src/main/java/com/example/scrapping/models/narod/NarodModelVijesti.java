package com.example.scrapping.models.narod;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class NarodModelVijesti implements IPortal {

  String baseUrlNarod = "www.narod.hr";
  Elements elements = new Elements();

  public NarodModelVijesti() throws IOException {
    int brojStranica=1;

    for (int i=1; i<=brojStranica; i++) {
      // vijesti iz hrvatske
      Document document = Jsoup.connect("https://narod.hr/hrvatska/page/" + i).get();
      Elements elementsX = document.getElementsByClass("td-main-content").get(0).getElementsByClass("td-image-wrap");
      elements.addAll(elementsX);

      // vijesti iz svijeta
      document = Jsoup.connect("https://narod.hr/svijet/page/" + i).get();
      elementsX = document.getElementsByClass("td-main-content").get(0).getElementsByClass("td-image-wrap");
      elements.addAll(elementsX);
    }
  }

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x->x.attr("title"))
            .collect(Collectors.toList());
    return nasloviClanaka;
  }

  @Override
  public List<String> getSazetciClanaka() {
    List<String> sazetciClanaka = elements.stream()
            .map(x -> "")
            .collect(Collectors.toList());
    return sazetciClanaka;
  }


  @Override
  public List<String> getLinkoviNaClanke() {
    List<String> linkoviNaClanke = elements.stream()
            .map(x->x.attr("href"))
            .collect(Collectors.toList());
    return linkoviNaClanke;
  }

  @Override
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviNaSlike = elements.stream()
            .map(x -> {

              String urlSlike;

              try {
                 urlSlike = x.getElementsByTag("img").get(0).attr("data-lazy-src");
              } catch (Exception e) {
                urlSlike = x.getElementsByClass("entry-thumb").get(0).attr("data-bg");
              }
              return urlSlike;
            })
            .collect(Collectors.toList());
//
    return linkoviNaSlike;
  }


  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> "")
            .collect(Collectors.toList());
     return vremenaObjave;
  }
}
