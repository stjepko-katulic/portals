package com.example.scrapping.models.rep;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RepModelVijesti implements IPortal {

  Elements elements = new Elements();
  String baseUrlRep = "https://rep.hr";

  public RepModelVijesti() throws IOException {
    int brojStranica=2;


    for (int i=1; i<=brojStranica; i++) {
      Document document = Jsoup.connect("https://rep.hr/stranica/" + i).get();

      if (i==1) {
        elements.add(document.getElementsByClass("content-column").get(0).getElementsByClass("post").get(0));
      }

      Elements elementsX = document.getElementsByClass("content-column").get(0).getElementsByClass("standard");
      elements.addAll(elementsX);
    }
  }

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> x.select("h1, h2, h3, h4").get(0).getElementsByTag("a").get(0).html())
            .collect(Collectors.toList());
    return nasloviClanaka;
  }

  @Override
  public List<String> getSazetciClanaka() {
    List<String> sazetciClanaka = elements.stream()
            .map(x -> {
              String sazetak = null;

              if (x.getElementsByClass("post-caption").get(0).getElementsByTag("p").size() > 0) {
                sazetak = x.getElementsByClass("post-caption").get(0)
                        .getElementsByTag("p").get(0).html();
              } else {
                sazetak = "";
              }

              return sazetak;
            })
            .collect(Collectors.toList());
    return sazetciClanaka;
  }

  @Override
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviNaSlike = elements.stream()
            .map(x -> baseUrlRep + x.getElementsByTag("img").get(0).attr("src"))
            .collect(Collectors.toList());
    return linkoviNaSlike;
  }

  @Override
  public List<String> getLinkoviNaClanke() {
    List<String> linkoviNaClanke = elements.stream()
            .map(x -> baseUrlRep + x.select("h1, h2, h3, h4").get(0)
                    .getElementsByTag("a").attr("href"))
            .collect(Collectors.toList());
    return linkoviNaClanke;
  }

  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> x.getElementsByClass("time").get(0).html())
            .map(x -> x.toLowerCase())
            .collect(Collectors.toList());
    return vremenaObjave;
  }
}
