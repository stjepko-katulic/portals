package com.example.scrapping.models.ict;

import com.example.scrapping.models.IPortal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component(value = "ictModelVijesti")
public class IctModelVijesti implements IPortal {

  Elements elements = new Elements();
  String baseUrlIctBusiness = "https://www.ictbusiness.info/";

  @Value(value = "${properties.max-broj-znakova.sazetak}")
  int maxBrojZnakovaSazetak;

  @Value(value = "${properties.max-broj-znakova.naslov}")
  int maxBrojZnakovaNaslov;

  @Override
  public List<String> getNasloviClanaka() {
    List<String> nasloviClanaka = elements.stream()
            .map(x -> {
              String naslov = x.getElementsByClass("item-content").get(0)
                      .getElementsByTag("a").get(0).html();
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
              String sazetak = x.getElementsByClass("item-content").get(0)
                      .getElementsByTag("p").get(0).html();
              sazetak = sazetak.replace("&nbsp;", " ");
              sazetak = sazetak.replace("&amp;", "&");

              if (sazetak.length() > maxBrojZnakovaSazetak) {
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
            .map(x -> x.getElementsByClass("item-content").get(0)
                    .getElementsByTag("a").get(0).attr("href"))
            .collect(Collectors.toList());
    return linkoviNaClanke;
  }

  @Override
  public List<String> getVremenaObjave() {
    List<String> vremenaObjave = elements.stream()
            .map(x -> {
              String vrijemeObjave = x.getElementsByClass("item-meta-i").html();
              vrijemeObjave = vrijemeObjave.substring(vrijemeObjave.length()-11, vrijemeObjave.length());
              return vrijemeObjaveConverter("00:00 " + vrijemeObjave);
            })
            .collect(Collectors.toList());
    return vremenaObjave;
  }

  @Override
  public void createElements(String stranica) throws IOException {
    if (elements!=null)
      elements.clear();

    String skip = Integer.toString((Integer.parseInt(stranica) - 1) * 30);

    Document document = Jsoup.connect("https://www.ictbusiness.info/vijesti/?skip=" + skip).get();
    Elements elementsX = document.getElementsByClass("main-content-block").get(0)
            .getElementsByClass("item");
    elements.addAll(elementsX);
  }


  private String vrijemeObjaveConverter(String vrijemeObjave) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy.");
    LocalDateTime dateTime = LocalDateTime.parse(vrijemeObjave, formatter);
    Duration razlika = Duration.between(dateTime, LocalDateTime.now());

    long prijeDana = razlika.toDays();
    String objavljenoPrije = "prije " + prijeDana + " dana";

    return objavljenoPrije;
  }
}
