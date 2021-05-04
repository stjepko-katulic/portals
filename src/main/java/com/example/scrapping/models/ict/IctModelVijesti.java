package com.example.scrapping.models.ict;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IctModelVijesti implements IPortal {

  Elements elements = new Elements();
  String baseUrlIctBusiness = "https://www.ictbusiness.info/";

  public IctModelVijesti(String stranica) throws IOException {

     String skip = Integer.toString((Integer.parseInt(stranica)-1)*30);

      Document document = Jsoup.connect("https://www.ictbusiness.info/vijesti/?skip=" + skip).get();
      Elements elementsX = document.getElementsByClass("main-content-block").get(0)
              .getElementsByClass("item");
      elements.addAll(elementsX);

  }



  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> {
              String naslov = x.getElementsByClass("item-content").get(0)
                      .getElementsByTag("a").get(0).html();
              naslov = naslov.replace("&nbsp;", " ");
              naslov = naslov.replace("&amp;", "&");
              return naslov;
            })
            .collect(Collectors.toList());
    return nasloviClanaka;
  }

  @Override
  public List<String> getSazetciClanaka() {
    List<String> sazetciClanaka = elements.stream()
            .map(x -> {
              String sazetak = x.getElementsByClass("item-content").get(0)
                      .getElementsByTag("p").get(0).html();
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
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviNaSlike = elements.stream()
            .map(x -> baseUrlIctBusiness + x.getElementsByTag("img").get(0).attr("src"))
            .collect(Collectors.toList());
    return linkoviNaSlike;
  }

  @Override
  public List<String> getLinkoviNaClanke() {
    List<String> linkoviNaClanke = elements.stream()
            .map(x -> x.getElementsByClass("item-content").get(0)
                    .getElementsByTag("a").get(0).attr("href"))
            .collect(Collectors.toList());
    return linkoviNaClanke;
  }

  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> "")
            .collect(Collectors.toList());
    return vremenaObjave;
  }
}
