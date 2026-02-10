package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Dto.ProductcreateDto;
import com.example.Catalog.Managment.Dto.SkuCreateDto;
import com.example.Catalog.Managment.Entity.*;
import com.example.Catalog.Managment.Mapper.ProductMapper;
import com.example.Catalog.Managment.Repository.*;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor

public class ProductServiceimpl implements ProductService
{
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private  final InventoryRepository inventoryRepository;
    private final SkuAttributesRepository skuAttributesRepository;
    private final SkuRepository skuRepository;


    @Override
    @Transactional
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(ProductcreateDto dto) {

        try {

            Category category = new Category();
            category.setName(dto.getCategory().getName());
            category.setDescription(dto.getCategory().getDescription());
            category.setActive(true);

            Category savedCategory = categoryRepository.save(category);


            Product product = new Product();
            product.setName(dto.getName());
            product.setCategory(savedCategory);
            product.setAvailability(true);

            Product savedProduct = productRepository.save(product);


            for (SkuCreateDto skuDto : dto.getSkus()) {

                Sku sku = new Sku();
                sku.setProduct(savedProduct);
                sku.setPrice(skuDto.getPrice());
                sku.setActive(true);
                sku.setSkucode(generateSkuCode(savedProduct, skuDto));

                Sku savedSku = skuRepository.save(sku);

                // Inventory
                Inventory inventory = new Inventory();
                inventory.setSku(savedSku);
                inventory.setQuantity(skuDto.getInitialStock());
                inventoryRepository.save(inventory);

                // Attributes
                for (Map.Entry<String, String> attr : skuDto.getAttributes().entrySet()) {
                    SkuAttribute attribute = new SkuAttribute();
                    attribute.setSku(savedSku);
                    attribute.setName(attr.getKey());
                    attribute.setValue(attr.getValue());

                    skuAttributesRepository.save(attribute);
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            HttpStatus.CREATED.value(),
                            "Product with category, SKUs, attributes and inventory created",
                            productMapper.toDto(savedProduct)
                    ));

        } catch (Exception e) {
            log.error("Product creation failed", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            INTERNAL_SERVER_ERROR.value(),
                            "Failed to create product",
                            null
                    ));
        }
    }




    @Override
    public ResponseEntity<ApiResponse<ProductDto>> getProductbyid(int id)
    {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found with given id"));
            return ResponseEntity.ok(
                    new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Fetched product by id successfully",
                    productMapper.toDto(product)

            ));
        }
            catch(Exception e)
            {
                log.error("Failed to get Product", e);
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(
                                false,
                                INTERNAL_SERVER_ERROR.value(),
                                "Failed to fetch product by id",
                                null

                        ));
            }

    }

    @Override
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(ProductDto dto)
    {
        try {
            Product existing = productRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            existing.setName(dto.getName());


            Product updated = productRepository.save(existing);
            return ResponseEntity.ok(
                    new  ApiResponse<>(
                            true,
                            HttpStatus.OK.value(),
                            "Updated product successfully",
                            productMapper.toDto(updated)
                    )
            );
        }
        catch (Exception e)
        {
            log.error("Failed to update Product",e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            INTERNAL_SERVER_ERROR.value(),
                            "Failed to update product",
                            null
                            )
                    );
        }

    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllproducts() {
        try
        {
            List<ProductDto> products = productRepository.findByAvailabilityTrue()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
            return ResponseEntity.ok(new  ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "fetched all products successfully",
                    products

            ));
        }
        catch(Exception e)
        {
            log.error("Failed to get Products",e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            INTERNAL_SERVER_ERROR.value(),
                            "Failed to get all products",
                            null
                    ));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteProduct(int id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            product.setAvailability(false);
            return ResponseEntity.ok
                    (new ApiResponse<>(
                            true,
                            HttpStatus.OK.value(),
                            "Deleted product successfully",
                            "Deleted product with id: " + id

                    ));
        } catch (Exception e) {
            log.error("Failed to delete Product", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            INTERNAL_SERVER_ERROR.value(),
                            "Failed to delete product",
                            null
                    ));
        }
    }
        public ResponseEntity<ApiResponse<ProductDto>> updateCategory(int id, ProductDto dto)
        {
            try
            {   log.info("inside method");
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                log.info("product:{}",product);
                Category category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                product.setCategory(category);
                Product newcategory = productRepository.save(product);

                return ResponseEntity.ok(new  ApiResponse<>(
                                true,
                                HttpStatus.OK.value(),
                                "category updated",
                        productMapper.toDto(newcategory)
                ));


            } catch (Exception e)
            {
                log.error("Failed to update Product category",e);
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(
                                false,
                                INTERNAL_SERVER_ERROR.value(),
                                "Failed to category update",
                                null
                        ));
            }

        }



    private String generateSkuCode(Product product, SkuCreateDto skuDto) {

        String attrPart = skuDto.getAttributes()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, String>comparingByKey())
                .map(e -> e.getValue().replaceAll("\\s+", "").toUpperCase())

                .reduce((a, b) -> a + "-" + b)
                .orElse("BASE");

        return product.getName()
                .replaceAll("\\s+", "")
                .toUpperCase()
                + "-"
                + attrPart
                + "-"
                + System.nanoTime();
    }

}
