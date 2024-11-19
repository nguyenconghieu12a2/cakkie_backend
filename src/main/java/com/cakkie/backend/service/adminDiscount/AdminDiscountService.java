package com.cakkie.backend.service.adminDiscount;

import com.cakkie.backend.dto.adminDiscount.*;
import com.cakkie.backend.exception.adminException.DiscountNotFoundException;
import com.cakkie.backend.model.category;
import com.cakkie.backend.model.discount;
import com.cakkie.backend.model.discountCategory;
import com.cakkie.backend.repository.adminDiscount.AdminCommonDiscountRepo;
import com.cakkie.backend.repository.adminDiscount.AdminDiscountRepo;
import com.cakkie.backend.repository.adminDiscount.AdminCategoryDiscountRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminDiscountService {
    private final AdminDiscountRepo adminDiscountRepo;
    private final AdminCommonDiscountRepo adminCommonDiscountRepo;
    private final AdminCategoryDiscountRepo adminCategoryDiscountRepo;

    //getCateDiscountList
    public List<CateDiscountListDTO> getCateDiscountList() {
        List<Object[]> cateDiscountList = adminDiscountRepo.getCateDiscountList();
        List<CateDiscountListDTO> cateDiscountListDTOList = new ArrayList<>();

        for (Object[] row : cateDiscountList) {
            int id = ((Number) row[0]).intValue();
            String name = (String) row[1];
            String currentDiscount = (String) row[2];
            double discountValue = ((Number) row[3]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "";
            } else {
                discountRate = discountValue + "";
            }

            CateDiscountListDTO cateDiscountListDTO = new CateDiscountListDTO(
                    id, name, currentDiscount, discountRate
            );
            cateDiscountListDTOList.add(cateDiscountListDTO);
        }
        return cateDiscountListDTOList;
    }

    //getCommon discount list
    public List<CommonDiscountListDTO> getCommonDiscountList() {
        List<Object[]> commonDiscountList = adminCommonDiscountRepo.getCommonDiscountList();
        List<CommonDiscountListDTO> commonDiscountListDTOList = new ArrayList<>();

        for (Object[] row : commonDiscountList) {
            int id = ((Number) row[0]).intValue();
            String name = (String) row[1];
            double discountValue = ((Number) row[2]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "";
            } else {
                discountRate = discountValue + "";
            }

            String startDate = (String) row[3];
            String endDate = (String) row[4];

            CommonDiscountListDTO commonDiscountListDTO = new CommonDiscountListDTO(
                    id, name, discountRate, startDate, endDate
            );
            commonDiscountListDTOList.add(commonDiscountListDTO);
        }
        return commonDiscountListDTOList;
    }

    //detail common discount with applied cate
    public DetailCommonDiscountDTO detailCommonDiscount(int id) {
        List<Object[]> detailCommonDiscount = adminCommonDiscountRepo.getDetailCommonDiscountById(id);
        List<Object[]> cateAppliedCommonDiscountList = adminDiscountRepo.getCateAppliedCommonDiscountById(id);
        List<CommonCateAppliedDTO> commonCateAppliedCommonDiscountList = new ArrayList<>();

        // Check if detailCommonDiscount is not null to avoid NullPointerException
        if (detailCommonDiscount.isEmpty()) {
            throw new DiscountNotFoundException("Sorry, discount not found with the id: " + id);
        }

        Object[] row = detailCommonDiscount.get(0);

        int discountId = ((Number) row[0]).intValue();
        String discountName = (String) row[1];
        double discountValue = ((Number) row[2]).doubleValue();

        // Check if the discount rate should be displayed with or without decimals
        String discountRate;
        if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
            discountRate = (int) discountValue + "";
        } else {
            discountRate = discountValue + "";
        }

        String description = (String) row[3];
        String startDate = (String) row[4];
        String endDate = (String) row[5];

        for (Object[] rows : cateAppliedCommonDiscountList) {
            int cateDisId = ((Number) rows[0]).intValue();
            int cateId = ((Number) rows[1]).intValue();
            String cateName = (String) rows[2];
            int isDeleted = ((Number) rows[3]).intValue();
            CommonCateAppliedDTO commonCateAppliedDTO = new CommonCateAppliedDTO(
                    cateDisId, cateId, cateName, isDeleted
            );
            commonCateAppliedCommonDiscountList.add(commonCateAppliedDTO);
        }
        return new DetailCommonDiscountDTO(
                discountId, discountName, description, discountRate, startDate, endDate, commonCateAppliedCommonDiscountList
        );
    }

    //list Cate NOT Applied Common Discount By Id
    public List<CommonCateAppliedDTO> cateNotAppliedCommonDiscount(int id) {
        List<Object[]> cateNotApplied = adminDiscountRepo.getCateNOTAppliedCommonDiscountById(id);

        List<CommonCateAppliedDTO> commonCateAppliedCommonDiscountList = new ArrayList<>();
        for (Object[] row : cateNotApplied) {
            int cateId = ((Number) row[0]).intValue();
            String cateName = (String) row[1];

            CommonCateAppliedDTO commonCateAppliedDTO = new CommonCateAppliedDTO(
                    cateId, cateName
            );
            commonCateAppliedCommonDiscountList.add(commonCateAppliedDTO);
        }
        return commonCateAppliedCommonDiscountList;
    }

    //discount by cate activate (wherether it's common or discrete)
    public List<DetailCateDiscountDTO> getDiscountByCategoryActivate(int id) {
        List<Object[]> cateActivate = adminDiscountRepo.getDiscountByCategoryActivate(id);
        List<DetailCateDiscountDTO> detailCateDiscountDTOList = new ArrayList<>();
        for (Object[] row : cateActivate) {
            int cateDisId = ((Number) row[0]).intValue();
            int disId = ((Number) row[1]).intValue();
            int cateId = ((Number) row[2]).intValue();
            String disName = (String) row[3];
            String des = (String) row[4];
            double discountValue = ((Number) row[5]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "";
            } else {
                discountRate = discountValue + "";
            }

            String startDate = (String) row[6];
            String endDate = (String) row[7];

            DetailCateDiscountDTO detailCateDiscountDTO = new DetailCateDiscountDTO(
                    cateDisId, disId, cateId, disName, des, discountRate, startDate, endDate
            );
            detailCateDiscountDTOList.add(detailCateDiscountDTO);
        }
        return detailCateDiscountDTOList;
    }

    //discrete discount by cate inactivate
    public List<DetailCateDiscountDTO> getDiscountByCategoryInactivate(int id) {
        List<Object[]> cateInactivate = adminDiscountRepo.getDiscountByCategoryInactivate(id);
        List<DetailCateDiscountDTO> detailCateDiscountDTOList = new ArrayList<>();
        for (Object[] row : cateInactivate) {
            int cateDisId = ((Number) row[0]).intValue();
            int disId = ((Number) row[1]).intValue();
            int cateId = ((Number) row[2]).intValue();
            String disName = (String) row[3];
            String des = (String) row[4];
            double discountValue = ((Number) row[5]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "";
            } else {
                discountRate = discountValue + "";
            }

            String startDate = (String) row[6];
            String endDate = (String) row[7];

            DetailCateDiscountDTO detailCateDiscountDTO = new DetailCateDiscountDTO(
                    cateDisId, disId, cateId, disName, des, discountRate, startDate, endDate
            );
            detailCateDiscountDTOList.add(detailCateDiscountDTO);
        }
        return detailCateDiscountDTOList;
    }

    //common discount by cate inactivate
    public List<DetailCateDiscountDTO> getCommonDiscountByCategoryInactivate(int id) {
        List<Object[]> cateInactivate = adminDiscountRepo.getCommonDiscountByCategoryInactivate(id);
        List<DetailCateDiscountDTO> detailCateDiscountDTOList = new ArrayList<>();
        for (Object[] row : cateInactivate) {
            int cateDisId = ((Number) row[0]).intValue();
            int disId = ((Number) row[1]).intValue();
            int cateId = ((Number) row[2]).intValue();
            String disName = (String) row[3];
            String des = (String) row[4];
            double discountValue = ((Number) row[5]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "";
            } else {
                discountRate = discountValue + "";
            }

            String startDate = (String) row[6];
            String endDate = (String) row[7];

            DetailCateDiscountDTO detailCateDiscountDTO = new DetailCateDiscountDTO(
                    cateDisId, disId, cateId, disName, des, discountRate, startDate, endDate
            );
            detailCateDiscountDTOList.add(detailCateDiscountDTO);
        }
        return detailCateDiscountDTOList;
    }

    //edit common discount
    public discount editCommonDiscount(int id, String name, String des, double discountRate, Date startDate, Date endDate) {
        Optional<discount> existsDiscountOpt = adminCommonDiscountRepo.findById(id);
        if (!existsDiscountOpt.isPresent()) {
            throw new IllegalArgumentException("Discount 1 not found with ID: " + id);
        }

        discount existingDiscount = existsDiscountOpt.get();

        // Log existing start and end dates for debugging purposes
        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("Current startDate in database: " + myFormatObj.format(existingDiscount.getStartDate()));
//        System.out.println("Current endDate in database: " + myFormatObj.format(existingDiscount.getEndDate()));

        // If new startDate or endDate is not provided, retain the existing ones
        Date finalStartDate = (startDate != null) ? startDate : existingDiscount.getStartDate();
        Date finalEndDate = (endDate != null) ? endDate : existingDiscount.getEndDate();

//        System.out.println("Final startDate to be set: " + myFormatObj.format(finalStartDate));
//        System.out.println("Final endDate to be set: " + myFormatObj.format(finalEndDate));

        // Update other fields
        existingDiscount.setName(name);
        existingDiscount.setDescription(des);
        existingDiscount.setDiscountRate(discountRate);
        existingDiscount.setStartDate(finalStartDate);
        existingDiscount.setEndDate(finalEndDate);
        existingDiscount.setIsDeleted(1);
        return adminCommonDiscountRepo.save(existingDiscount);
    }

    //add category to common discount
    public discountCategory addCateToCommon(int categoryId, int discountId) {
        discountCategory discountCategory = new discountCategory();

        discount discountCheck = adminCommonDiscountRepo.findById(discountId)
                .orElseThrow(() -> new IllegalArgumentException("Discount 2 not found with ID: " + discountId));
        category categoryCheck = adminCategoryDiscountRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category 1 not found with ID: " + categoryId));

        discountCategory.setCategoryId(categoryCheck);
        discountCategory.setDiscountId(discountCheck);
        discountCategory.setIsDeleted(0);

        return adminDiscountRepo.save(discountCategory);
    }

    //detail discrete discount
    public DetailDiscreteDiscountDTO detailDiscreteDiscount(int id) {
        List<Object[]> detailCommonDiscount = adminCommonDiscountRepo.getDetailCommonDiscountById(id);

        // Check if detailCommonDiscount is not null to avoid NullPointerException
        if (detailCommonDiscount.isEmpty()) {
            throw new DiscountNotFoundException("Sorry, discount not found with the id: " + id);
        }

        Object[] row = detailCommonDiscount.get(0);

        int discountId = ((Number) row[0]).intValue();
        String discountName = (String) row[1];
        double discountValue = ((Number) row[2]).doubleValue();

        // Check if the discount rate should be displayed with or without decimals
        String discountRate;
        if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
            discountRate = (int) discountValue + "";
        } else {
            discountRate = discountValue + "";
        }

        String description = (String) row[3];
        String startDate = (String) row[4];
        String endDate = (String) row[5];
        return new DetailDiscreteDiscountDTO(
                discountId, discountName, description, discountRate, startDate, endDate
        );
    }

    //add discrete discount
    public discount addDiscreteDiscount(String name, String description, double discountRate, Date startDate, Date endDate) {
        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        discount dis = new discount();

        dis.setName(name);
        dis.setDescription(description);
        dis.setDiscountRate(discountRate);
        dis.setStartDate(startDate);
        dis.setEndDate(endDate);
        dis.setIsDeleted(1);

        return adminCommonDiscountRepo.save(dis);
    }

    //map discrete created discount with selected category
    public discountCategory mapCateToDiscrete(int categoryId, discount createdDiscount) {
        // Fetch the category by its ID
        category categoryCheck = adminCategoryDiscountRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category 2 not found with ID: " + categoryId));

        // Create and map the discount category
        discountCategory discountCategory = new discountCategory();
        discountCategory.setCategoryId(categoryCheck);    // Set category
        discountCategory.setDiscountId(createdDiscount);  // Using the created discount here
        discountCategory.setIsDeleted(0); // Assuming not deleted

        // Save and return the discount-category mapping
        return adminDiscountRepo.save(discountCategory);
    }

    //edit discrete discount
    public discount editDiscreteDiscount(int id, String name, String des, double discountRate, Date startDate, Date endDate) {
        Optional<discount> existsDiscountOpt = adminCommonDiscountRepo.findById(id);
        if (!existsDiscountOpt.isPresent()) {
            throw new IllegalArgumentException("Discount 3 not found with ID: " + id);
        }

        discount existingDiscount = existsDiscountOpt.get();

        // Log existing start and end dates for debugging purposes
//        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("Current startDate in database: " + myFormatObj.format(existingDiscount.getStartDate()));
//        System.out.println("Current endDate in database: " + myFormatObj.format(existingDiscount.getEndDate()));

        // If new startDate or endDate is not provided, retain the existing ones
        Date finalStartDate = (startDate != null) ? startDate : existingDiscount.getStartDate();
        Date finalEndDate = (endDate != null) ? endDate : existingDiscount.getEndDate();

//        System.out.println("Final startDate to be set: " + myFormatObj.format(finalStartDate));
//        System.out.println("Final endDate to be set: " + myFormatObj.format(finalEndDate));

        // Update other fields
        existingDiscount.setName(name);
        existingDiscount.setDescription(des);
        existingDiscount.setDiscountRate(discountRate);
        existingDiscount.setStartDate(finalStartDate);
        existingDiscount.setEndDate(finalEndDate);
        existingDiscount.setIsDeleted(0);
        return adminCommonDiscountRepo.save(existingDiscount);
    }

    //remove current activate cateDiscount
    @Transactional
    public void removeCurrentDiscount(int id) {
        if (!adminCategoryDiscountRepo.existsById(id)) {
            throw new IllegalArgumentException("Category 3 not found with ID: " + id);
        }
        adminDiscountRepo.removeCurrentDiscount(id);
    }

    //replace current activate cateDiscount
    @Transactional
    public void replaceCurrentDiscount(int categoryId, int cateDisID) {
        if (!adminCategoryDiscountRepo.existsById(categoryId)) {
            throw new IllegalArgumentException("Category 4 not found with ID: " + categoryId);
        }
        if (!adminDiscountRepo.existsById(cateDisID)) {
            throw new IllegalArgumentException("Discount 4 not found with ID: " + cateDisID);
        }
        adminDiscountRepo.removeCurrentDiscount(categoryId);
        adminDiscountRepo.replaceCurrentDiscount(cateDisID);
    }

}
