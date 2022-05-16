package com.example.Covid_Stats;

import com.example.Covid_Stats.db.CovidDB;
import com.example.Covid_Stats.singlecountry.Stats;
import com.example.Covid_Stats.strategy.CurrentDataStrategy;
import com.example.Covid_Stats.strategy.StatStrategy;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
            Stats stats = strategy.getStats();

            return Response.ok(stats).build();
        }
        catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}