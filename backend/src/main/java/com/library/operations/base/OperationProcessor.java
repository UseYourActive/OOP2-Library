package com.library.operations.base;

public interface OperationProcessor <Response extends OperationOutput, Request extends OperationInput>{
    Response process(Request request);
}
