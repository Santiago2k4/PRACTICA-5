var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
                    osmAttrib = '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
                    osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});

            var map = L.map('map').setView([-4.036, -79.201], 15);

            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(map);
L.marker([-4.28735738764392,-78.92995127132221]).addTo(map)
.bindPopup('Estacion 1')
.openPopup();
L.marker([-4.286684352689373,-78.99215177121255]).addTo(map)
.bindPopup('Estacion 2')
.openPopup();
L.marker([-4.305326698085269,-78.99976819996948]).addTo(map)
.bindPopup('Estacion 3')
.openPopup();
L.marker([-4.30555684764806,-78.98730495291268]).addTo(map)
.bindPopup('Estacion 4')
.openPopup();
L.marker([-4.2896648142012435,-79.03257080063466]).addTo(map)
.bindPopup('Estacion 5')
.openPopup();
L.marker([-4.310951853233976,-79.04374469224832]).addTo(map)
.bindPopup('Estacion 6')
.openPopup();
L.marker([-4.331937702148664,-79.03665039956974]).addTo(map)
.bindPopup('Estacion 7')
.openPopup();
L.marker([-4.354622267919701,-78.96895478261824]).addTo(map)
.bindPopup('Estacion 8')
.openPopup();
L.marker([-4.3262289775745435,-78.96129765156626]).addTo(map)
.bindPopup('Estacion 9')
.openPopup();
L.marker([-4.29735739764392,-78.92995127132221]).addTo(map)
.bindPopup('Estacion 10')
.openPopup();
