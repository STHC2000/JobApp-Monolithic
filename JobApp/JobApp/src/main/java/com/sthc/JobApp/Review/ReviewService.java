package com.sthc.JobApp.Review;

import com.sthc.JobApp.Job.Job;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ReviewService {
    List<Review> getAllReview(Long companyId);
    boolean addReview(Long companyId,Review review);
    Review getReview(Long companyId,Long reviewId);

    boolean updateReview(Long companyId,Long reviewId, Review updateReview);

    boolean deleteById(Long companyId,Long reviewId);
}
