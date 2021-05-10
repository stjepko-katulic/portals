package com.example.scrapping.models.narod;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "narodModelSport")
public class NarodModelSport implements IPortal {

  String baseUrlNarod = "www.narod.hr";
  Elements elements = new Elements();
  Elements elementsSazetci = new Elements();

  @Value(value = "${properties.max-broj-znakova.sazetak}")
  int maxBrojZnakovaSazetak;

  @Value(value = "${properties.max-broj-znakova.naslov}")
  int maxBrojZnakovaNaslov;

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .filter(x -> x.attr("href").contains("https://narod.hr/sport"))
            .map(x -> {
              String naslov = x.attr("title");
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
    List<String> sazetciClanaka = elementsSazetci.stream()
            .map(x -> {
              String sazetak = x.html();
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
            .filter(x -> x.attr("href").contains("https://narod.hr/sport"))
            .map(x->x.attr("href"))
            .collect(Collectors.toList());

    return linkoviNaClanke;
  }

  @Override
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviNaSlike = elements.stream()
            .filter(x -> x.attr("href").contains("https://narod.hr/sport"))
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

    return linkoviNaSlike;
  }


  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .filter(x -> x.attr("href").contains("https://narod.hr/sport"))
            .map(x -> "")
            .collect(Collectors.toList());

    return vremenaObjave;
  }

  @Override
  public void createElements(String stranica) throws IOException {
    if (elements!=null)
      elements.clear();

    Document document = Jsoup.connect("https://narod.hr/sport/page/" + stranica).get();
    Elements elementsX = document.getElementsByClass("td-main-content").get(0).getElementsByClass("td-image-wrap");
    elements.addAll(elementsX);
    elementsSazetci.addAll(document.getElementsByClass("td-excerpt"));
  }
}
