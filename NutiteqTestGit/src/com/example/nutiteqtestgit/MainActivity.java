package com.example.nutiteqtestgit;

import android.app.Activity;
import android.os.Bundle;

import com.nutiteq.MapView;
import com.nutiteq.components.Components;
import com.nutiteq.datasources.raster.WMSRasterDataSource;
import com.nutiteq.projections.EPSG3857;
import com.nutiteq.projections.EPSG4326;
import com.nutiteq.rasterdatasources.HTTPRasterDataSource;
import com.nutiteq.rasterdatasources.RasterDataSource;
import com.nutiteq.rasterlayers.RasterLayer;


public class MainActivity extends Activity {

    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapView);
        
     // define new configuration holder object
        mapView.setComponents(new Components());

        // Define base layer. Here we use MapQuest open tiles which are free to use
        // Almost all online maps use EPSG3857 projection.
          // We use online data source for the tiles and the URL is given as template. 
          RasterDataSource dataSource = new HTTPRasterDataSource(new EPSG3857(), 0, 18, "http://otile1.mqcdn.com/tiles/1.0.0/osm/{zoom}/{x}/{y}.png");
          RasterLayer mapLayer = new RasterLayer(dataSource, 0);
          mapView.getLayers().setBaseLayer(mapLayer);
          
          String url = "http://kaart.maakaart.ee/geoserver/wms?transparent=true&";
          String layers = "topp:states";

          // note that data projection is different: WGS84 (EPSG:4326)
          WMSRasterDataSource wmsDataSource = new WMSRasterDataSource(
               new EPSG4326(), 0, 19, url, "", layers, "image/png");
          RasterLayer wmsLayer = new RasterLayer(wmsDataSource, 1012);
          wmsLayer.setFetchPriority(-5);
          mapView.getLayers().addLayer(wmsLayer);

          
      }

      // it is suggested to start and stop mapping in Activity lifecycle methods, as following:

      @Override
      protected void onStart() {
          super.onStart();
          //Start the map - mandatory
          mapView.startMapping();
      }

      @Override
      protected void onStop() {
          //Stop the map - mandatory to avoid problems with app restart
          mapView.stopMapping();
          super.onStop();
      }

}
