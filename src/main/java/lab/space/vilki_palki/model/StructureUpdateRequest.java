package lab.space.vilki_palki.model;

import lab.space.vilki_palki.entity.StructureCategory;
import org.springframework.web.multipart.MultipartFile;

public record StructureUpdateRequest(
        Long id,
        String title,
        StructureCategory structureCategory,
        Integer weight,
        Integer price,
        MultipartFile image
){}
