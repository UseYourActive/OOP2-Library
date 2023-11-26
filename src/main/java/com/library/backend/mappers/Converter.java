package com.library.backend.mappers;

public interface Converter <Type,Response> {
    Response convert(Type source);
}
