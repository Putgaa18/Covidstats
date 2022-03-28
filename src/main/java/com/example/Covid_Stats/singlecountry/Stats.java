package com.example.Covid_Stats.singlecountry;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
public class Stats {
    @XmlElement(name = "0")
    private SpecifiedCountry sp;

    public static void main(String[] args) {
        try {
            HttpResponse response = Unirest.get("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p." +
                    "rapidapi.com/api/npm-covid-data/country-report-iso-based/Austria/aut")
                    .header("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p." +
                            "rapidapi.com")
                    .header("x-rapidapi-key", "b9e5b6b837msh0d164cdd8603773p15f8e8jsnfd2a93515373")
                    .asString();

            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    " <root>\n" +
                    "     <0>\n" +
                    "         <Country>Canada</Country>\n" +
                    "     </0>\n" +
                    " </root>";

            //InputStream is = response.getRawBody();
            String str = (String) response.getBody();
            System.out.println(str);
            //Stats stats2 = JAXB.unmarshal(str, Stats.class);
            //System.out.println(stats2);
            String jsonStr = str.substring(1, str.length() - 1);
            JSONObject json = new JSONObject(jsonStr);
            String xmlStr = XML.toString(json);
            System.out.println(xmlStr);

            JAXBContext  jaxbContext = JAXBContext.newInstance(Stats.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xmlStr);
            Stats stats3 = (Stats) unmarshaller.unmarshal(reader);
            System.out.println(stats3);

            //Stats stats = JAXB.unmarshal(xmlStr, Stats.class);
            //System.out.println(stats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
