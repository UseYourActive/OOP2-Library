package com.library.backend.operations;

import com.library.backend.mappers.CreateBookResponseConverter;
import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.LogInOperationProcessor;
import com.library.backend.operations.processors.contracts.CreateBookOperation;
import com.library.backend.operations.processors.contracts.CreateUserOperation;
import com.library.backend.operations.processors.CreateBookOperationProcessor;
import com.library.backend.operations.processors.CreateUserOperationProcessor;
import com.library.backend.operations.processors.contracts.LogInOperation;
import com.library.database.repositories.AuthorRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.GenreRepository;
import com.library.database.repositories.UserRepository;

public class OperationProcessorFactory {

    public CreateUserOperation getUserOperation(){
        return new CreateUserOperationProcessor(new UserRepository(),new CreateUserResponseConverter());
    }

    public CreateBookOperation getBookOperation(){
        return new CreateBookOperationProcessor(new BookRepository(),new GenreRepository(),new AuthorRepository(),new CreateBookResponseConverter());
    }

    public LogInOperation getLogInOperation(){
        return new LogInOperationProcessor(new UserRepository());
    }

}
