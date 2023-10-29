package com.library.operation.base;

public interface OperationProcessor <Response extends OperationOutput, Request extends OperationInput>{
    Response process(Request request);
}
