package com.example.Covid_Stats;

import com.example.Covid_Stats.db.CovidDB;
import com.example.Covid_Stats.singlecountry.HistoricStats;
import com.example.Covid_Stats.singlecountry.Stats;
import com.example.Covid_Stats.strategy.CurrentDataStrategy;
import com.example.Covid_Stats.strategy.HistoryDataStrategy;
import com.example.Covid_Stats.strategy.StatStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * Used as the 'Navigator'-class from the strategy pattern
 */
@Path("/stats")
public class CovidResource {

    private static StatStrategy strategy;

    /**
     * Method to load current data for a specified country
     * @param country Name of the selected country
     * @param iso3 ISO3 of the selected country
     * @return HTML page with output of the data
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{c}/{iso}")
    public Response loadData(@PathParam("c") String country, @PathParam("iso") String iso3)
    {
        try {
            strategy = new CurrentDataStrategy();
            strategy.loadCovidData(country, iso3);
            ArrayList<Stats> stats = strategy.getStats();

            //ObjectMapper om = new ObjectMapper();
            //String data = om.writeValueAsString(stats.toString());


            return Response.ok(stats).build();
        }
        catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{c}/{iso}/{t}")
    public Response loadData(@PathParam("c") String country, @PathParam("iso") String iso3, @PathParam("t") String h)
    {
        try {
            strategy = new HistoryDataStrategy();
            strategy.loadCovidData(country, iso3);
            ArrayList<Stats> stats = strategy.getStats();

            return Response.ok(stats).build();
        }
        catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}