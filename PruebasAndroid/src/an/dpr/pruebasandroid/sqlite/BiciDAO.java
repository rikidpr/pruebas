package an.dpr.pruebasandroid.sqlite;

import java.util.ArrayList;

import an.dpr.pruebasandroid.content.BiciContract;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * DAO para Bici
 * 
 * @author rsaez
 * 
 */
public class BiciDAO {

	private static final String TAG = BiciDAO.class.getName();
	private BiciDBHelper dbHelper;

	public BiciDAO(Context c) {
		dbHelper = new BiciDBHelper(c);
	}
	
	public long persist(Bici bici) {
		ContentValues values = new ContentValues();
		values.put(BiciContract.COLUMN_ID, bici.getBiciId());
		values.put(BiciContract.COLUMN_MARCA, bici.getMarca());
		values.put(BiciContract.COLUMN_MODELO, bici.getModelo());
		values.put(BiciContract.COLUMN_GRUPO, bici.getGrupo());
		return persist(values);
	}
	
	
	public long persist(ContentValues values) {
		Log.d(TAG, "persistBike()");
		SQLiteDatabase db = null;
		long retValue = 0;
		try {
			Bici rb = getById(values.get(BiciContract.COLUMN_ID));
			// abrimos el db despues, porque en getBike se abre un
			// readableDatabase y daria conflicto
			db = dbHelper.getWritableDatabase();
			if (rb != null) {
				String where = BiciContract.COLUMN_ID+"=?";
				String[] valueWhere = new String[] { 
						Integer.toString(bici.getBiciId()) };
				retValue = db.update(BiciContract.TABLE_NAME, values, where, valueWhere);
			} else {
				retValue = db.insert(BiciContract.TABLE_NAME, "", values);
			}

		} catch (Exception e) {
			Log.e(TAG, "error insertando datos", e);
		} finally {
			if (db != null)
				db.close();
		}
		return retValue;
	}

	public boolean delete(Integer biciId) {
		Log.d(TAG, "addBike()");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		boolean delete = false;
		try {
			String whereClause = BiciContract.COLUMN_ID+"=?";
			String[] whereArgs = new String[] { Integer.toString(biciId) };
			int afected = db.delete(BiciContract.TABLE_NAME, whereClause, whereArgs);
			if (afected > 0) {
				delete = true;
			}

		} catch (Exception e) {
			Log.e(TAG, "error insertando datos", e);
		} finally {
			if (db != null)
				db.close();
		}
		return delete;

	}

	public ArrayList<Bici> getList() {
		Log.d(TAG, "getBikes()");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ")
		.append(BiciContract.COLUMN_ID).append(", ")
		.append(BiciContract.COLUMN_MARCA).append(", ")
		.append(BiciContract.COLUMN_MODELO).append(", ")
		.append(BiciContract.COLUMN_GRUPO)
		.append(" from ").append(BiciContract.TABLE_NAME);
		
		ArrayList<Bici> retValue = new ArrayList<Bici>();

		try {
			Cursor c = db.rawQuery(sql.toString(), null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				do {
					Bici bici = getBici(c);
					retValue.add(bici);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			Log.e(TAG, "error insertando datos", e);
		} finally {
			if (db != null)
				db.close();
		}

		return retValue;

	}

	/**
	 * 
	 * @param biciId
	 * @return null si no existe, objeto Bici si existe
	 */
	public Bici getById(int biciId) {
		Log.d(TAG, "getBike(biciId)");
		Bici bici = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder();
		sql.append("select ")
		  .append(BiciContract.COLUMN_ID).append(", ")
		  .append(BiciContract.COLUMN_MARCA).append(", ")
		  .append(BiciContract.COLUMN_MODELO).append(", ")
		  .append(BiciContract.COLUMN_GRUPO)
		  .append(" from ").append(BiciContract.TABLE_NAME)
		  .append(" where ").append(BiciContract.COLUMN_ID)
		  .append("=").append(biciId);

		try {
			Cursor c = db.rawQuery(sql.toString(), null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				bici = getBici(c);
			}
		} catch (Exception e) {
			Log.e(TAG, "error insertando datos", e);
		} finally {
			if (db != null)
				db.close();
		}

		return bici;

	}

	private Bici getBici(Cursor c) {
		Bici bici = new Bici();
		new Bici();
		bici.setBiciId(c.getInt(c.getColumnIndex(BiciContract.COLUMN_ID)));
		bici.setMarca(c.getString(c.getColumnIndex(BiciContract.COLUMN_MARCA)));
		bici.setModelo(c.getString(c.getColumnIndex(BiciContract.COLUMN_MODELO)));
		bici.setGrupo(c.getString(c.getColumnIndex(BiciContract.COLUMN_GRUPO)));
		return bici;
	}
}
