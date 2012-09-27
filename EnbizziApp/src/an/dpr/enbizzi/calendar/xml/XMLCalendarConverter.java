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
	private static final String xmlText = "<calendar><year>2012</year><club>Enbizzi</club><version>1</version>"
			+ "<event><date>03/03/2012</date><stop>Monegrillo</stop><route>Monegrillo</route>"
			+ "<returnRoute>Monegrillo</returnRoute><difficulty>EASY</difficulty><km>87</km>"
			+ "<elevationGain>269</elevationGain><type>ROAD</type></event><event><date>04/03/2012</date>"
			+ "<stop>Parada</stop><route>Vamos por aqui</route><returnRoute>Volvemos por alla</returnRoute>"
			+ "<difficulty>EASY</difficulty><km>71.3</km><elevationGain>135</elevationGain><type>MTB</type></event>"
			+ "</calendar>";

	public static final String nameSpace = null;

	private static final String TAG = XMLCalendarConverter.class.getName();

	public static List<BikeCalendar> getCalendarViaFactory()
			throws XmlPullParserException, IOException {
		List<BikeCalendar> rv = new ArrayList<BikeCalendar>();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(xmlText));
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				System.out.println("Start document");
			} else if (eventType == XmlPullParser.END_DOCUMENT) {
				System.out.println("End document");
			} else if (eventType == XmlPullParser.START_TAG) {
				System.out.println("Start tag " + xpp.getName());
			} else if (eventType == XmlPullParser.END_TAG) {
				System.out.println("End tag " + xpp.getName());
			} else if (eventType == XmlPullParser.TEXT) {
				System.out.println("Text " + xpp.getText());
			}
			eventType = xpp.next();
		}

		return rv;
	}

	public static List<BikeCalendar> getCalendarViaNewPullParser(InputStream in)
			throws XmlPullParserException, IOException {
		List<BikeCalendar> rv = null;
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xmlText));// in, null);
			parser.nextTag();
			rv = readCalendar(parser);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return rv;
	}

	private static List<BikeCalendar> readCalendar(XmlPullParser parser)
			throws XmlPullParserException, IOException {
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
				Log.d(tag, "padentro " + name);
				list.add(getCalendarItem(parser));
			} else {
				Log.d(tag, "skip " + name);
				skip(parser);
			}
		}
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
			Log.d(TAG + ".getCalendarItem",
					parser.getName() != null ? parser.getName() : "null");
			if (next == XmlPullParser.END_TAG
					&& BikeCalendarXMLTags.event.equals(bcx)) {
				continuar = false;
			} else if (next != XmlPullParser.START_TAG) {
				continue;
			} else {
				switch (bcx) {
				case elevationGain:
					bCal.setElevationGain(Integer.valueOf(readTag(parser, bcx)));
					break;
				case date:
					bCal.setDate(readTag(parser, bcx));
					break;
				case difficulty:
					bCal.setDifficulty(Difficulty.valueOf(readTag(parser, bcx)));
					break;
				case km:
					bCal.setKm(Float.valueOf(readTag(parser, bcx)));
					break;
				case route:
					bCal.setRoute(readTag(parser, bcx));
					break;
				case returnRoute:
					bCal.setReturnRoute(readTag(parser, bcx));
					break;
				case stop:
					bCal.setStop(readTag(parser, bcx));
					break;
				case type:
					bCal.setType(CyclingType.valueOf(readTag(parser, bcx)));
					break;
				default:
					break;
				}
			}
		} while (continuar);

		return bCal;
	}

	private static String readTag(XmlPullParser parser, BikeCalendarXMLTags tag)
			throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, nameSpace, tag.name());
		String valor = null;
		Log.d(TAG + ".readTag", tag.name());
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
