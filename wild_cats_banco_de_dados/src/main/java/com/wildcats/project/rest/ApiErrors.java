package com.wildcats.project.rest;

import lombok.Getter;

import java.util.List;

public class ApiErrors {

    @Getter
    private final List<String> errors;

    public ApiErrors(List<String> errors) { this.errors = errors; }
}
