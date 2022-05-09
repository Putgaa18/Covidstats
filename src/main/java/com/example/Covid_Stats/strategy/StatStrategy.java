package com.example.Covid_Stats.strategy;

import com.example.Covid_Stats.singlecountry.Stats;

public interface StatStrategy {
    void loadCovidData(String country, String iso3);
    Stats getStats();
}
