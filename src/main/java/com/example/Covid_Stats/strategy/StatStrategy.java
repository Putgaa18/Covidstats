package com.example.Covid_Stats.strategy;

import com.example.Covid_Stats.singlecountry.Stats;

import java.util.ArrayList;

/**
 * Common interface for all strategies. The 'CovidResource' class knows which concrete strategy needs to be called.
 */
public interface StatStrategy {
    void loadCovidData(String country, String iso3);
    ArrayList<Stats> getStats();
}
