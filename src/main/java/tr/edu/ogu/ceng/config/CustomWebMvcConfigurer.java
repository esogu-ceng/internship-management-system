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
		registry.addViewController("/facultysupervisor/company-list")
				.setViewName("forward:/facultysupervisor/company-list");
		registry.addViewController("/facultysupervisor/AllInternships")
				.setViewName("forward:/facultysupervisor/AllInternships");
		registry.addViewController("/admin").setViewName("redirect:/admin/");
		registry.addViewController("/admin/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/students").setViewName("redirect:/admin/");
		registry.addViewController("/admin/students/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/companySupervisors").setViewName("redirect:/admin/");
		registry.addViewController("/admin/companySupervisors/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/facultySupervisors").setViewName("redirect:/admin/");
		registry.addViewController("/admin/facultySupervisors/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/companies").setViewName("redirect:/admin/");
		registry.addViewController("/admin/companies/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/settings").setViewName("redirect:/admin/");
		registry.addViewController("/admin/settings/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/profile").setViewName("redirect:/admin/");
		registry.addViewController("/admin/profile/").setViewName("forward:/admin/index.html");
		registry.addViewController("/admin/companySupervisors/:id").setViewName("redirect:/admin/");
		registry.addViewController("/admin/companySupervisors/:id").setViewName("forward:/admin/index.html");
		registry.addViewController("/public").setViewName("redirect:/public/");
		registry.addViewController("/public/").setViewName("forward:/public/index.html");
		registry.addViewController("/public/reset-password").setViewName("forward:/public/index.html");
	}
}