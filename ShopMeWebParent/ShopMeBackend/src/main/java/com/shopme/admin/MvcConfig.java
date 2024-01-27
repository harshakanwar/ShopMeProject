package com.shopme.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "/Udemy Learnings/ShopMeProject/ShopMeWebParent/ShopMeBackend/user-photos";
        Path userPhotosDir = Paths.get(dirName);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
        System.out.println("Absolute Path " + userPhotosPath);
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/" + userPhotosPath + "/");


        String categoryImagesDirName = "/Udemy Learnings/ShopMeProject/ShopMeWebParent/ShopMeBackend/category-images";
        Path categoryImagesDir = Paths.get(categoryImagesDirName);

        String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/category-images/**" + categoryImagesDirName + "/**")
                .addResourceLocations("file:/" + categoryImagesPath + "/");
    }
}
