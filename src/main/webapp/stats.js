function getStats()
{
    var inputCountry = document.getElementById("inputCountry").value;
    fetch('./api/stats/' + inputCountry)
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