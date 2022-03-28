package com.example.Covid_Stats.singlecountry;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Stats {
    @XmlElement(name = "Country")
    private String country;
    @XmlElement(name = "ThreeLetterSymbol")
    private String threeLetter;
    @XmlElement(name = "TotalCases")
    private String totalcases;
    @XmlElement(name = "NewCases")
    private String newcases;
    @XmlElement(name = "TotalDeaths")
    private String totaldeaths;
    @XmlElement(name = "NewDeaths")
    private String newdeaths;
    private String rank;
}
