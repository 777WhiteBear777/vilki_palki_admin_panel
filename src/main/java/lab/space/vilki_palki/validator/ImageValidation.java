package lab.space.vilki_palki.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageValidation {
    public void imageContentTypeValidation(MultipartFile file, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || file == null) return;
        final String contentType = file.getContentType();
        if (contentType != null && !List.of("image/png", "image/jpg", "image/jpeg").contains(contentType)) {
            bindingResult.addError(new FieldError("StructureUpdateRequest", "image", "Choose image"));
        }
    }
}
