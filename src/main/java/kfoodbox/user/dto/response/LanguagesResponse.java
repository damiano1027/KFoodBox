package kfoodbox.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LanguagesResponse {
    private List<Language> languages;

    @Getter @Builder
    private static class Language {
        private Long id;
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
