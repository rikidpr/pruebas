package an.dpr.enbizzi.calendar.bean;

import an.dpr.enbizzi.R;

public enum CyclingType {

	ROAD(1), MTB(2), BMX(3), INDOOR(4);

	private int id;

	private CyclingType(int id) {
		this.id = id;
	}

	public static CyclingType get(int id) {
		CyclingType retValue = null;
		if (ROAD.getId() == id) {
			retValue = ROAD;
		} else if (MTB.getId() == id) {
			retValue = MTB;
		} else if (BMX.getId() == id) {
			retValue = BMX;
		} else if (INDOOR.getId() == id) {
			retValue = INDOOR;
		}
		return retValue;
	}

	public int getId() {
		return id;
	}

	public int getKeyString() {
		int ret = 0;
		switch (this) {
		case BMX:
			ret = R.string.cycling_type_BMX;
			break;
		case INDOOR:
			ret = R.string.cycling_type_INDOOR;
			break;
		case MTB:
			ret = R.string.cycling_type_MTB;
			break;
		case ROAD:
			ret = R.string.cycling_type_ROAD;
			break;
		}
		return ret;
	}
}
