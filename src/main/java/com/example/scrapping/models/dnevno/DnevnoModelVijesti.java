package com.example.scrapping.models.dnevno;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "dnevnoModelVijesti")
public class DnevnoModelVijesti implements IPortal {
  String baseUrlDnevno = "http://www.dnevno.hr";
  Elements elements = new Elements();

  @Value(value = "${properties.max-broj-znakova.sazetak}")
  int maxBrojZnakovaSazetak;

  @Value(value = "${properties.max-broj-znakova.naslov}")
  int maxBrojZnakovaNaslov;

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> {
              String naslov = x.getElementsByTag("h2").get(0).html();
              naslov = naslov.replace("&nbsp;", " ");
              naslov = naslov.replace("&amp;", "&");

              if (naslov.length()>maxBrojZnakovaNaslov) {
                naslov = naslov.substring(0, maxBrojZnakovaNaslov) + "...";
              }

              return naslov;
            })
            .collect(Collectors.toList());
    return nasloviClanaka;
  }

  @Override
  public List<String> getSazetciClanaka() {
    List<String> sazetciClanaka = elements.stream()
            .map(x -> {
              String sazetak = x.getElementsByTag("p").get(0).html();
              sazetak = sazetak.replace("&nbsp;", " ");
              sazetak = sazetak.replace("&amp;", "&");

              if (sazetak.length()>maxBrojZnakovaSazetak) {
                sazetak = sazetak.substring(0, maxBrojZnakovaSazetak) + "...";
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

  @Override
  public void createElements(String stranica) throws IOException {
    if (elements!=null)
      elements.clear();

    // vijesti iz hrvatske
    Document document = Jsoup.connect("https://www.dnevno.hr/category/vijesti/hrvatska/page/" + stranica).get();
    Elements elementsX = document.getElementsByClass("post-holder").get(0).getElementsByTag("article");
    elements.addAll(elementsX);

    // vijesti iz svijeta
    document = Jsoup.connect("https://www.dnevno.hr/category/vijesti/svijet/page/" + stranica).get();
    elementsX = document.getElementsByClass("post-holder").get(0).getElementsByTag("article");
    elements.addAll(elementsX);
  }
}
