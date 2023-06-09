package lab.space.vilki_palki.controller;

import lab.space.vilki_palki.config.SecurityConfig;
import lab.space.vilki_palki.model.product.ProductSaveRequest;
import lab.space.vilki_palki.model.product.ProductUpdateRequest;
import lab.space.vilki_palki.service.ProductCategoryService;
import lab.space.vilki_palki.service.ProductService;
import lab.space.vilki_palki.service.ProductTypeService;
import lab.space.vilki_palki.service.StructureService;
import lab.space.vilki_palki.validator.ImageValidation;
import lab.space.vilki_palki.validator.ProductValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(ProductController.class)
@WithMockUser
public class ProductControllerTest {

    private final String DEFAULT_PATH = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private StructureService structureService;
    @MockBean
    private ProductCategoryService productCategoryService;
    @MockBean
    private ProductTypeService productTypeService;
    @MockBean
    private ProductValidation productValidation;
    @MockBean
    private ImageValidation imageValidation;

    @Test
    void testShowProductPage() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testSaveProduct() throws Exception {
        ClassPathResource imageResource = new ClassPathResource("img/img.png");
        MockMultipartFile image = new MockMultipartFile("image", "image", "image/png", imageResource.getInputStream());
        ProductSaveRequest request = new ProductSaveRequest();
        request.setName("Product 1");
        request.setProductInfo("Info");
        request.setPrice(BigDecimal.valueOf(100));
        request.setDescription("Description");
        request.setImage(image);
        request.setProductsCategoryId(1L);
        request.setProductsTypeId(1L);
        request.setStructureIds(List.of(1L));


        mockMvc.perform(multipart(DEFAULT_PATH + "/product-save")
                        .file(image)
                        .param("name", request.getName())
                        .param("productInfo", request.getProductInfo())
                        .param("price", String.valueOf(request.getPrice()))
                        .param("description", request.getDescription())
                        .param("productsCategoryId", String.valueOf(request.getProductsCategoryId()))
                        .param("productsTypeId", String.valueOf(request.getProductsTypeId()))
                        .param("structureIds", String.valueOf(request.getStructureIds().get(0)))
                )

                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    void testUpdateProduct() throws Exception {
        ClassPathResource imageResource = new ClassPathResource("img/img.png");
        MockMultipartFile image = new MockMultipartFile("image", "image", "image/png", imageResource.getInputStream());
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setId(1L);
        request.setName("Product 1");
        request.setProductInfo("Info");
        request.setPrice(BigDecimal.valueOf(100));
        request.setDescription("Description");
        request.setImage(image);
        request.setProductsCategoryId(1L);
        request.setProductsTypeId(1L);
        request.setStructureIds(List.of(1L));

        mockMvc.perform(
                        multipart(HttpMethod.PUT, DEFAULT_PATH + "/product-update")
                                .file(image)
                                .param("name", request.getName())
                                .param("productInfo", request.getProductInfo())
                                .param("price", String.valueOf(request.getPrice()))
                                .param("description", request.getDescription())
                                .param("productsCategoryId", String.valueOf(request.getProductsCategoryId()))
                                .param("productsTypeId", String.valueOf(request.getProductsTypeId()))
                                .param("structureIds", String.valueOf(request.getStructureIds().get(0)))
                                .param("id", String.valueOf(request.getId()))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH + "/get-all-products"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductById() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH + "/get-product/" + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProductById() throws Exception {
        mockMvc.perform(delete(DEFAULT_PATH + "/product-delete/" + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testGetAllStructures() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH + "/get-all-structure-order-by-name"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllProductTypes() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH + "/get-all-product-types-order-by-name"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllProductCategories() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH + "/get-all-product-categories-order-by-name"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
