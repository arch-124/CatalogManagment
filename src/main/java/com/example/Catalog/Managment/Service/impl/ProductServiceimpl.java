package com.example.Catalog.Managment.Service.impl;

import com.example.Catalog.Managment.Dto.ProductDto;
import com.example.Catalog.Managment.Dto.ProductcreateDto;
import com.example.Catalog.Managment.Dto.SkuCreateDto;
import com.example.Catalog.Managment.Entity.*;
import com.example.Catalog.Managment.Mapper.InventoryMapper;
import com.example.Catalog.Managment.Mapper.ProductMapper;
import com.example.Catalog.Managment.Mapper.SkuMapper;
import com.example.Catalog.Managment.Repository.*;
import com.example.Catalog.Managment.Response.ApiResponse;
import com.example.Catalog.Managment.Service.ProductService;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;


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
    private final SkuMapper skuMapper;
    private final InventoryMapper inventoryMapper;


    @Override
    @Transactional
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(ProductcreateDto dto) {

        try {

            Category category = categoryRepository.findById(Long.valueOf(dto.getCategoryId()))
                    .orElseThrow(() ->
                            new RuntimeException("Category does not exist. Create category first"));

            //  Reuse product if already exists

            List<Product> products = productRepository.findByName(dto.getName());

            Product savedProduct;

            if (!products.isEmpty()) {
                savedProduct = products.get(0); // pick first product
            } else {
                Product p = productMapper.toEntity(dto);
                p.setCategory(category);

                savedProduct = productRepository.save(p);

            }

            // Process SKUs
            for (SkuCreateDto skuDto : dto.getSkus()) {

                Sku existingSku = findExistingSku(savedProduct, skuDto);

                // MERGE STOCK
                if (existingSku != null) {

                    Inventory inv = inventoryRepository
                            .findBySkuId(existingSku.getId())
                            .orElseThrow();

                    int stock = skuDto.getInitialStock();

                    int currentQty = Math.toIntExact(inv.getQuantity() == null ? 0 : inv.getQuantity());
                    int currentAvail = inv.getAvailableQuantity() == null ? 0 : inv.getAvailableQuantity();

                    inv.setQuantity((long) (currentQty + stock));
                    inv.setAvailableQuantity(currentAvail + stock);

                    inventoryRepository.save(inv);
                    continue;
                }

                //NEW SKU
                Sku sku = skuMapper.toEntity(skuDto);
                sku.setProduct(savedProduct);
                sku.setSkucode(generateSkuCode(savedProduct, skuDto));


                Sku savedSku = skuRepository.save(sku);

                // Inventory
                Inventory inventory = inventoryMapper.toEntity(skuDto);
                inventory.setSku(savedSku);
                if (inventory.getQuantity() == null) {
                    inventory.setQuantity(Long.valueOf(skuDto.getInitialStock()));
                }

                if (inventory.getAvailableQuantity() == null) {
                    inventory.setAvailableQuantity(skuDto.getInitialStock());
                }

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
            ProductDto response = productMapper.toDto(savedProduct);

            if (savedProduct.getCategory() != null) {
                response.setCategoryId(Math.toIntExact(savedProduct.getCategory().getId()));
                response.setCategoryName(savedProduct.getCategory().getName());
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(
                            true,
                            HttpStatus.CREATED.value(),
                            "Product created/merged successfully",
                            response
                    ));

        } catch (Exception e) {
            log.error("Product creation failed", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(
                            false,
                            INTERNAL_SERVER_ERROR.value(),
                            e.getMessage(),
                            null
                    ));
        }
    }




    @Override
    public ResponseEntity<ApiResponse<ProductDto>> getProductbyid(Long id)
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
                    .map(product -> {

                        ProductDto dto = productMapper.toDto(product);

                        if (product.getCategory() != null) {
                            dto.setCategoryId(Math.toIntExact(product.getCategory().getId()));
                            dto.setCategoryName(product.getCategory().getName());
                        }

                        return dto;
                    })
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
    public ResponseEntity<ApiResponse<String>> deleteProduct(Long id) {
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
        public ResponseEntity<ApiResponse<ProductDto>> updateCategory(Long id, ProductDto dto)
        {
            try
            {   log.info("inside method");
                Product product = productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                log.info("product:{}",product);
                Category category = categoryRepository.findById(Long.valueOf(dto.getCategoryId()))
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
        @Override
        public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory (Long categoryId)
        {
            try
            {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                List<ProductDto> products = productRepository
                        .findByCategory_IdAndAvailabilityTrue(categoryId)
                        .stream()
                        .map(product -> {

                            ProductDto dto = productMapper.toDto(product);

                            if (product.getCategory() != null) {
                                dto.setCategoryId(Math.toIntExact(product.getCategory().getId()));
                                dto.setCategoryName(product.getCategory().getName());
                            }

                            return dto;
                        })

                        .toList();

                return ResponseEntity.ok(new ApiResponse<>(
                        true,
                        HttpStatus.OK.value(),
                        "products fetched by category ",
                        products
                ));

            }
            catch (Exception e)
            {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                        new ApiResponse<>(
                                false,
                                INTERNAL_SERVER_ERROR.value(),
                                "Failed to fetch products by category",
                                null
                        )
                );
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

    private Sku findExistingSku(Product product, SkuCreateDto dto) {

        List<Sku> skus = skuRepository.findByProductId(product.getId());

        for (Sku sku : skus) {

            List<SkuAttribute> attrs = skuAttributesRepository.findBySkuId(sku.getId());

            if (attributesMatch(attrs, dto.getAttributes())) {
                return sku;
            }
        }

        return null;
    }

    private boolean attributesMatch(
            List<SkuAttribute> existing,
            Map<String, String> incoming) {

        if (existing.size() != incoming.size()) return false;

        for (SkuAttribute e : existing) {

            String val = incoming.get(e.getName());
            if (val == null) return false;

            if (!val.equalsIgnoreCase(e.getValue())) return false;
        }

        return true;
    }



}
