var connectionStatus = false;

$(document).ready(function () {
    $.fn.dataTable.ext.search.push(
        function (settings, data, dataIndex) {
            var filterDate = $('#dateFilter').val();
            var startDate = data[3];
            if (filterDate == "") return true;
            if (filterDate == startDate) return true;
            return false;
        }
    );
})

function loading() {
    $('#nest5').addClass('dialog');
    // $('#colorBalls').addClass('colorBalls');
}

function hideLoading() {
    $('#nest5').removeClass('dialog');
    // $('#colorBalls').removeClass('colorBalls');
    connectionStatus = false;
}

(function(){
	getPersonList();
	firebaseInit();
})();

// Initialize and add the map
var marker;
function initMap() {
  // The location of Uluru
  const uluru = { lat: 16.840939, lng: 96.173526 };
  // The map, centered at Uluru
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: 15,
    center: uluru,
  });
  // The marker, positioned at Uluru
  marker = new google.maps.Marker({
    position: uluru,
    map: map,
  });

// Create the initial InfoWindow.
  let infoWindow = new google.maps.InfoWindow({
    content: "Click the map to get Lat/Lng!",
    position: uluru,
  });
  infoWindow.open(map);
  // Configure the click listener.
  map.addListener("click", (mapsMouseEvent) => {
    // Close the current InfoWindow.
    infoWindow.close();
    // assign the lat:long coordinate to textbox
    let latLng = mapsMouseEvent.latLng.toJSON();
	  $("#latText").val(latLng.lat);
	  $("#langText").val(latLng.lng);
    // Create a new InfoWindow.
    infoWindow = new google.maps.InfoWindow({
      position: mapsMouseEvent.latLng,
    });
    infoWindow.setContent(
      JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
    );
    infoWindow.open(map);
  });
}