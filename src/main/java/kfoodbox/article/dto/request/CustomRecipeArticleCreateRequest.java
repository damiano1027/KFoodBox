package kfoodbox.article.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomRecipeArticleCreateRequest {
    @NotBlank
    @Size(min = 1, max = 200)
    @Schema(description = "제목\n" +
                          "- Not null\n" +
                          "- 길이가 1 이상 200 이하\n" +
                          "- 공백문자만 있으면 안됨\n")
    private String title;

    @Schema(description = "음식 id")
    private Long foodId;

    @NotBlank @Size(min = 1, max = 16777215)
    @Schema(description = "내용\n" +
                          "- Not null\n" +
                          "- 길이가 1 이상 16777215 이하\n" +
                          "- 공백문자만 있으면 안됨\n")
    private String content;

    @NotNull @Size(min = 1)
    @Schema(description = "조리 순서 정보 리스트\n" +
                          "- Not null\n" +
                          "- 리스트의 길이가 1 이상")
    private List<Sequence> sequences;

    @NotNull @Size(min = 1)
    @Schema(description = "재료 정보 리스트\n" +
                          "- Not null\n" +
                          "- 리스트의 길이가 1 이상")
    private List<Ingredient> ingredients;

    @NotNull @Size(max = 10)
    @Schema(description = "이미지 URL 리스트\n" +
                          "- Not null\n" +
                          "- 리스트의 길이가 10 이하")
    private List<String> imageUrls;

    @Getter
    public static class Sequence {
        @NotNull @Positive
        @Schema(description = "순서 번호\n" +
                              "- Not null\n" +
                              "- 양의 정수")
        private Long sequenceNumber;

        @NotNull @Size(min = 1, max = 500)
        @Schema(description = "내용\n" +
                              "- Not null\n" +
                              "- 길이가 1 이상 500 이하")
        private String content;

        @Schema(description = "이미지 URL")
        private String imageUrl;
    }

    @Getter
    public static class Ingredient {
        @NotNull @Size(min = 1, max = 500)
        @Schema(description = "재료 이름\n" +
                              "- Not null\n" +
                              "- 길이가 1 이상 500 이하")
        private String name;

        @NotNull @Size(min = 1, max = 500)
        @Schema(description = "재료의 양\n" +
                              "- Not null\n" +
                              "- 길이가 1 이상 500 이하")
        private String quantity;
    }
}
