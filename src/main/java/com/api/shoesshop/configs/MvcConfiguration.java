package com.api.shoesshop.configs;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api.shoesshop.interceptors.AdminInterceptor;
import com.api.shoesshop.interceptors.LoginInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // registry.addInterceptor(new AdminInterceptor())
    // .addPathPatterns(
    // "/product/create",
    // "/product/update/*",
    // "/product/delete/*",
    // "/product-detail/create",
    // "/product-detail/update/*",
    // "/product-detail/delete/*",
    // "/product/category/create",
    // "/category/create",
    // "/category/update/*",
    // "/category/delete/*",
    // "/account/create",
    // "/account/update/*",
    // "/account/delete/*",
    // "/coupon/create",
    // "/coupon/update/*",
    // "/coupon/delete/*",
    // "/variant/create",
    // "/variant/update/*",
    // "/variant/delete/*",
    // "/variant-value/create",
    // "/variant-value/update/*",
    // "/variant-value/delete/*");
    // registry.addInterceptor(new LoginInterceptor())
    // .addPathPatterns(
    // // "/auth/my-profile",
    // // "/cart/account/read",
    // // "/auth/change-password",
    // // "/cart-item/create",
    // // "/cart-item/update/*",
    // // "/cart-item/delete/*",
    // // "/order/account/read",
    // // "/wishlist/*"
    // );
    // }

    // @Bean
    // public AdminInterceptor requestHandler1() {
    // return new AdminInterceptor();
    // }

    // @Bean
    // public LoginInterceptor requestHandler2() {
    // return new LoginInterceptor();
    // }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../"))
            dirName = dirName.replace("../", "");

        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
