package com.sparkfusion.quiz.brainvoyage.api.dto.catalog_progress.progress;

import java.time.LocalDateTime;

public class GetCatalogProgressDto {

    private Long id;
    private Integer playCount;
    private LocalDateTime nextTryAt;

    public GetCatalogProgressDto(Long id, Integer playCount, LocalDateTime nextTryAt) {
        this.id = id;
        this.playCount = playCount;
        this.nextTryAt = nextTryAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public LocalDateTime getNextTryAt() {
        return nextTryAt;
    }

    public void setNextTryAt(LocalDateTime nextTryAt) {
        this.nextTryAt = nextTryAt;
    }
}
