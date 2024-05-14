package kfoodbox.food.entity;

import lombok.Getter;

@Getter
public class KoreaRestaurant {
    private Long id;
    private Long koreaRestaurantTagId;
    private String name;
    private String address;
    private String phoneNumber;
    private String openTime;
    private String imageUrl;
    private Long visitorReviewCount;
    private Double visitorRating;
    private Long blogReviewCount;
    private Long photoReviewCount;
    private String latitude;
    private String longitude;
    private String information;
    private String additionalExplanation;
    private String homepageUrl;
    private String detailedPageUrl;
}
