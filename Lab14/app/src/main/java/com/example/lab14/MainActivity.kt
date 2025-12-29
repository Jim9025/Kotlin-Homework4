override fun onMapReady(map: GoogleMap) {
    val fineGranted = ActivityCompat.checkSelfPermission(
        this, android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!fineGranted) {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
        )
        return
    }

    map.isMyLocationEnabled = true

    val points = listOf(
        LatLng(25.033611, 121.565000) to "台北101",
        LatLng(25.047924, 121.517081) to "台北車站",
    )

    points.forEach { (latLng, title) ->
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
                .draggable(true)
        )
    }

    val polylinePoints = listOf(
        LatLng(25.033611, 121.565000),
        LatLng(25.032435, 121.534905),
        LatLng(25.047924, 121.517081),
    )

    map.addPolyline(
        PolylineOptions()
            .addAll(polylinePoints)
            .color(Color.BLUE)
            .width(10f)
    )

    map.moveCamera(
        CameraUpdateFactory.newLatLngZoom(LatLng(25.035, 121.54), 13f)
    )
}
