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
                console.log(data);
                allcountryList = data;
                var countryList = document.getElementById("countrylist");
                for(var i = 0;i < data.country.length;i++){
                    var currentiso = (data.country[i].iso).toLowerCase();
                    var currentName = (data.country[i].countryname).toLowerCase();
                    var current = (data.country[i].iso3).toLowerCase();
                    var cname = currentName.charAt(0).toUpperCase() + currentName.slice(1);

                    if(cname.length < 30)
                    {
                        //var onclickTag = document.createElement("img");
                        //onclickTag.addEventListener(fetchStats(currentName, current));
                        var start = "<li>\n" +
                            "            <div>\n" +
                            "<div class=\"textdiv\">\n" +
                            "    <p class=\"name\">Country name:</p>\n" +
                            "    <a class=\"actualName\">" + data.country[i].nicename + "</a>\n" +
                            "</div>" +
                            "                <div class=\"imgC\">\n" +
                            "                    <img onclick='location.href=\"http://localhost:8080/Covid-Stats-1.0-SNAPSHOT/api/stats/" + cname + "/" + current + "\"' class=\"countryImage\" " +
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
    alert(currentname);
    alert(currentiso3);
    fetch("./api/stats/").then(
        function (response) {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            response.json().then(function (data) {
                console.log(data);

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

function getStats(useiso)
{
    alert(useiso);
    var inputCountry = document.getElementById("inputCountry").value;
    fetch('./api/stats/' + inputCountry + '/' + currentiso3)
        .then(
            function(response ){
                if(response.status !== 200)
                {
                    console.log('Looks like there was a problem: ' + response.status);
                    return;
                }
                response.json().then(function(data){
                    var info = document.getElementById("outData");
                    info.innerText = "Country: " + data.country;
                    info.innerHTML += "<br/>";
                    info.innerText += "ThreeLetter: " + data.threeLetter;
                    info.innerHTML += "<br/>";
                    info.innerText += "Total cases: " + data.totalcases;
                    info.innerHTML += "<br/>";
                    info.innerText += "New cases: " + data.newcases;
                    info.innerHTML += "<br/>";
                    info.innerText += "Total deaths: " + data.totaldeaths;
                    info.innerHTML += "<br/>";
                    info.innerText += "New deaths: " + data.newdeaths;
                    info.innerHTML += "<br/>";
                    info.innerText += "Rank: " + data.rank;
                })
                    .catch(function (err){
                        alert("This country does not exist");
                        console.log(err);
                    });
            }
        );
}
