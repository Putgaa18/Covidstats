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
                    var start = "<li>\n" +
                        "            <div>\n" +
                        "<div class=\"textdiv\">\n" +
                        "    <p class=\"name\">Country name:</p>\n" +
                        "    <a class=\"actualName\">" + data.country[i].nicename + "</a>\n" +
                        "</div>" +
                        "                <div class=\"imgC\">\n" +
                        "                    <img class=\"countryImage\" src=\"https://flagcdn.com/w80/" + currentiso + ".png\">\n" +
                        "                </div>\n" +
                        "                <div class=\"textDiv\">\n" +
                        "                    <h5></h5>\n" +
                        "                    <p></p>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </li>";
                    countryList.insertAdjacentHTML("afterend", start);
                }
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