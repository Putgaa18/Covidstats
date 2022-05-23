package com.example.Covid_Stats.strategy;

import com.example.Covid_Stats.db.CovidDB;
import com.example.Covid_Stats.singlecountry.Stats;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Concrete strategy. Implements the current data of a country.
 */
public class CurrentDataStrategy implements StatStrategy{

    private CurrentDataStrategy currentDataStrategy;

    private ArrayList<Stats> stats = new ArrayList<>();

    public CurrentDataStrategy getCurrentDataStrategy()
    {
        return currentDataStrategy;
    }

    @Override
    public void loadCovidData(String country, String iso3) {
        try {
            // Input with name of the country and iso (three-letter-code)
            // At the moment the iso is still hardcoded, it should be getted through the selection of a country
            HttpResponse response = Unirest.get("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p." +
                    "rapidapi.com/api/npm-covid-data/country-report-iso-based/" + country + "/" + iso3)
                    .header("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p." +
                            "rapidapi.com")
                    .header("x-rapidapi-key", "b9e5b6b837msh0d164cdd8603773p15f8e8jsnfd2a93515373")
                    .asString();

            // Get response from API
            String str = (String) response.getBody();
            // Convert JSON to XML
            String jsonStr = str.substring(1, str.length() - 1);
            JSONObject json = new JSONObject(jsonStr);
            String xmlStr = XML.toString(json);

            // Add essential Rootelement in order to properly call it
            xmlStr += "</root>";
            String newXmlStr = "<root>" + xmlStr;

            // Unmarshall the xml-file
            JAXBContext jaxbContext = JAXBContext.newInstance(Stats.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(newXmlStr);
            stats.add((Stats) unmarshaller.unmarshal(reader));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Stats> getStats() {return stats;}
}
