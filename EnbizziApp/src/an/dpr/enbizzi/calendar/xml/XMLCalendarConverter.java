package an.dpr.enbizzi.calendar.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import an.dpr.enbizzi.calendar.bean.BikeCalendar;
import an.dpr.enbizzi.calendar.bean.CyclingType;
import an.dpr.enbizzi.calendar.bean.Difficulty;
import android.util.Log;
import android.util.Xml;

public class XMLCalendarConverter {
	// FIXME esto es solo para probar, hay que leerlo de internet
	public static final String xmlText = "<calendar><year>2012</year><club>Enbizzi</club><version>1</version>"
			+ "<event><date>03/03/2012</date><stop>Monegrillo</stop><route>Monegrillo</route>"
			+ "<returnRoute>Monegrillo</returnRoute><difficulty>EASY</difficulty><km>87</km>"
			+ "<elevationGain>269</elevationGain><type>ROAD</type></event><event><date>04/03/2012</date>"
			+ "<stop>Parada</stop><route>Vamos por aqui</route><returnRoute>Volvemos por alla</returnRoute>"
			+ "<difficulty>EASY</difficulty><km>71.3</km><elevationGain>135</elevationGain><type>MTB</type></event>"
			+ "</calendar>";

	public static final String nameSpace = null;

	private static final String TAG = XMLCalendarConverter.class.getName();

	public static List<BikeCalendar> getCalendarViaNewPullParser(String xml)
			throws XmlPullParserException, IOException {
		List<BikeCalendar> rv = null;
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xml));// in, null);
			parser.nextTag();
			rv = readCalendar(parser);
		} catch (IOException e) {
			Log.e(TAG, "Error leyendo xml calendar", e);
		}
		return rv;
	}

	private static List<BikeCalendar> readCalendar(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		Log.d(TAG, "readCalendar");
		List<BikeCalendar> list = new ArrayList<BikeCalendar>();
		String tag = TAG + ".readCalendar";

		parser.require(XmlPullParser.START_TAG, nameSpace,
				BikeCalendarXMLTags.calendar.name());
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(BikeCalendarXMLTags.event.name())) {
				list.add(getCalendarItem(parser));
			} else if (!name.equals(BikeCalendarXMLTags.events.name())) {
				skip(parser);
			}
		}
		Log.d(TAG, list.toString());
		return list;
	}

	private static BikeCalendar getCalendarItem(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		BikeCalendar bCal = new BikeCalendar();
		parser.require(XmlPullParser.START_TAG, nameSpace,
				BikeCalendarXMLTags.event.name());
		boolean continuar = true;
		do {
			int next = parser.next();
			BikeCalendarXMLTags bcx = BikeCalendarXMLTags.valueOf(parser
					.getName());
			if (next == XmlPullParser.END_TAG
					&& BikeCalendarXMLTags.event.equals(bcx)) {
				continuar = false;
			} else if (next != XmlPullParser.START_TAG) {
				continue;
			} else {
				String valor = readTag(parser, bcx);
				if (valor != null){
					switch (bcx) {
					case elevationGain:
						bCal.setElevationGain(Integer.valueOf(valor));
						break;
					case date:
						bCal.setDate(valor);
						break;
					case difficulty:
						bCal.setDifficulty(Difficulty.valueOf(valor));
						break;
					case km:
						bCal.setKm(Float.valueOf(valor));
						break;
					case route:
						bCal.setRoute(valor);
						break;
					case returnRoute:
						bCal.setReturnRoute(valor);
						break;
					case stop:
						bCal.setStop(valor);
						break;
					case type:
						bCal.setType(CyclingType.valueOf(valor));
						break;
					case aemetCodeStart:
						bCal.setAemetStart(Integer.valueOf(valor));
						break;
					case aemetCodeStop:
						bCal.setAemetStop(Integer.valueOf(valor));
						break;
					default:
						break;
					}
				}
			}
		} while (continuar);

		return bCal;
	}

	private static String readTag(XmlPullParser parser, BikeCalendarXMLTags tag)
			throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, nameSpace, tag.name());
		String valor = null;
//		Log.d(TAG + ".readTag", tag.name());
		if (parser.next() == XmlPullParser.TEXT) {
			valor = parser.getText();
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, nameSpace, tag.name());
		return valor;
	}

	/** va saltando tags */
	private static void skip(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
