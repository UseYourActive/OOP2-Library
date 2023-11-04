package com.library.libraryproject.backend.operations.base;

public interface OperationProcessor<Response, Request> {
    Response process(Request request);
}
