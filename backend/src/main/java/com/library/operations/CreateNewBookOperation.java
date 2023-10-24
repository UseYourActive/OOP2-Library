package com.library.operations;

import com.library.operations.base.OperationProcessor;
import com.library.requests.CreateNewBookRequest;
import com.library.responses.CreateNewBookResponse;

public interface CreateNewBookOperation extends OperationProcessor<CreateNewBookResponse, CreateNewBookRequest> {
}
