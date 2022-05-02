package com.example.Covid_Stats;

import com.example.Covid_Stats.db.CovidDB;
import com.example.Covid_Stats.singlecountry.Stats;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.NoSuchElementException;

@Path("/stats")
public class CovidResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{c}/{iso}")
    public Response loadData(@PathParam("c") String country, @PathParam("iso") String iso3)
    {
        try {
            CovidDB.getInstance().loadCovidData(country, iso3);
            Stats stats = CovidDB.getInstance().getStats();
            return Response.ok(stats).build();
        }
        catch (NoSuchElementException nsee) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}