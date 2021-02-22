package com.alkemy.ot9.interfaceService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IApiService {
	
	@GetMapping("/provincias?campos=basico")
	String getProvincia();
	
	@GetMapping("/localidades?provincia={id}&campos=basico&max=5000")
	String getLocation(@PathVariable("id") String id);
}