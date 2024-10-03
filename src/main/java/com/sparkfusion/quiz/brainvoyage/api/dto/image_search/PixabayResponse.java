package com.sparkfusion.quiz.brainvoyage.api.dto.image_search;

import java.util.List;

public class PixabayResponse {

    private List<PixabayImageDto> hits;

    public PixabayResponse() {}

    public PixabayResponse(List<PixabayImageDto> hits) {
        this.hits = hits;
    }

    public List<PixabayImageDto> getHits() {
        return hits;
    }

    public void setHits(List<PixabayImageDto> hits) {
        this.hits = hits;
    }
}
