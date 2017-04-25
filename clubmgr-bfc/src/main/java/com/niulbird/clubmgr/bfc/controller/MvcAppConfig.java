package com.niulbird.clubmgr.bfc.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class MvcAppConfig extends WebMvcConfigurationSupport  {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
				.addContentVersionStrategy("*.js", "*.css", "*.png");
				//.addVersionStrategy(new ContentVersionStrategy(), "/**", "*.css", "*.png");
		registry.addResourceHandler("/robots.txt").addResourceLocations("/robots.txt");
		registry.addResourceHandler("/sitemap.xml").addResourceLocations("/sitemap.xml");
		registry.addResourceHandler("/static/css/main*css").addResourceLocations("/static/css/")
				.setCachePeriod(60 * 60 * 24 * 1) /* one day */
				.resourceChain(true).addResolver(versionResourceResolver);
		registry.addResourceHandler("/static/**").addResourceLocations("/static/")
				.setCachePeriod(60 * 60 * 24 * 365) /* one year */
				.resourceChain(true).addResolver(versionResourceResolver);
	}
}