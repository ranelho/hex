package com.rlti.hex.adapters.input.api.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements
) {
    public static <T> PageResult<T> from(Page<T> page) {
        return new PageResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }
}
