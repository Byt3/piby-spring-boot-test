package com.piby.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.piby.ecommerce.entities.Image;
import com.piby.ecommerce.entities.User;
import com.piby.ecommerce.repository.ImageRepository;
import com.piby.ecommerce.repository.UserRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {

    private static String UPLOAD_ROOT = "upload-dir";

    private final ImageRepository imageRepository;
    private final ResourceLoader resourceLoader;

    private final SimpMessagingTemplate messagingTemplate;
    private UserRepository userRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, ResourceLoader resourceLoader,
                        SimpMessagingTemplate messagingTemplate, UserRepository userRepository) {

        this.imageRepository = imageRepository;
        this.resourceLoader = resourceLoader;
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;

    }


    public Page<Image> findPage(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    public Resource findOneImage(String filename) {
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
    }

    public void createImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
            imageRepository.save(
                    new Image(
                            file.getOriginalFilename(),
                            userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())));
            messagingTemplate.convertAndSend("/topic/newImage", file.getOriginalFilename());
        }
    }


    @PreAuthorize("@imageRepository.findByName(#filename)?.owner?.username == authentication?.name or hasRole('ADMIN')")
    public void deleteImage(@Param("filename") String filename) throws IOException {

        final Image byName = imageRepository.findByName(filename);
        imageRepository.delete(byName);
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
        messagingTemplate.convertAndSend("/topic/deleteImage", filename);
    }

    @Bean
//    @Profile("dev")
    CommandLineRunner setUp(ImageRepository imageRepository, UserRepository userRepository) throws IOException {

        return (args) -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectory(Paths.get(UPLOAD_ROOT));

            User byt3 = userRepository.save(new User("byte", "byllopowha", "ROLE_ADMIN", "ROLE_USER"));
            User lucifer = userRepository.save(new User("lucifer", "rulesinhell", "ROLE_USER"));

            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/test"));
            imageRepository.save(new Image("test", byt3));

            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/test2"));
            imageRepository.save(new Image("test2", byt3));

            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/test3"));
            imageRepository.save(new Image("test3", lucifer));


        };
    }

}
