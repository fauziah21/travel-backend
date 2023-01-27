package com.fauzia.project.backend.service;

import com.fauzia.project.backend.converter.TravelPackageGalleryConverter;
import com.fauzia.project.backend.dto.TravelPackageGalleryDTO;
import com.fauzia.project.backend.model.TravelPackageGalleryModel;
import com.fauzia.project.backend.model.TravelPackageModel;
import com.fauzia.project.backend.repository.TravelPackageGalleryRepository;
import com.fauzia.project.backend.repository.TravelPackageRepository;
import com.fauzia.project.backend.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelPackageGalleryService {
    private final Path root = Paths.get("D:\\images\\");
    @Autowired
    private TravelPackageGalleryRepository travelPackageGalleryRepository;
    @Autowired
    private TravelPackageRepository travelPackageRepository;
    @Autowired
    private TravelPackageGalleryConverter travelPackageGalleryConverter;

    //create
    public boolean create(int travelId, MultipartFile image, int userId){
        Optional<TravelPackageModel> travelOpt = travelPackageRepository.findById(travelId);
        String imageContentType = image.getContentType();
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDateTime now = LocalDateTime.now();

        if (travelOpt.isEmpty()){
            return false;
        }

        String name = travelOpt.get().getTravelTitle().replaceAll(" ", "");

        if (imageContentType.contains(imageContentType)){
            try{
                //jika belum ada directory maka buat dulu
                if (!Files.exists(root)){
                    Files.createDirectory(root);
                }

                //get content file type
                String contentType = image.getContentType();
                contentType = contentType.toLowerCase().replaceAll(imageContentType, ".png");

                //generate random string
                String randomString = randomStringGenerator.getRandom();

                //copy file into directory with random string
                Files.copy(image.getInputStream(), this.root.resolve(name + "-" + formatter.format(now) + "-" + randomString + contentType));
                //insert into db
                TravelPackageGalleryModel galleryModel = new TravelPackageGalleryModel();
                galleryModel.setTravelPackageId(travelId);
                galleryModel.setGalleryImage(name + "-" + formatter.format(now) + "-" + randomString + contentType);
                galleryModel.setCreationDate(LocalDateTime.now());
                galleryModel.setCreateBy(userId);

                travelPackageGalleryRepository.save(galleryModel);
                return true;
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }

    }

    //get all
    public List<TravelPackageGalleryDTO> getAll(int travelPackageId){
        List<TravelPackageGalleryModel> galleries = travelPackageGalleryRepository.findByTravelPackageId(travelPackageId);
        return galleries.stream().map(gallery -> travelPackageGalleryConverter.convertDTO(gallery)).collect(Collectors.toList());
    }

    //HARD DELETE
    @Transactional
    public boolean delete(int galleryId){
        Optional<TravelPackageGalleryModel> galleryOpt = travelPackageGalleryRepository.findById(galleryId);

        if (galleryOpt.isEmpty()){
            return false;
        }else {
            /*
            * 1. delete file in storage
            * 2. delete file path in db*/
            try{
                System.err.println(root + galleryOpt.get().getGalleryImage());
                //delete file in storage
                Files.deleteIfExists(Paths.get(root + "\\" + galleryOpt.get().getGalleryImage()));
                //delete file in db
                TravelPackageGalleryModel deleteGallery = galleryOpt.get();
                travelPackageGalleryRepository.delete(deleteGallery);

                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}
