/* Google Maps */

jQuery(function($) {
    // Asynchronously Load the map API 
    var script = document.createElement('script');
    script.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyAhxzq8BDIzBZY5WPu5cBcuNWmlrixp3r8&callback=initialize";
    document.body.appendChild(script);
});

function initialize() {
    var map,
    	position,
    	boundsListener,
    	bounds = new google.maps.LatLngBounds(),
    	mapOptions = {
        	mapTypeId: 'roadmap'
    	},
    	markers = [
                     ['Andy Livingstone Park', 49.278660, -123.103361],
    	             ['China Creek Park North', 49.2648382, -123.0845981],
    	             ['Vancouver Technical Secondary', 49.2605065, -123.0521023]
    	         ],
    	infoWindowContent = [
                     ['<div class="info_content">' +
	                  '<h4>Andy Livingstone Park</h4>' +
	                  '<p>Men\'s and Women\'s Training Pitch.</p>' +
	                  '</div>'],
    	             ['<div class="info_content">' +
    	              '<h4>China Creek Park North</h4>' +
    	              '<p>Men\'s A and B Home Pitch.</p>' +
    	              '</div>'],
    	             ['<div class="info_content">' +
    	              '<h4>Vancouver Technical Secondary</h4>' +
    	              '<p>Women\'s and Men\'s Jurassic Home Pitch.</p>' +
    	              '</div>']
    	         ],
    	infoWindow = new google.maps.InfoWindow(), marker, i;
    	         
                    
    // Display a map on the page
    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    map.setTilt(45);
    
        
    // Display multiple markers on a map
    
    
    // Loop through our array of markers & place each one on the map  
    for( i = 0; i < markers.length; i++ ) {
        position = new google.maps.LatLng(markers[i][1], markers[i][2]);
        bounds.extend(position);
        marker = new google.maps.Marker({
            position: position,
            map: map,
            title: markers[i][0]
        });
        
        // Allow each marker to have an info window    
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
                infoWindow.setContent(infoWindowContent[i][0]);
                infoWindow.open(map, marker);
            }
        })(marker, i));

        // Automatically center the map fitting all markers on the screen
        map.fitBounds(bounds);
    }

    // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
    boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
        this.setZoom(12);
        google.maps.event.removeListener(boundsListener);
    });
    
}