package com.library.backend.operations;

import com.library.backend.mappers.CreateBookResponseConverter;
import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.CreateBookOperationProcessor;
import com.library.backend.operations.processors.CreateUserOperationProcessor;
import com.library.backend.operations.processors.LogInOperationProcessor;
import com.library.backend.operations.processors.contracts.OperationProcessor;
import com.library.database.repositories.AuthorRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.GenreRepository;
import com.library.database.repositories.UserRepository;
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
