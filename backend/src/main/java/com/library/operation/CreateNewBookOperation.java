package com.library.operation;

import com.library.operation.base.OperationProcessor;
import com.library.requests.CreateNewBookRequest;
import com.library.responses.CreateNewBookResponse;

public interface CreateNewBookOperation extends OperationProcessor<CreateNewBookResponse, CreateNewBookRequest> {
}
