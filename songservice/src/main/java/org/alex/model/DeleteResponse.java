package org.alex.model;

import lombok.Builder;

import java.util.List;

@Builder
public record DeleteResponse(List<Long> ids) {
}