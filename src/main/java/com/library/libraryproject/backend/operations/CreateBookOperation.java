package com.library.libraryproject.backend.operations;

import com.library.libraryproject.backend.operations.base.OperationProcessor;
import com.library.libraryproject.backend.requests.CreateBookRequest;
import com.library.libraryproject.backend.responses.CreateBookResponse;

public interface CreateBookOperation extends OperationProcessor<CreateBookResponse, CreateBookRequest> {
}
