package an.dpr.enbizzi.calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import an.dpr.enbizzi.calendar.bean.AemetEstadoCielo;
import an.dpr.enbizzi.calendar.bean.AemetHora;
import an.dpr.enbizzi.calendar.bean.AemetLocalidadTags;
import an.dpr.enbizzi.calendar.bean.AemetPeriodo;
import an.dpr.enbizzi.calendar.bean.AemetViento;
import an.dpr.enbizzi.calendar.bean.PrediccionAemet;
import android.util.Log;


public class DetalleSalidaUtil{

	private static final String TAG = DetalleSalidaUtil.class.getName();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final String AEMET_URL_BASE_1 = "http://www.aemet.es/xml/municipios/localidad_";
	private static final String AEMET_URL_BASE_2 = ".xml";
//	private static final String RUTA_FILE_XML = "/programas/apache-tomcat-6.0.18/webapps/dpr-restfullprueba/localidad_50003.xml";
	
	public static PrediccionAemet getPrediccion(Integer aemetCode, Date fecha)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "-init-");
		PrediccionAemet ret = null;
		String xml = leerXML(aemetCode);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(xml));
			int eventType = xpp.getEventType();
			do {
				if (eventType == XmlPullParser.START_TAG) {
					if (isDia(xpp, fecha)) {
						ret = leerPrediccion(xpp);
					}
				}
				eventType = xpp.next();
			} while (eventType != XmlPullParser.END_DOCUMENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "", e);
		}
		return ret;
	}
	
	private static String leerXML(Integer aemetCode) throws IOException {
		StringBuilder ret = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(getAemetUrl(aemetCode));
			HttpResponse response = client.execute(post);
			InputStreamReader isr = new InputStreamReader(
					response.getEntity().getContent(), "cp1252");
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line=br.readLine())!= null){
				ret.append(line);
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG, "error leyendo info meteo", e);
		} catch (IOException e) {
			Log.e(TAG, "error leyendo info meteo", e);
		}
		return ret.toString();
	}
	
	private static String getAemetUrl(Integer aemetCode){
		StringBuilder sb = new StringBuilder();
		sb.append(AEMET_URL_BASE_1);
		sb.append(aemetCode);
		sb.append(AEMET_URL_BASE_2);
		return sb.toString();
	}


	private static boolean isDia(XmlPullParser xpp, Date fecha) {
		boolean ret = false;
		if (xpp != null && fecha != null) {
			AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
			if (AemetLocalidadTags.tag_dia.equals(tag)) {
				Log.d(TAG, "Estamos en tag DIA");
				String pFecha = getAttributeValue(xpp, AemetLocalidadTags.param_fecha.getValue());
				Log.d(TAG, "FECHA:" + pFecha);
				if (sdf.format(fecha).equals(pFecha)) {
					ret = true;
				}
			}
		}
		return ret;
	}

	private static String getAttributeValue(XmlPullParser parser, String clave) {
		String retValue = null;
		if (parser != null && clave != null) {
			for (int i = 0; i < parser.getAttributeCount(); i++) {
				if (parser.getAttributeName(i).equals(clave)) {
					retValue = parser.getAttributeValue(i);
				}
			}
		}
		return retValue;
	}

	private static PrediccionAemet leerPrediccion(XmlPullParser xpp)
			throws XmlPullParserException, IOException, ParseException {
		Log.d(TAG, "-init-");
		int eventType = xpp.getEventType();
		PrediccionAemet prediccion = new PrediccionAemet();
		prediccion = getDia(xpp, prediccion);
		do {
			AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
			if (tag != null && eventType == XmlPullParser.START_TAG) {
				switch (tag) {
				case tag_estado_cielo:
					prediccion = addEstadoCielo(prediccion, xpp);
					break;
				case tag_temperatura:
					prediccion = addTemperatura(prediccion, xpp);
					break;
				case tag_viento:
					prediccion = addViento(prediccion, xpp);
					break;
				case tag_prob_precipitacion:
					prediccion = addProbPrecipitacion(prediccion, xpp);
					break;
				case tag_racha_max:
					prediccion = addRachaMax(prediccion, xpp);
					break;
				default:
					break;
				}
			} else if (eventType == XmlPullParser.END_TAG
					&& AemetLocalidadTags.tag_dia.equals(tag)) {
				Log.d(TAG, "fin prediccion, salimos");
				break;
			}
			eventType = xpp.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
		Log.d(TAG, prediccion.toString());
		return prediccion;
	}
	
	//<racha_max periodo="00-12"></racha_max>
	private static PrediccionAemet addRachaMax(PrediccionAemet prediccion,
			XmlPullParser xpp) throws XmlPullParserException, IOException {
		AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
		int eventType = xpp.getEventType();
		if (AemetLocalidadTags.tag_racha_max.equals(tag)
				&& eventType == XmlPullParser.START_TAG){
			String sPer = getAttributeValue(xpp, 
					AemetLocalidadTags.param_periodo.getValue());
			AemetPeriodo periodo = AemetPeriodo.getPeriodo(sPer);
			xpp.next();
			//comprobamos si esta en tag cierre o contenido
			tag = AemetLocalidadTags.get(xpp.getName());
			if (tag == null){
				Integer racha = Integer.valueOf(xpp.getText());
				prediccion.getRachaMax().put(periodo, racha);
				xpp.next();//puntero a tag cierre
			} else {
				//no hay valor de racha, asignamos 0
				prediccion.getRachaMax().put(periodo, 0);
			}
		}
		return prediccion;
	}

	//<prob_precipitacion periodo="00-12">5</prob_precipitacion>
	private static PrediccionAemet addProbPrecipitacion(
			PrediccionAemet prediccion, XmlPullParser xpp) 
			throws XmlPullParserException, IOException {
		AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
		int eventType = xpp.getEventType();
		if (AemetLocalidadTags.tag_prob_precipitacion.equals(tag)
				&& eventType == XmlPullParser.START_TAG){
			AemetPeriodo periodo = AemetPeriodo.getPeriodo(getAttributeValue(
					xpp,AemetLocalidadTags.param_periodo.getValue()));
			xpp.next();
			Integer prob = Integer.valueOf(xpp.getText());
			prediccion.getProbPrecipitacion().put(periodo, prob);
			xpp.next();//colocamos en tag de cierre
		}
		return prediccion;
	}

	private static PrediccionAemet getDia(XmlPullParser xpp,
			PrediccionAemet prediccion) throws ParseException,
			XmlPullParserException {
		AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
		int eventType = xpp.getEventType();
		if (eventType == XmlPullParser.START_TAG
				&& AemetLocalidadTags.tag_dia.equals(tag)) {
			String pFecha = getAttributeValue(xpp,
					AemetLocalidadTags.param_fecha.getValue());
			prediccion.setDia(sdf.parse(pFecha));
		}
		return prediccion;
	}

//		<estado_cielo periodo="00-12" descripcion="Nuboso">14</estado_cielo>
	public static PrediccionAemet addEstadoCielo(PrediccionAemet prediccion, XmlPullParser xpp) throws XmlPullParserException, IOException{
		Log.d(TAG, "-init-");
		int eventType = xpp.getEventType();
		AemetLocalidadTags tag = AemetLocalidadTags.get(xpp.getName());
		if (AemetLocalidadTags.tag_estado_cielo.equals(tag)
				&& eventType == XmlPullParser.START_TAG){
			String periodo = getAttributeValue(xpp, AemetLocalidadTags.param_periodo.getValue());
			String descripcion = getAttributeValue(xpp, AemetLocalidadTags.param_descripcion.getValue());
			//pasamos al contenido
			xpp.next();
			String estado = xpp.getText();
			AemetEstadoCielo aec = new AemetEstadoCielo();
			aec.setPeriodo(AemetPeriodo.getPeriodo(periodo));
			aec.setCode(estado);
			aec.setDescripcion(descripcion);
			prediccion.getEstadoCielo().add(aec);
			//dejamos el puntero en el tag de cierre
			xpp.next();
		}
		return prediccion;
	}

	public static PrediccionAemet addTemperatura(PrediccionAemet prediccion,
			XmlPullParser xpp) throws XmlPullParserException, IOException {
		Log.d(TAG, "-init-");
		boolean continuar = true;
		int eventType = 0;
		AemetLocalidadTags tag;
		do{
			eventType = xpp.getEventType();
			tag = AemetLocalidadTags.get(xpp.getName());
			if (tag!=null){
				switch(tag){
				case tag_maxima:
					prediccion = addMaxTemperatura(prediccion, xpp);
					break;
				case tag_minima:
					prediccion = addMinTemperatura(prediccion, xpp);
					break;
				case tag_dato:
					prediccion = addDatoTemperatura(prediccion, xpp);
					break;
				case tag_temperatura:
					if (eventType == XmlPullParser.END_TAG){
						continuar = false;
					}
					break;
				}
			}
			if (continuar){
				xpp.next();
			}
		} while(continuar);
		return prediccion;
	}

	private static PrediccionAemet addMaxTemperatura(PrediccionAemet prediccion,
		XmlPullParser xpp) throws XmlPullParserException, IOException {
		Log.d(TAG, "-init-");
		if (xpp!=null && xpp.getEventType() == XmlPullParser.START_TAG){
			xpp.next();
			String maxima = xpp.getText();
			prediccion.setMaxTemperatura(Integer.valueOf(maxima));
		}
		return prediccion;
	}

	private static PrediccionAemet addMinTemperatura(PrediccionAemet prediccion,
			XmlPullParser xpp) throws XmlPullParserException, IOException {
		Log.d(TAG, "-init-");
		if (xpp!=null && xpp.getEventType() == XmlPullParser.START_TAG){
			xpp.next();
			String minima = xpp.getText();
			prediccion.setMinTemperatura(Integer.valueOf(minima));
		}
		return prediccion;
	}
	
	private static PrediccionAemet addDatoTemperatura(
			PrediccionAemet prediccion, XmlPullParser xpp)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "-init-");
		if (xpp != null && xpp.getEventType() == XmlPullParser.START_TAG) {
			String hora = getAttributeValue(xpp,
					AemetLocalidadTags.param_hora.getValue());
			xpp.next();
			String temp = xpp.getText();
			prediccion.getHoraTemperatura().put(AemetHora.getPeriodo(hora),
					Integer.valueOf(temp));
		}
		return prediccion;
	}
	
	private static PrediccionAemet addViento(
			PrediccionAemet prediccion, XmlPullParser xpp)
			throws XmlPullParserException, IOException {
		int eventType = 0;
		AemetLocalidadTags tag;
		boolean continuar = true;
		AemetViento av = new AemetViento();
		do{
			tag = AemetLocalidadTags.get(xpp.getName());
			eventType = xpp.getEventType();
			if (tag != null){
				switch(tag){
				case tag_viento:
					if (eventType == XmlPullParser.START_TAG){
						String periodo = getAttributeValue(xpp, AemetLocalidadTags.param_periodo.getValue());
						av.setPeriodo(AemetPeriodo.getPeriodo(periodo));
					} else {
						continuar = false;
					}
					break;
				case tag_direccion:
					if (eventType == XmlPullParser.START_TAG){
						xpp.next();
						av.setDireccion(xpp.getText());
					}
					break;
				case tag_velocidad:
					if (eventType == XmlPullParser.START_TAG){
						xpp.next();
						av.setVelocidad(Integer.valueOf(xpp.getText()));
					}
					break;
				}
			}
			if (continuar){
				xpp.next();
			}
		}while(continuar);
		prediccion.getViento().add(av);
		return prediccion;
	}

}