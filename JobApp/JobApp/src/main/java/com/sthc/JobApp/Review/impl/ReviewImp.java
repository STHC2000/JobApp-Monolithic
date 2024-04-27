package com.sthc.JobApp.Review.impl;

import com.sthc.JobApp.Company.Company;
import com.sthc.JobApp.Company.CompanyService;
import com.sthc.JobApp.Review.Review;
import com.sthc.JobApp.Review.ReviewRepository;
import com.sthc.JobApp.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewImp implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewImp(ReviewRepository reviewRepository,CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService=companyService;
    }

    @Override
    public List<Review> getAllReview(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if (company!=null)
        {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updateReview) {
        if (companyService.getCompanyById(companyId) != null){
            updateReview.setCompany(companyService.getCompanyById(companyId));
            updateReview.setId(reviewId);
            reviewRepository.save(updateReview);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteById(Long companyId, Long reviewId) {
        if (companyService.getCompanyById(companyId)!=null && reviewRepository.existsById(reviewId)){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = companyService.getCompanyById(companyId);
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId,company);
            reviewRepository.deleteById(reviewId);
            return true;
        }else{
            return false;
        }

    }
}
