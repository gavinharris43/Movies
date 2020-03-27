let map;
function initMap() {
    let myLatLng = {lat: 53.474245, lng: -2.286474};
    map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        zoom: 18
    });
    let marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        title: 'KinoPlex'
    });
}
