package an.dpr.enbizzi.calendar.bean;

import an.dpr.enbizzi.R;

public enum Difficulty {
	
	EASY(1), MEDIUM(2), HARD(3), VERY_HARD(4);
	
	private int id;
	private Difficulty(int id){
		this.id = id;
	}
	
	public static Difficulty get(int id){
		Difficulty retValue = null;
		if (EASY.getId()==id){
			retValue =EASY;
		} else if (MEDIUM.getId()==id){
			retValue =MEDIUM;
		} else if (HARD.getId()==id){
			retValue =HARD;
		} else if (VERY_HARD.getId()==id){
			retValue =VERY_HARD;
		}
		return retValue;
	}
	
	public int getId(){
		return id;
	}
	
	public int getKeyString(){
		int ret = 0;
		switch(this){
		case EASY:
			ret = R.string.difficulty_EASY;
			break;
		case HARD:
			ret = R.string.difficulty_HARD;
			break;
		case MEDIUM:
			ret = R.string.difficulty_MEDIUM;
			break;
		case VERY_HARD:
			ret = R.string.difficulty_VERY_HARD;
			break;
		}
		return ret;
	}

}
