package com.example.scrapping.models.net;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "netModelVijesti")
public class NetModelVijesti implements IPortal {

  String baseUrlNarod = "www.net.hr";
  Elements elements = new Elements();
  String stranica;

  @Value(value = "${properties.max-broj-znakova.naslov}")
  int maxBrojZnakovaNaslov;

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> {
              String naslov = x.getElementsByClass("title").get(0).html();
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
            .map(x -> "")
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
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> {
              String vrijemeObjave = x.getElementsByClass("undertitle").html().substring(0,16);
              return vrijemeObjaveConverter(vrijemeObjave);
            })
            .collect(Collectors.toList());
    return vremenaObjave;
  }

  @Override
  public void createElements(String stranica) throws IOException {
    if (elements!=null)
      elements.clear();

    // vijesti iz hrvatske
    Document document = Jsoup.connect("https://net.hr/kategorija/danas/hrvatska/page/" + stranica).get();
    Elements elementsX = document.getElementsByClass("article-feed");
    elements.addAll(elementsX);

    // vijesti iz svijeta
    document = Jsoup.connect("https://net.hr/kategorija/danas/svijet/page/" + stranica).get();
    elementsX = document.getElementsByClass("article-feed");
    elements.addAll(elementsX);
  }


  private String vrijemeObjaveConverter(String vrijemeObjave) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
    LocalDateTime dateTime = LocalDateTime.parse(vrijemeObjave, formatter);
    Duration razlika = Duration.between(dateTime, LocalDateTime.now());

    long prijeDana = razlika.toDays();
    long prijeSati = razlika.toHours();
    long orijeMinuta = razlika.toMinutes();

    String objavljenoPrije;

    if (prijeDana!=0L) {
      objavljenoPrije = "prije " + prijeDana + " dana";
    } else if (prijeSati!=0L) {
      objavljenoPrije = "prije " + prijeSati + " h";
    } else {
      objavljenoPrije = "prije " + orijeMinuta + " min";
    }

    return objavljenoPrije;
  }

}
