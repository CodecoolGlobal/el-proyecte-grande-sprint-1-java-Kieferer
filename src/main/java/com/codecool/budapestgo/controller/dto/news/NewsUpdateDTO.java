package com.codecool.budapestgo.controller.dto.news;

import com.codecool.budapestgo.dao.model.News;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NewsUpdateDTO(
        @NotNull long id,
        @NotNull byte[] imgData,
        @NotNull @NotBlank String title,
        @NotNull  @NotBlank String description,
        @NotNull @NotBlank String articleText
) {
    public static NewsUpdateDTO of(News news) {
        return NewsUpdateDTO.builder()
                .id(news.getId())
                .imgData(news.getImgData())
                .title(news.getTitle())
                .description(news.getDescription())
                .articleText(news.getArticleText())
                .build();
    }
}
