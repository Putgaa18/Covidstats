package com.example.Covid_Stats.singlecountry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "root")
public class HistoricStats {

    /*
    Unused
     */

    @XmlElement(name = "Country")
    private String country;
    @XmlElement(name = "date")
    private String date;
    @XmlElement(name = "new_cases")
    private String newcases;
    @XmlElement(name = "new_deaths")
    private String newdeaths;
    @XmlElement(name = "total_cases")
    private String totalcases;
}
