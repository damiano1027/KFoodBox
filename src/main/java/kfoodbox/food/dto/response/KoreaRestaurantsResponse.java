package kfoodbox.food.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kfoodbox.food.entity.KoreaRestaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter @Builder
public class KoreaRestaurantsResponse {
    @Schema(description = "식당 리스트")
    private List<Restaurant> restaurants;

    @Getter @Builder
    public static class Restaurant {
        @Schema(description = "고유 id")
        private Long id;
        @Schema(description = "이름")
        private String name;
        @Schema(description = "주소")
        private String address;
        @Schema(description = "전화번호")
        private String phoneNumber;
        @Schema(description = "썸네일 이미지 URL")
        private String imageUrl;
        @Schema(description = "방문자 리뷰수 (네이버 기준)")
        private Long visitorReviewCount;
        @Schema(description = "방문자 평점 (네이버 기준)")
        private Double visitorRating;
        @Schema(description = "블로그 리뷰수 (네이버 기준)")
        private Long blogReviewCount;
        @Schema(description = "사진 리뷰수 (네이버 기준)")
        private Long photoReviewCount;
        @Schema(description = "위도")
        private String latitude;
        @Schema(description = "경도")
        private String longitude;
        @Schema(description = "정보")
        private String information;
        @Schema(description = "부가 설명")
        private String additionalExplanation;
        @Schema(description = "홈페이지 URL")
        private String homepageUrl;
    }

    public static KoreaRestaurantsResponse from(List<KoreaRestaurant> koreaRestaurantEntities) {
        List<Restaurant> restaurants = new ArrayList<>();

        koreaRestaurantEntities.forEach(koreaRestaurant -> {
            restaurants.add(
                    Restaurant.builder()
                            .id(koreaRestaurant.getId())
                            .name(koreaRestaurant.getName())
                            .address(koreaRestaurant.getAddress())
                            .phoneNumber(koreaRestaurant.getPhoneNumber())
                            .imageUrl(koreaRestaurant.getImageUrl())
                            .visitorReviewCount(koreaRestaurant.getVisitorReviewCount())
                            .visitorRating(koreaRestaurant.getVisitorRating())
                            .blogReviewCount(koreaRestaurant.getBlogReviewCount())
                            .photoReviewCount(koreaRestaurant.getPhotoReviewCount())
                            .latitude(koreaRestaurant.getLatitude())
                            .longitude(koreaRestaurant.getLongitude())
                            .information(koreaRestaurant.getInformation())
                            .additionalExplanation(koreaRestaurant.getAdditionalExplanation())
                            .homepageUrl(koreaRestaurant.getHomepageUrl())
                            .build()
            );
        });

        return KoreaRestaurantsResponse.builder()
                .restaurants(restaurants)
                .build();
    }
}
