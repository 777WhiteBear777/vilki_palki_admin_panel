package lab.space.vilki_palki.controller;

import jakarta.validation.Valid;
import lab.space.vilki_palki.model.product.ProductResponse;
import lab.space.vilki_palki.model.product.ProductSaveRequest;
import lab.space.vilki_palki.model.product.ProductUpdateRequest;
import lab.space.vilki_palki.model.product_category.ProductCategoryResponse;
import lab.space.vilki_palki.model.product_type.ProductTypeResponse;
import lab.space.vilki_palki.model.structure.StructureResponse;
import lab.space.vilki_palki.service.ProductCategoryService;
import lab.space.vilki_palki.service.ProductService;
import lab.space.vilki_palki.service.ProductTypeService;
import lab.space.vilki_palki.service.StructureService;
import lab.space.vilki_palki.util.ErrorMapper;
import lab.space.vilki_palki.validator.ImageValidation;
import lab.space.vilki_palki.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StructureService structureService;
    private final ProductCategoryService productCategoryService;
    private final ProductTypeService productTypeService;
    private final ProductValidator productValidator;
    private final ImageValidation imageValidation;

    @GetMapping({"/", ""})
    public String showProductPage() {
        return "/admin-panel/pages/product-structure/products";
    }

    @PostMapping("product-save")
    @ResponseBody
    public ResponseEntity<?> saveBanner(@Valid @ModelAttribute ProductSaveRequest request,
                                        BindingResult bindingResult) {
        productValidator.isNameUniqueValidation(request.name(), bindingResult);
        productValidator.isArrayValidation(request.structureIds(),bindingResult);
        imageValidation.imageContentTypeValidation(request.image(),bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        productService.saveProduct(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("product-update")
    @ResponseBody
    public ResponseEntity<?> updateBanner(@Valid @ModelAttribute ProductUpdateRequest request,
                                          BindingResult bindingResult) {
        productValidator.isNameUniqueValidationWithId(request.id(), request.name(), bindingResult);
        productValidator.isArrayValidation(request.structureIds(),bindingResult);
        imageValidation.imageContentTypeValidation(request.image(),bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        productService.updateProductById(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("product-delete/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-all-products")
    public ResponseEntity<List<ProductResponse>> getAllBanners() {
        return ResponseEntity.ok(productService.getAllProductsSimpleDtoWithImageByOrderByCreateAt());
    }

    @GetMapping("get-product/{id}")
    public ResponseEntity<ProductResponse> getBannerById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDto(id));
    }

    @GetMapping("get-all-structure-order-by-name")
    public ResponseEntity<List<StructureResponse>> getAllStructures() {
        return ResponseEntity.ok(structureService.getAllProductStructuresByOrderByName());
    }

    @GetMapping("get-all-product-types-order-by-name")
    public ResponseEntity<List<ProductTypeResponse>> getAllProductTypes() {
        return ResponseEntity.ok(productTypeService.getAllProductTypes());
    }

    @GetMapping("get-all-product-categories-order-by-name")
    public ResponseEntity<List<ProductCategoryResponse>> getAllProductCategories() {
        return ResponseEntity.ok(productCategoryService.getAllProductCategories());
    }
}
