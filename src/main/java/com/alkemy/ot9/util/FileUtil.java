package com.alkemy.ot9.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

	public boolean isEmpty(MultipartFile multipartFile) {

		return multipartFile.isEmpty();

	}

}
