package com.data.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return result.get("secure_url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}