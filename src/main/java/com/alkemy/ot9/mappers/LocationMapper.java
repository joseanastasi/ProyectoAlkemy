package com.alkemy.ot9.mappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.models.Location;
import com.google.gson.Gson;

@Service
public class LocationMapper  {	
	
	public String map(String locationResponse) {
		JSONArray locationsJSON = new JSONObject(locationResponse).getJSONArray("localidades");
		ArrayList<Location> locations = new ArrayList<>();
		for (int i = 0; i < locationsJSON.length(); i++) {			
			locations.add(new Location(locationsJSON.getJSONObject(i).getString("id"), locationsJSON.getJSONObject(i).getString("nombre")));			
		}
		return new Gson().toJson(locations);		
	}
}