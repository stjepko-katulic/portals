package com.example.scrapping.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IndexModelVijesti implements IIndex {

  String baseUrlIndex = "www.index.hr";
  Document doc;
  Elements elements;

  public IndexModelVijesti() throws IOException {
    doc = Jsoup.connect("https://www.index.hr/najnovije?kategorija=3").get();
    // trazenje svih elementata klase "title-box"
    elements = doc.getElementsByClass("title-box");
  }

  @Override
  public List<String> getNasloviClanaka() {
    // mapiranje naslova clanaka u listu
    List<String> nasloviClanaka = elements.stream()
            .map(x -> {
              String naslov = x.getElementsByClass("title").html();
              naslov = naslov.replace("&nbsp;", " ");
              naslov = naslov.replace("&amp;", "&");

              if (naslov.indexOf(">") != -1) {
                naslov = naslov.substring(naslov.lastIndexOf(">") + 1);
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
              String sazetak = x.getElementsByClass("summary").html();
              sazetak = sazetak.replace("&nbsp;", " ");
              sazetak = sazetak.replace("&amp;", "&");
              return sazetak;
            })

            .collect(Collectors.toList());

    return sazetciClanaka;
  }

  @Override
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviNaFotke = elements.stream()
            .map(x -> x.getElementsByClass("img-responsive").get(0).attr("src"))
            .collect(Collectors.toList());

    return linkoviNaFotke;
  }

  @Override
  public List<String> getLinkoviNaClanke() {
    List<String> linkoviNaClanake = elements.stream()
            .map(x -> baseUrlIndex + x.getElementsByClass("vijesti-text-hover").get(0).attr("href"))
            .collect(Collectors.toList());

    return linkoviNaClanake;
  }

  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> x.parent().getElementsByClass("num").html() + " " +
                    x.parent().getElementsByClass("desc").html())
            .collect(Collectors.toList());

    return vremenaObjave;
  }

}
