package lab.space.vilki_palki.service.impl;

import lab.space.vilki_palki.entity.ProductType;
import lab.space.vilki_palki.model.product_type.ProductTypeRequest;
import lab.space.vilki_palki.model.product_type.ProductTypeResponse;
import lab.space.vilki_palki.model.product_type.ProductTypeSaveRequest;
import lab.space.vilki_palki.model.product_type.ProductTypeUpdateRequest;
import lab.space.vilki_palki.repository.ProductTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductTypeServiceImplTest {
    @Mock
    private ProductTypeRepository repository;
    @Mock
    private ProductTypeSpecification productTypeSpecification;

    @InjectMocks
    private ProductTypeServiceImpl productTypeService;

    @Test
    void getProductTypeById() {
        Long productTypeId = 1L;
        ProductType productType = new ProductType();
        productType.setId(productTypeId);

        when(repository.findById(productTypeId)).thenReturn(Optional.of(productType));

        ProductType productType1 = productTypeService.getProductTypeById(productTypeId);
        assertEquals(productType, productType1);
        verify(repository, times(1)).findById(productTypeId);
    }

    @Test
    void getProductTypeToDto() {
        Long id = 1L;
        ProductType productType = new ProductType().setName("Bober");
        productType.setId(1L);
        ProductTypeResponse expectedResponse = ProductTypeResponse.builder().id(1L).name("Bober").build();
        when(repository.findById(id)).thenReturn(Optional.of(productType));

        ProductTypeResponse response = productTypeService.getProductTypeToDto(productType.getId());

        assertEquals(expectedResponse, response);
    }

    @Test
    void getAllProductTypes() {
        List<ProductType> productTypes = List.of(new ProductType(), new ProductType());
        productTypes.get(0).setId(1L);
        productTypes.get(0).setName("123");
        productTypes.get(1).setId(2L);
        productTypes.get(1).setName("5435");
        List<ProductTypeResponse> response = List.of(
                ProductTypeResponse.builder().id(1L).name("123").build(),
                ProductTypeResponse.builder().id(2L).name("5435").build()
        );

        when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(productTypes);
        List<ProductTypeResponse> actualResponses = productTypeService.getAllProductTypes();

        assertEquals(response, actualResponses);
    }

    @Test
    void getAllProductTypesByOrderByCreateAt() {
        int pageIndex = 1;
        String query = "";
        ProductTypeRequest request = new ProductTypeRequest();
        request.setPageIndex(pageIndex);
        request.setQuery(query);

        List<ProductType> productTypes = List.of(new ProductType(), new ProductType());
        productTypes.get(0).setId(1L);
        productTypes.get(0).setName("123");
        productTypes.get(1).setId(2L);
        productTypes.get(1).setName("5435");
        Page<ProductType> orderPage = new PageImpl<>(productTypes);

        when(repository.findAll((Specification<ProductType>) any(), any(PageRequest.class))).thenReturn(orderPage);
        Page<ProductTypeResponse> actualResponses = productTypeService.getAllProductTypesByOrderByCreateAt(request);

        assertEquals(productTypes.size(), actualResponses.getTotalElements());
    }

    @Test
    void saveProductType() {
        ProductTypeSaveRequest request = new ProductTypeSaveRequest();
        request.setName("Name");

        productTypeService.saveProductType(request);

        verify(repository, times(1)).save(any(ProductType.class));
    }

    @Test
    void updateProductType() {
        ProductTypeUpdateRequest request = new ProductTypeUpdateRequest();
        request.setId(1L);
        request.setName("Name");
        ProductType productType = new ProductType().setName("name");
        productType.setId(1L);

        when(repository.findById(request.getId())).thenReturn(Optional.of(productType));

        productTypeService.updateProductType(request);

        verify(repository, times(1)).save(any(ProductType.class));
    }

    @Test
    void deleteProductTypeById() {
        Long productTypeId = 1L;
        ProductType productType = new ProductType();
        productType.setId(productTypeId);

        when(repository.findById(productTypeId)).thenReturn(Optional.of(productType));

        productTypeService.deleteProductTypeById(productTypeId);

        verify(repository, times(1)).findById(productTypeId);
        verify(repository, times(1)).delete(productType);
    }
}