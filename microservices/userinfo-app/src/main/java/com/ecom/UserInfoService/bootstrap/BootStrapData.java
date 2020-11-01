package com.ecom.UserInfoService.bootstrap;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecom.UserInfoService.models.ShippingAddress;
import com.ecom.UserInfoService.models.User;
import com.ecom.UserInfoService.repository.ShippingRepository;
import com.ecom.UserInfoService.repository.UserRepository;

@Component
public class BootStrapData implements CommandLineRunner {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private ShippingRepository shippingRepo;

    @Override
    public void run(String... args) throws Exception {
        userRepository.saveAll(Arrays.asList(new User( "Anmoljeet", "Kaur", 746383628),
        		new User("Devesh", "Tyagi", 836483928),
        		new User("Rohit", "Malhotra", 783720920),
        		new User("Tanuj", "Gupta", 472940948),
        		new User("Aditi", "Gupta", 292197300)))
                .blockLast(Duration.ofSeconds(10));
        
        shippingRepo.saveAll(Arrays.asList(new ShippingAddress(1L,"H no 43","Sector 23","Gurguram","HR","IN",122017),
        		new ShippingAddress(1L,"H no 46","Sector 23","Gurguram","HR","IN",122017),
        		new ShippingAddress(2L,"H no 4738","Sector 21","Delhi","DL","IN",122015),
        		new ShippingAddress(3L,"H no 43","Janakpuri West","Delhi","DL","IN",122017),
        		new ShippingAddress(3L,"H no 9274","Janakpuri Eest","Delhi","DL","IN",122017)));

    }
}
