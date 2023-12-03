package com.project.backend.operations;

import com.project.backend.mappers.CreateBookResponseConverter;
import com.project.backend.mappers.CreateUserResponseConverter;
import com.project.backend.operations.processors.CreateBookOperationProcessor;
import com.project.backend.operations.processors.CreateUserOperationProcessor;
import com.project.backend.operations.processors.LogInOperationProcessor;
import com.project.backend.operations.processors.contracts.OperationProcessor;
import com.project.database.repositories.AuthorRepository;
import com.project.database.repositories.BookRepository;
import com.project.database.repositories.GenreRepository;
import com.project.database.repositories.UserRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OperationFactory {

    public static <T extends OperationProcessor<?,?>> T getOperationProcessor(Class<T> tClass) throws Exception {
        if(tClass == CreateUserOperationProcessor.class )
            return tClass.cast(new CreateUserOperationProcessor(new UserRepository(),new CreateUserResponseConverter()));

        if(tClass == CreateBookOperationProcessor.class )
            return tClass.cast(new CreateBookOperationProcessor(new BookRepository(),new GenreRepository(),new AuthorRepository(),new CreateBookResponseConverter()));

        if(tClass == LogInOperationProcessor.class )
            return tClass.cast(new LogInOperationProcessor(new UserRepository()));

        throw new Exception("BIGGEST ERROR IN THE HISTORY OF ERRORS");
    }
}
