package com.project.backend.operations.processors.contracts;

public interface OperationProcessor<Response, Request> {
    Response process(Request request);
}
