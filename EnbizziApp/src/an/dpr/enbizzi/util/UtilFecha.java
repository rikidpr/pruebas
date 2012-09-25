package an.dpr.enbizzi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Clase con utiles de manejo de fechas
 * 
 * @author rsaez
 * 
 */
public class UtilFecha {
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat SDF_FH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static final SimpleDateFormat SDF_H = new SimpleDateFormat("HH:mm");
	/**
	 * formato dd/MM/yyyy
	 * 
	 * @param fecha
	 * @return
	 */
	public static String formatFecha(Date fecha) {
		return formatFecha(SDF, fecha);
	}

	/**
	 * formato dd/MM/yyyy HH:mm
	 * 
	 * @param fecha
	 * @return
	 */
	public static String verFechaHora(Date fecha) {
		return formatFecha(SDF_FH, fecha);
	}

	/**
	 * formato HH:mm
	 * 
	 * @param fecha
	 * @return
	 */
	public static String verHora(Date fecha) {
		return formatFecha(SDF_H, fecha);
	}

	private static String formatFecha(SimpleDateFormat sdf, Date fecha) {
		return fecha != null ? sdf.format(fecha) : "-";
	}

	/**
	 * Devuelve la fecha con hora 00:00:00.000
	 * 
	 * @param fecha
	 * @return
	 */
	public static Date quitarHora(Date fecha) {
		if (fecha != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		} else {
			return null;
		}
	}

	/**
	 * Suma o resta dias a una fecha
	 * 
	 * @param fecha
	 *            fecha sobre la que sumar dias
	 * @param dias
	 *            si es positivo, suma los dias, si es negativo los resta
	 * @return
	 */
	public static Date sumaDias(Date fecha, Integer dias) {
		Date retValue = null;
		if (fecha != null && dias != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.add(Calendar.DAY_OF_MONTH, dias.intValue());
			retValue = cal.getTime();
		}
		return retValue;
	}
}
