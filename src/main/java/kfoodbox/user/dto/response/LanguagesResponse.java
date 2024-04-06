package kfoodbox.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LanguagesResponse {
    @Schema(description = "언어 리스트")
    private List<Language> languages;

    @Getter @Builder
    private static class Language {
        @Schema(description = "언어 id")
        private Long id;
        @Schema(description = "언어 이름")
        private String name;
    }

    public LanguagesResponse(List<kfoodbox.user.entity.Language> languageEntities) {
        languages = languageEntities.stream().map(languageEntity ->
                Language.builder()
                        .id(languageEntity.getId())
                        .name(languageEntity.getName())
                        .build()
                ).collect(Collectors.toList()
        );
    }
}
