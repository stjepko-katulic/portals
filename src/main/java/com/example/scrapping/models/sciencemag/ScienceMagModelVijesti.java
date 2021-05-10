package com.example.scrapping.models.sciencemag;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component(value = "scienceModelVijesti")
public class ScienceMagModelVijesti implements IPortal {

  Elements elements = new Elements();
  String baseUrlScience = "https://www.sciencemag.org";

  @Value(value = "${properties.max-broj-znakova.sazetak}")
  int maxBrojZnakovaSazetak;

  @Value(value = "${properties.max-broj-znakova.naslov}")
  int maxBrojZnakovaNaslov;

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> {
              String naslov = x.getElementsByTag("h2").get(0).getElementsByTag("a").get(0).html();
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
              String sazetak = x.getElementsByClass("media__deck").get(0).html();
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
            .map(x -> baseUrlScience + x.getElementsByTag("h2").get(0)
            .getElementsByTag("a").get(0).attr("href"))
            .collect(Collectors.toList());
    return linkoviNaClanke;
  }

  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> {
              String vrijemeObjave = "00:00 " + x.getElementsByTag("time").html();
              vrijemeObjave = vrijemeObjave.replace(".", "");

              if (vrijemeObjave.charAt(12)!=',') {
                vrijemeObjave=vrijemeObjave.substring(0,10) + "0" + vrijemeObjave.substring(10);
              }

              return vrijemeObjaveConverter(vrijemeObjave);
            })
            .collect(Collectors.toList());
    return vremenaObjave;
  }


  @Override
  public void createElements(String stranica) throws IOException {
    if (elements!=null)
      elements.clear();

    Document document = Jsoup.connect("https://www.sciencemag.org/news/latest-news%20/h?page=0" + stranica).get();
    elements = document.getElementsByClass("view-article-lists-page-3").get(0)
            .getElementsByClass("media__body");
  }


  private String vrijemeObjaveConverter(String vrijemeObjave) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm MMM d, yyyy", Locale.ENGLISH);
    LocalDateTime dateTime = LocalDateTime.parse(vrijemeObjave, formatter);
    Duration razlika = Duration.between(dateTime, LocalDateTime.now());
    long prijeDana = razlika.toDays();
    String objavljenoPrije = "prije " + prijeDana + " dana";
    return objavljenoPrije;
  }
}
