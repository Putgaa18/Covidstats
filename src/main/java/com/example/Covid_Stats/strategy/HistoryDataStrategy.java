package com.example.Covid_Stats.strategy;

import com.example.Covid_Stats.db.CovidDB;
import com.example.Covid_Stats.singlecountry.Stats;

public class HistoryDataStrategy implements StatStrategy{
    private static CovidDB theInstance;

    private Stats stats;

    public synchronized static CovidDB getInstance()
    {
        if (theInstance == null)
        {
            theInstance = new CovidDB();
        }

        return theInstance;
    }

    @Override
    public void loadCovidData(String country, String iso3) {

    }

    @Override
    public Stats getStats() {return stats;}
}
