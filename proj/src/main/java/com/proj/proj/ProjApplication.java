package com.proj.proj;

import com.proj.proj.Service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;
@SpringBootApplication
public class ProjApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProjApplication.class, args);
	}

}
