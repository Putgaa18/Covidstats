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
 * Concrete strategy. Implements the historic data of a country.
 */
public class HistoryDataStrategy implements StatStrategy{

    private HistoryDataStrategy historyDataStrategy;

    private ArrayList<Stats> stats = new ArrayList<>();

    public HistoryDataStrategy getHistoryDataStrategy()
    {
        return historyDataStrategy;
    }

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
                    System.out.println(splitedStr[i]);
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
