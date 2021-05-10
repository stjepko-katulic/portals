package com.example.scrapping.models.index;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "indexModelSport")
public class IndexModelSport implements IPortal {

  String baseUrlIndex = "http://www.index.hr";
  Document doc;
  Elements elements;

  @Value(value = "${properties.max-broj-znakova.sazetak}")
  int maxBrojZnakovaSazetak;

  @Value(value = "${properties.max-broj-znakova.naslov}")
  int maxBrojZnakovaNaslov;

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
              String sazetak = x.getElementsByClass("summary").html();
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
    List<String> linkoviNaClanake = elements.stream()
            .map(x -> baseUrlIndex + x.getElementsByClass("sport-text-hover").get(0).attr("href"))
            .collect(Collectors.toList());

    return linkoviNaClanake;
  }

  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> "prije " + x.parent().getElementsByClass("num").html() + " " +
                    x.parent().getElementsByClass("desc").html())
            .collect(Collectors.toList());

    return vremenaObjave;
  }

  @Override
  public void createElements(String stranica) throws IOException {
    if (elements!=null)
      elements.clear();

    doc = Jsoup.connect("https://www.index.hr/najnovije?kategorija=150").get();
    // trazenje svih elementata klase "title-box"
    elements = doc.getElementsByClass("title-box");
  }
}
