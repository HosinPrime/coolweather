package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoolWeatherDB {

	//数据库名
	public static final String DB_NAME="cool_weather.db";
	
	//数据库版本
	public static final int VERSION=1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context)
	{
		CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context)
	{
		if(coolWeatherDB==null)
		{
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
		
	}
	//将province存入数据库
	public void saveProvince(Province province)
	{
		if(province!=null)
		{
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code",province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	//将province信息从数据库读出
	public List<Province> loadProvinces()
	{
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst())
		{
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
				
				
			}while(cursor.moveToNext());
		}
		
		if(cursor!=null)
		{
			cursor.close();
		}
		
		return list;
		
	}
	
	//将city信息存储到数据库
	public void saveCity(City city)
	{
		if(city!=null)
		{
  			ContentValues values=new ContentValues();
  			//Log.d("city",city.getCityName());
	        values.put("city_name",city.getCityName());
	        values.put("city_code", city.getCityCode());
	        values.put("province_id", city.getProvinceId());
	        db.insert("City", null, values);
			
		}
		
	}
	
	//将city信息从数据库读出
	public List<City> loadCities(int provinceId)
	{
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)},null, null, null);
		if(cursor.moveToFirst())
		{
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
			
		}
         
		if(cursor!=null)
		{
			cursor.close();
		}
		return list;			
			
	}
	//将county的信息存入数据库
	public void saveCounty(County county)
	{
		if(county!=null)
		{
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id",county.getCityId());
			db.insert("County", null, values);
			
		}
	}
	
	//将county的信息从数据库读出
	public List<County> loadCounties(int cityId)
	{
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst())
		{
			do{
				County county=new County();
				county.setCityId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);		
				list.add(county);
			}while(cursor.moveToNext());
			
		}
		
		if(cursor!=null)
		{
			cursor.close();
		}
		
		return list;
		
	}
}
