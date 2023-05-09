package tr.edu.ogu.ceng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/student").setViewName("redirect:/student/");
		registry.addViewController("/student/").setViewName("forward:/student/index.html");
		registry.addViewController("/companysupervisor").setViewName("redirect:/companysupervisor/");
		registry.addViewController("/companysupervisor/").setViewName("forward:/companysupervisor/index.html");
		registry.addViewController("/facultysupervisor").setViewName("redirect:/facultysupervisor/");
		registry.addViewController("/facultysupervisor/").setViewName("forward:/facultysupervisor/index.html");
		registry.addViewController("/admin").setViewName("redirect:/admin/");
		registry.addViewController("/admin/").setViewName("forward:/admin/index.html");
		registry.addViewController("/public").setViewName("redirect:/public/");
		registry.addViewController("/public/").setViewName("forward:/public/index.html");
	}
}