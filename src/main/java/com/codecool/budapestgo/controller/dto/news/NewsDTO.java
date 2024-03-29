package com.codecool.budapestgo.controller.dto.news;

import com.codecool.budapestgo.dao.model.News;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NewsDTO(
        @NotNull byte[] imgData,
        @NotNull @NotBlank String title,
        @NotNull  @NotBlank String description,
        @NotNull @NotBlank String articleText
) {
    public static NewsDTO of(News news) {
        return NewsDTO.builder()
                .imgData(news.getImgData())
                .title(news.getTitle())
                .description(news.getDescription())
                .articleText(news.getArticleText())
                .build();
    }
}
