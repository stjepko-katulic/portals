package com.example.scrapping.models.net;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class NetModelSport implements IPortal {

  String baseUrlNarod = "www.net.hr";
  Elements elements = new Elements();

  public NetModelSport() throws IOException {
    int brojStranica=2;

    for (int i=1; i<=brojStranica; i++) {
      // nogomet
      Document document = Jsoup.connect("https://net.hr/kategorija/sport/nogomet/page/" + i).get();
      Elements elementsX = document.getElementsByClass("article-feed");
      elements.addAll(elementsX);

      // ostali sportovi
      document = Jsoup.connect("https://net.hr/kategorija/sport/ostali-sportovi/page/" + i).get();
      elementsX = document.getElementsByClass("article-feed");
      elements.addAll(elementsX);
    }
  }



  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> x.getElementsByClass("title").get(0).html())
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
  public List<String> getLinkoviSlikeClanaka() {
    List<String> linkoviSlikeClanaka = elements.stream()
            .map(x -> x.getElementsByTag("img").get(0).attr("src"))
            .collect(Collectors.toList());
    return linkoviSlikeClanaka;
  }

  @Override
  public List<String> getLinkoviNaClanke() {
    List<String> linkoviNaClanke = elements.stream()
            .map(x -> x.getElementsByTag("a").get(0).attr("href"))
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
