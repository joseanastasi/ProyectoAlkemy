package com.alkemy.ot9.interfaceService;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "api-service", url="https://apis.datos.gob.ar/georef/api")
public interface IApiServiceProxy extends IApiService{

}
