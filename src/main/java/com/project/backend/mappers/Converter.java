package com.project.backend.mappers;

public interface Converter <Type,Response> {
    Response convert(Type source);
}
