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
 * Concrete strategy. Implements the historic data of a country. The methods of this class are called by the
 * 'CovidResource' class and an ArrayList with all the data is being returned in a Getter-Function
 * @author putgaa18
 * @version 1.0
 * date: 17.06.2022
 */
public class HistoryDataStrategy implements StatStrategy{

    private ArrayList<Stats> stats = new ArrayList<>();

    /**
     * This method fills the ArrayList with the data from the 'VACCOVID-API'. For the API-call the name and iso3 of the
     * country is used. This api call differs from the one called in 'CurrentDataStrategy'. The JSON response from the
     * API is saved on a String, which will be converted to XML.
     * In order to add multiple statistics to the ArrayList the String must be splitted into an array. A loop goes
     * through the entire array. And adds the String '{"date"}' at the beginning. To save the data a root-element has to
     * be added at the beginning and at the end of the single String in the array. Now a single String will be
     * unmarshalled and added to the ArrayList.
     * @param country Name of the selected country, which is needed to set a correct API-call
     * @param iso3 ISO3 of the selected country, which is needed to set a correct API-call
     */
    @Override
    public void loadCovidData(String country, String iso3) {
        try {
            // Input with name of the country and iso (three-letter-code)
            // At the moment the iso is still hardcoded, it should be getted through the selection of a country
            HttpResponse response = Unirest.get("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p." +
                                                "rapidapi.com/api/covid-ovid-data/sixmonth/" + iso3)
                    .header("x-rapidapi-host", "vaccovid-coronavirus-vaccine-and-treatment-tracker.p." +
                            "rapidapi.com")
                    .header("x-rapidapi-key", "3189e74810mshf086f44fa17a27ap1f0f46jsne71def0cf46d")
                    .asString();

            // Get response from API
            String str = (String) response.getBody();
            // Convert JSON to XML
            String jsonStr = str.substring(1, str.length() - 1);

            // Split the string into array, to add it into the arraylist
            String splitedStr[] = jsonStr.split("\"date\"");

            JSONObject json;
            String xmlStr;
            String newXmlStr;
            StringReader reader;
            JAXBContext jaxbContext;
            Unmarshaller unmarshaller;
            for(int i = 0; i < splitedStr.length; i++)
            {
                if(i >= 1)
                {
                    splitedStr[i] = "{\"date\"" + splitedStr[i] + "}";
                    json = new JSONObject(splitedStr[i]);
                    xmlStr = XML.toString(json);

                    // Add essential Rootelement in order to properly call it
                    xmlStr += "</root>";
                    newXmlStr = "<root>" + xmlStr;

                    // Unmarshall the xml-file
                    jaxbContext = JAXBContext.newInstance(Stats.class);
                    unmarshaller = jaxbContext.createUnmarshaller();

                    reader = new StringReader(newXmlStr);
                    stats.add((Stats) unmarshaller.unmarshal(reader));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Stats> getStats() {return stats;}
}
