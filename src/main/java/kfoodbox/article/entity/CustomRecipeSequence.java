package kfoodbox.article.entity;

import kfoodbox.article.dto.request.CustomRecipeArticleCreateRequest;
import kfoodbox.article.dto.request.CustomRecipeArticleUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
public class CustomRecipeSequence {
    private Long id;
    private Long customRecipeArticleId;
    private Long sequenceNumber;
    private String content;
    private String imageUrl;

    public static CustomRecipeSequence from(long customRecipeArticleId, CustomRecipeArticleUpdateRequest.Sequence sequence) {
        return CustomRecipeSequence.builder()
                .customRecipeArticleId(customRecipeArticleId)
                .sequenceNumber(sequence.getSequenceNumber())
                .content(sequence.getContent())
                .imageUrl(sequence.getImageUrl())
                .build();
    }

    public static List<CustomRecipeSequence> from(long customRecipeArticleId, CustomRecipeArticleCreateRequest request) {
        return request.getSequences().stream()
                .map(sequence -> CustomRecipeSequence.builder()
                        .customRecipeArticleId(customRecipeArticleId)
                        .sequenceNumber(sequence.getSequenceNumber())
                        .content(sequence.getContent())
                        .imageUrl(sequence.getImageUrl())
                        .build()
                ).collect(Collectors.toList());
    }

    public static List<CustomRecipeSequence> from(long customRecipeArticleId, CustomRecipeArticleUpdateRequest request) {
        return request.getSequences().stream()
                .map(sequence -> CustomRecipeSequence.builder()
                        .customRecipeArticleId(customRecipeArticleId)
                        .sequenceNumber(sequence.getSequenceNumber())
                        .content(sequence.getContent())
                        .imageUrl(sequence.getImageUrl())
                        .build()
                ).collect(Collectors.toList());
    }
}
