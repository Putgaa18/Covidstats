package com.example.Covid_Stats;

import com.example.Covid_Stats.singlecountry.Stats;
import com.example.Covid_Stats.strategy.CurrentDataStrategy;
import com.example.Covid_Stats.strategy.HistoryDataStrategy;
import com.example.Covid_Stats.strategy.StatStrategy;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;



/**
 * Covid-Stats
 * This class is used as the 'Navigator'-class from the strategy pattern
 * @author putgaa18
 * @version 1.0
 * date: 17.06.2022
 */
@Path("/stats")
public class CovidResource {

    private static StatStrategy strategy;

    /**
     * Method to load the current data for a specified country and return them to a JavaScript-Function
     * @param country Name of the selected country
     * @param iso3 ISO3 of the selected country
     * @return ArrayList with all the useful statistics of the country, which will later be displayed on an html page
     * through a JavaScript-Function
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{c}/{iso}")
    public List loadData(@PathParam("c") String country, @PathParam("iso") String iso3)
    {
        try {
            strategy = new CurrentDataStrategy();
            strategy.loadCovidData(country, iso3);
            ArrayList<Stats> stats = strategy.getStats();

            return stats;
        }
        catch (NoSuchElementException nsee) {
            return null;
        }

    }

    /**
     * Method to load the historic data for a specified country and return them to a JavasScript-function
     * @param country Name of the selected country
     * @param iso3 ISO3 of the selected country
     * @param h An extra parameter in the path in order to know that the historic stats have to be called
     * @return ArrayList with all the useful statistics of the country from the last 10 days
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{c}/{iso}/{t}")
    public List loadData(@PathParam("c") String country, @PathParam("iso") String iso3, @PathParam("t") String h)
    {
        try {
            strategy = new HistoryDataStrategy();
            strategy.loadCovidData(country, iso3);
            ArrayList<Stats> stats = strategy.getStats();

            return stats;
        }
        catch (NoSuchElementException nsee) {
            return null;
        }
    }
}