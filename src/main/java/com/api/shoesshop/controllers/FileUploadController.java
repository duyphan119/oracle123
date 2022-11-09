package com.api.shoesshop.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.utils.Helper;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class FileUploadController {

    public class UploadResponse {
        public String secure_url;
    }

    @PostMapping("/api/upload/image/single")
    public ResponseEntity<String> uploadImage(HttpServletRequest req, @RequestParam("image") MultipartFile file) {
        try {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dwhjftwvw",
                    "api_key", "335652142568654",
                    "api_secret", "rVXHGRE29TukCR3eUxZEyJlv3ME"));
            Gson g = new Gson();
            String str = Helper.toJson(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "public_id",
                    "xshop/" + RandomStringUtils.randomAlphanumeric(10) + new Date().getTime())));
            UploadResponse uploaded = g.fromJson(str, UploadResponse.class);
            return Helper.responseSuccess(uploaded.secure_url);

        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
    }
}
