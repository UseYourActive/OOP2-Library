package com.library.libraryproject.backend.operations;

import com.library.libraryproject.backend.operations.base.OperationProcessor;
import com.library.libraryproject.backend.requests.CreateUserRequest;
import com.library.libraryproject.backend.responses.CreateUserResponse;

public interface CreateUserOperation extends OperationProcessor<CreateUserResponse, CreateUserRequest> {
}
