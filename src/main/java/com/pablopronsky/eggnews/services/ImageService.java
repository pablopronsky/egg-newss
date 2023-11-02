package com.pablopronsky.eggnews.services;

import com.pablopronsky.eggnews.entities.Image;
import com.pablopronsky.eggnews.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public Image saveImage(MultipartFile file) throws Exception{
        if(file != null){
            try{
                Image image = new Image();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Image updateImage(MultipartFile file, Long idImage) throws Exception{
        if(file != null){
            try{
                Image image = new Image();
                if(idImage != null){
                    Optional<Image> imageOptional = imageRepository.findById(idImage);
                    if (imageOptional.isPresent()){
                        image = imageOptional.get();
                    }
                }
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());
                return imageRepository.save(image);
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
