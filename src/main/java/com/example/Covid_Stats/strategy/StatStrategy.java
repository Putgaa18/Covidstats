package com.example.Covid_Stats.strategy;

import com.example.Covid_Stats.db.CovidDB;
import com.example.Covid_Stats.singlecountry.Stats;

/**
 * Common interface for all strategies.
 */
public interface StatStrategy {
    void loadCovidData(String country, String iso3);
    Stats getStats();
}
