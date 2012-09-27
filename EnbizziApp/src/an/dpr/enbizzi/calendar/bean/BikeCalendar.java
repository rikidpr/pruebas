package an.dpr.enbizzi.calendar.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import an.dpr.enbizzi.util.UtilFecha;
import android.util.Log;

public class BikeCalendar implements Serializable {

	/**
	 * FOR SERLIALIZABLE
	 */
	private static final long serialVersionUID = 1L;
	private static final String TAG = BikeCalendar.class.getName();

	private int id;
	private Date date; // fecha/hora
	private String route;
	private String returnRoute;
	private String stop;
	private Float km;
	private Integer elevationGain;
	private Difficulty difficulty;
	private CyclingType type;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return the returnRoute
	 */
	public String getReturnRoute() {
		return returnRoute;
	}

	/**
	 * @param returnRoute
	 *            the returnRoute to set
	 */
	public void setReturnRoute(String returnRoute) {
		this.returnRoute = returnRoute;
	}

	/**
	 * @return the stop
	 */
	public String getStop() {
		return stop;
	}

	/**
	 * @param stop
	 *            the stop to set
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}

	/**
	 * @return the km
	 */
	public Float getKm() {
		return km;
	}

	/**
	 * @param km
	 *            the km to set
	 */
	public void setKm(Float km) {
		this.km = km;
	}

	/**
	 * @return the dificulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @param dificulty
	 *            the dificulty to set
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the type
	 */
	public CyclingType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(CyclingType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BikeCalendar [id=" + id + ", date=" + date + ", route=" + route
				+ ", returnRoute=" + returnRoute + ", stop=" + stop + ", km="
				+ km + ", elevationGain=" + elevationGain + ", dificulty="
				+ difficulty + ", type=" + type + "]";
	}

	public Integer getElevationGain() {
		return elevationGain;
	}

	public void setElevationGain(Integer elevationGain) {
		this.elevationGain = elevationGain;
	}

	public void setDate(String fecha) {
		try {
			this.date = UtilFecha.SDF.parse(fecha);
		} catch (ParseException e) {
			Log.e(TAG, "Fecha en formato incorrecto:"+fecha);
		}
	}

}
