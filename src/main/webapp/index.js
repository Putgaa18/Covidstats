let allcountryList = "";

function loadCountryList(){
    fetch("https://country-list5.p.rapidapi.com/countrylist/", {
        "method": "GET",
        "headers": {
            "x-rapidapi-host": "country-list5.p.rapidapi.com",
            "x-rapidapi-key": "a374e6a7a6msh2923c26863d4df9p183d01jsn996f585a4ded"
        }
    }).then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            response.json().then(function (data) {
                //console.log(data);
                allcountryList = data.country;
                var countryList = document.getElementById("countrylist");
                for(var i = 0;i < data.country.length;i++){
                    var currentiso = (data.country[i].iso).toLowerCase();
                    var currentName = (data.country[i].countryname).toLowerCase();
                    let current = (data.country[i].iso3).toLowerCase();
                    let cname = currentName.charAt(0).toUpperCase() + currentName.slice(1);

                    //console.log(allcountryList[i]);
                    if(cname.length < 30)
                    {

                        var functionCall = "fetchStats(\""+cname+"\",\""+current+"\")";

                        var start = "<li>\n" +
                            "            <div>\n" +
                            "<div class=\"textdiv\">\n" +
                            "    <p class=\"name\">Country name:</p>\n" +
                            "    <a class=\"actualName\">" + data.country[i].nicename + "</a>\n" +
                            "</div>" +
                            "                <div class=\"imgC\">\n" +
                            "                    <img id='cImage' onclick="+functionCall+" class=\"countryImage\" " +
                            "                       src=\"https://flagcdn.com/w80/" + currentiso + ".png\">\n" +
                            "                </div>\n" +
                            "                <div class=\"textDiv\">\n" +
                            "                    <h5></h5>\n" +
                            "                    <p></p>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </li>";

                        countryList.insertAdjacentHTML("afterend", start);
                    }
                }
            })
        }
    )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

function fetchStats(currentname, currentiso3) {
    fetchCurrent(currentname, currentiso3)
    fetchHistory(currentname, currentiso3);
}

function fetchCurrent(currentname, currentiso3) {
    fetch("./api/stats/" + currentname + "/" + currentiso3 + "/").then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            response.json().then(function (data) {
                console.log(data);
                loadPie1(data[0].activecases, data[0].newdeaths, data[0].newcases);
                loadPie2(data[0].totalRecovered, data[0].totaldeaths, data[0].population);


                var info = document.getElementById("genInfo");
                info.innerText += "\n\nCountryname: " + currentname + "\nIso3: " + currentiso3 +
                    "\nPopulation: " + data[0].population + "\nAll time deaths: " + data[0].totaldeaths;
            })
        }
    )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

function fetchHistory(currentname, currentiso3) {
    fetch("./api/stats/" + currentname + "/" + currentiso3 + "/t").then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            response.json().then(function (data) {
                console.log(data);
                loadLineOne(data[0].new_cases, data[1].new_cases, data[2].new_cases, data[3].new_cases,
                    data[4].new_cases, data[5].new_cases, data[6].new_cases, data[7].new_cases, data[8].new_cases,
                    data[8].new_cases, data[10].new_cases);
            })
        }
    )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

function loadCountryVisual(){

    for(let i = 0;i < 1000; i++){
        alert(allcountryList.toArray());
    }
}

function openNav() {
    document.getElementById("mySidenav").style.width = "27.5em";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function filterlist() {
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById("keyWordInput");
    filter = input.value.toUpperCase();
    li = document.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

function loadTop5(){
    var top5List = document.getElementById("top5");
    fetch("https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/npm-covid-data/", {
        method: 'GET',
        headers: {
            'X-RapidAPI-Host': 'vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com',
            'X-RapidAPI-Key': 'a374e6a7a6msh2923c26863d4df9p183d01jsn996f585a4ded'
        }
    }).then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            response.json().then(function (data) {
                for(var i = 2;i < data.length; i++){
                    if(data[i].rank <= 5){
                        var litag = document.createElement("li");
                        litag.innerText = data[i].Country + " ~ Total Cases: " + data[i].TotalCases;
                        top5List.append(litag);
                    }
                }
            })
        }
    )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

let myChart = null;
function loadPie1(activeCases, activeDeaths, newCases){
    if(myChart!=null){
        myChart.destroy();
    }

    const ctx = document.getElementById('pi1').getContext('2d');
    myChart = new Chart(ctx, {
        type: 'doughnut',
        data: data = {
            labels: [
                'Fälle (aktuell)',
                'Todesfälle (aktuell)',
                'Neue Fälle (aktuell)'
            ],
            datasets: [{
                label: 'My First Dataset',
                data: [activeCases, activeDeaths, newCases],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

}
let piechart2 = null;
function loadPie2(totalrec, totaldeaths, pop){
    if(piechart2!=null){
        piechart2.destroy();
    }
    const ctx = document.getElementById('pi2').getContext('2d');
    piechart2 = new Chart(ctx, {
        type: 'doughnut',
        data: data = {
            labels: [
                'Genesen (insgesammt)',
                'Todesfälle (insgesammt)',
                'Bevölkerung'
            ],
            datasets: [{
                label: 'My First Dataset',
                data: [totalrec, totaldeaths, pop],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ],
                hoverOffset: 4
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

let line_chart = null;
function loadLineOne(new0, new1, new2, new3, new4, new5, new6, new7, new8, new9, new10){
    if(line_chart!=null){
        line_chart.destroy();
    }
    line_chart = new Chart(document.getElementById("line1"), {
        type: 'line',
        data: {
            labels: ["Vor 10 Tagen", "Vor 9 Tagen", "vor 8 Tagen", "vor 7 Tagen", "vor 6 Tagen", "vor 5 Tagen",
                "vor 4 Tagen", "vor 3 Tagen", "vor 2 Tagen", "vor 1 Tag"],
            datasets: [{
                data: [new0,new1,new2,new3,new4,new5,new6,new7,new8,new9, new10],
                label: "New Cases",
                borderColor: "#3e95cd",
                fill: false
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: 'Neue Fälle in den letzten 10 Tagen'
            }
        }
    });
}
