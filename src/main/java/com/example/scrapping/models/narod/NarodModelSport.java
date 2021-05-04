package com.example.scrapping.models.narod;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class NarodModelSport implements IPortal {

  String baseUrlNarod = "www.narod.hr";
  Elements elements = new Elements();
  Elements elementsSazetci = new Elements();

  public NarodModelSport(String stranica) throws IOException {
      Document document = Jsoup.connect("https://narod.hr/sport/page/" + stranica).get();
      Elements elementsX = document.getElementsByClass("td-main-content").get(0).getElementsByClass("td-image-wrap");
      elements.addAll(elementsX);
      elementsSazetci.addAll(document.getElementsByClass("td-excerpt"));
  }

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .filter(x -> x.attr("href").contains("https://narod.hr/sport"))
            .map(x -> {
              String naslov = x.attr("title");
              naslov = naslov.replace("&nbsp;", " ");
              naslov = naslov.replace("&amp;", "&");
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
}
