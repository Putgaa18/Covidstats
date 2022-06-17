package com.example.Covid_Stats.strategy;

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
 * Covid-Stats
 * Concrete strategy. Implements the current data of a country. The methods of this class are called by the
 * 'CovidResource' class and an ArrayList with all the data is being returned in a Getter-Function
 * @author putgaa18
 * @version 1.0
 * date: 17.06.2022
 */
public class CurrentDataStrategy implements StatStrategy{

    private ArrayList<Stats> stats = new ArrayList<>();

    /**
     * This method fills the ArrayList with the data from the 'VACCOVID-API'. For the API-call the name and iso3 of the
     * country is used. The JSON response from the API is saved on a String, which will be converted to XML. To
     * save the data a root-element has to be added at the beginning and at the end of the String. Now the String will
     * be unmarshalled and added to the ArrayList.
     * @param country Name of the selected country, which is needed to set a correct API-call
     * @param iso3 ISO3 of the selected country, which is needed to set a correct API-call
     */
    @Override
    public void loadCovidData(String country, String iso3) {
        try {
            // Input with name of the country and iso (three-letter-code)
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
