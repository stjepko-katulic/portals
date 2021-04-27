package com.example.scrapping.models.dnevno;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DnevnoModelVijesti implements IPortal {
  String baseUrlDnevno = "http://www.dnevno.hr";
  Elements elements = new Elements();

  public DnevnoModelVijesti() throws IOException {
    int brojStranica=1;

    for (int i=1; i<=brojStranica; i++) {
      // vijesti iz hrvatske
      Document document = Jsoup.connect("https://www.dnevno.hr/category/vijesti/hrvatska/page/" + i).get();
      Elements elementsX = document.getElementsByClass("post-holder").get(0).getElementsByTag("article");
      elements.addAll(elementsX);

      // vijesti iz svijeta
      document = Jsoup.connect("https://www.dnevno.hr/category/vijesti/svijet/page/" + i).get();
      elementsX = document.getElementsByClass("post-holder").get(0).getElementsByTag("article");
      elements.addAll(elementsX);
    }
  }



  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> x.getElementsByTag("h2").get(0).html())
            .collect(Collectors.toList());
    return nasloviClanaka;
  }

  @Override
  public List<String> getSazetciClanaka() {
    List<String> sazetciClanaka = elements.stream()
            .map(x -> {
              String sazetak = x.getElementsByTag("p").get(0).html();

              if (sazetak.length()>250) {
                sazetak = sazetak.substring(0, 250) + "...";
              }

              return sazetak;

            })
            .collect(Collectors.toList());
    return sazetciClanaka;
  }


  @Override
  public List<String> getLinkoviNaClanke() {
    List<String> linkoviNaClanke = elements.stream()
            .map(x -> x.getElementsByTag("a").get(0).attr("href"))
            .collect(Collectors.toList());
    return linkoviNaClanke;
  }


  @Override
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviNaSlike = elements.stream()
            .map(x -> x.getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("src"))
            .collect(Collectors.toList());
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
