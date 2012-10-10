package an.dpr.rubenslocatephone.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//http://www.vogella.com/articles/AndroidLocationAPI/article.html
public class LocationPruebas implements LocationListener{

	//permisos
	//GPS-->ACCESS_FINE_LOCATION permission. 
	//network & wifi -> ACCESS_COARSE_LOCATION 
		private static final String TAG = LocationPruebas.class.getName();
		private LocationManager locationManager;

		public void gpsPrueba(Context context){
			locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
			
			/*
			The Google Map Activity should automatically activate the GPS device in the emulator but if you want to use the location manager directly you need to do this yourself. Currently their seems to be an issue with this.

			Start Google Maps on the emulator and request the current geo-position, this will allow you to activate the GPS. Send new GPS coordinates to the Android emulator.
			
			ESTO ES---> IR A LOS MAPAS Y JUGUETEAR, ENTONCES SE ACTIVA EL GPS
			LUEGO ENVIAMOS COORDENADAS DESDE CONSOLA O DEVICE CONTROLLER
			*/
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				Log.i(TAG, "GPS Activado");
			} 
			if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
				Log.i(TAG, "Network Activado");
			} 
			if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
				Log.i(TAG, "Passive Activado");
			}
			
			//OBTENEMOS PROVIDER
			Criteria criteria = new Criteria();//podemos usar el obj criteria por defecto, no hace falta setearle nada si no queremos nada concreto
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			String locProvider = locationManager.getBestProvider(criteria, false);
			Location location = locationManager.getLastKnownLocation(locProvider);
			Toast.makeText(context, location!= null ? location.toString():"nol!", Toast.LENGTH_SHORT).show();
			
//			LocationListener locationListener = new LocationListener();
//			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1.0f, locationListener);
		}
		
//		public void stopGPS(){
//			locationManager.removeUpdates(locationListener);
//		}
		
		//metodos de LocationListener
		public void	 onLocationChanged(Location location){
			//Called when the location has changed.
			Log.d(TAG,"onLocationChanged");
		}
		
		public void	 onProviderDisabled(String provider){
			//Called when the provider is disabled by the user.
			Log.d(TAG,"onProviderDisabled");
		}
		
		public void	 onProviderEnabled(String provider){
			//Called when the provider is enabled by the user.
			Log.d(TAG,"onProviderEnabled");
		}
			
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			Log.d(TAG,"onStatusChanged");
			
		}
	}	

		