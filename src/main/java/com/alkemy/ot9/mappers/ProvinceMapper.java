package com.alkemy.ot9.mappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.models.Provincia;

@Service
public class ProvinceMapper {
	
	public ArrayList<Provincia> map(String provinceResponse) {
		JSONArray provinciasJSON = new JSONObject(provinceResponse).getJSONArray("provincias");		
		ArrayList<Provincia> provincias = new ArrayList<>();		
		for (int i = 0; i < provinciasJSON.length(); i++) {			
			provincias.add(new Provincia(provinciasJSON.getJSONObject(i).getString("id"), provinciasJSON.getJSONObject(i).getString("nombre")));			
		}
		return provincias;		
	}
}