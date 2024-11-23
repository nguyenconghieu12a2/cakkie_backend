package com.cakkie.backend.controller.adminDiscount;

import com.cakkie.backend.dto.adminDiscount.*;
import com.cakkie.backend.model.discount;
import com.cakkie.backend.model.discountCategory;
import com.cakkie.backend.service.adminDiscount.AdminDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminDiscountController {
    private final AdminDiscountService adminDiscountService;

    //getCate discount list
    @GetMapping("/category-discount-list")
    public List<CateDiscountListDTO> getCateDiscountList() {
        return adminDiscountService.getCateDiscountList();
    }

    //get common discount list
    @GetMapping("/common-discount-list")
    public List<CommonDiscountListDTO> getCommonDiscountList() {
        return adminDiscountService.getCommonDiscountList();
    }

    //detail common discount with applied cate
    @GetMapping("/detail-common-discount/{id}")
    public DetailCommonDiscountDTO getDetailCommonDiscount(@PathVariable int id) {
        return adminDiscountService.detailCommonDiscount(id);
    }

    //list Cate NOT Applied Common Discount By Id
    @GetMapping("/cate-not-applied-common-discount/{id}")
    public List<CommonCateAppliedDTO> getCateNotAppliedCommonDiscount(@PathVariable int id) {
        return adminDiscountService.cateNotAppliedCommonDiscount(id);
    }

    //discount by cate activate (wherether it's common or discrete)
    @GetMapping("/detail-discount-activate/{id}")
    public List<DetailCateDiscountDTO> getDetailCateActivate(@PathVariable int id) {
        return adminDiscountService.getDiscountByCategoryActivate(id);
    }

    //discrete discount by cate inactivate
    @GetMapping("/detail-discount-inactivate/{id}")
    public List<DetailCateDiscountDTO> getDetailCateInactivate(@PathVariable int id) {
        return adminDiscountService.getDiscountByCategoryInactivate(id);
    }

    //common discount by cate inactivate
    @GetMapping("/detail-common-discount-activate/{id}")
    public List<DetailCateDiscountDTO> getDetailCommonCateInactivate(@PathVariable int id) {
        return adminDiscountService.getCommonDiscountByCategoryInactivate(id);
    }

    //edit common discount
    @PutMapping("/edit-common-discount/{id}")
    public ResponseEntity<discount> editCommonDiscount(@PathVariable int id, @RequestBody CommonDiscountDTO discountDTO) {
        try {
            discount editDiscount = adminDiscountService.editCommonDiscount(
                    id,
                    discountDTO.getName(),
                    discountDTO.getDescription(),
                    discountDTO.getDiscountRate(),
                    discountDTO.getStartDate(),
                    discountDTO.getEndDate());
            return ResponseEntity.ok(editDiscount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //add category to common discount
    @PostMapping("/add-category-to-common")
    public ResponseEntity<discountCategory> addCateToCommon(@RequestBody AddCateToCommonDTO addCateToCommonDTO) {
        try {
            discountCategory addCateToCommon = adminDiscountService.addCateToCommon(addCateToCommonDTO.getCategoryId(), addCateToCommonDTO.getDiscountId());
            return ResponseEntity.ok(addCateToCommon);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //detail discrete discount
    @GetMapping("/detail-discrete-discount/{id}")
    public DetailDiscreteDiscountDTO getDetailDiscreteDiscount(@PathVariable int id) {
        return adminDiscountService.detailDiscreteDiscount(id);
    }

    //add discrete discount
    @PostMapping("/add-discrete-discount/{id}")
    public ResponseEntity<?> addDiscreteDiscount(@PathVariable int id, @RequestBody CommonDiscountDTO discreteDiscountDTO) {
        try {
            // Step 1: Create a new discount
            discount createdDiscount = adminDiscountService.addDiscreteDiscount(
                    discreteDiscountDTO.getName(),
                    discreteDiscountDTO.getDescription(),
                    discreteDiscountDTO.getDiscountRate(),
                    discreteDiscountDTO.getStartDate(),
                    discreteDiscountDTO.getEndDate()
            );

            // Step 2: Map the created discount to the selected category
            discountCategory mappedDiscountCategory = adminDiscountService.mapCateToDiscrete(id, createdDiscount);

            // Return both the created discount and mapped category in the response body
            return ResponseEntity.ok(new ApiResponseDTO("Discount created and mapped successfully", createdDiscount, mappedDiscountCategory));

        } catch (IllegalArgumentException e) {
            // Return the error message in the response body with BAD_REQUEST status
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //edit discrete discount
    @PutMapping("/edit-discrete-discount/{id}")
    public ResponseEntity<discount> editDiscreteDiscount(@PathVariable int id, @RequestBody CommonDiscountDTO discountDTO) {
        try {
            discount editDiscount = adminDiscountService.editDiscreteDiscount(
                    id,
                    discountDTO.getName(),
                    discountDTO.getDescription(),
                    discountDTO.getDiscountRate(),
                    discountDTO.getStartDate(),
                    discountDTO.getEndDate());
            return ResponseEntity.ok(editDiscount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //remove current activate cateDiscount
    @PutMapping("/remove-current-discount/{id}")
    public void removeCurrentDiscount(@PathVariable int id) {
        adminDiscountService.removeCurrentDiscount(id);
    }

    //replace current activate cateDiscount
    @PutMapping("/replace-current-discount/{cateid}/{catedisid}")
    public void removeCurrentDiscount(@PathVariable int cateid, @PathVariable int catedisid) {
        adminDiscountService.replaceCurrentDiscount(cateid, catedisid);
    }

    //add commmon discount
    @PostMapping("/add-common-discount")
    public ResponseEntity<?> addCommonDiscount(@RequestBody AddCommonDiscountDTO addCommonDiscountDTO) {
        try {
            //Step 1: Create new discount
            discount createDiscount = adminDiscountService.addDiscreteDiscount(
                    addCommonDiscountDTO.getName(),
                    addCommonDiscountDTO.getDescription(),
                    addCommonDiscountDTO.getDiscountRate(),
                    addCommonDiscountDTO.getStartDate(),
                    addCommonDiscountDTO.getEndDate()
            );
            //Step 2: Get List id of applyCategory from AddCommonDiscountDTO and map to created discount
            List<discountCategory> mappedDiscountCategories = new ArrayList<>();
            for (Integer categoryId  : addCommonDiscountDTO.getApplyCategory()) {
                discountCategory mappedDiscountCategory = adminDiscountService.mapCateToDiscrete(categoryId, createDiscount);
                mappedDiscountCategories.add(mappedDiscountCategory);
            }
            return ResponseEntity.ok(new ApiResponseDTO("Discount created and mapped successfully", createDiscount, mappedDiscountCategories));

        }catch (IllegalArgumentException e) {
            // Return error message in case of invalid category or other exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO("Error: " + e.getMessage(), null, null));
        }
//        catch (Exception e) {
//            // Return error message for unexpected exceptions
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO("Internal server error", null, null));
//        }
    }
}
