package com.library.backend.operations;

import com.library.backend.mappers.CreateBookResponseConverter;
import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.CreateBookOperationProcessor;
import com.library.backend.operations.processors.CreateReaderOperationProcessor;
import com.library.backend.operations.processors.LogInOperationProcessor;
import com.library.backend.operations.processors.contracts.CreateBookOperation;
import com.library.backend.operations.processors.contracts.OperationProcessor;
import com.library.backend.operations.requests.Request;
import com.library.backend.operations.responses.Response;
import com.library.database.repositories.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * Factory class for obtaining instances of {@link OperationProcessor}.
 * This factory uses an internal enum {@link OperationProcessorType} to map processor types to their respective classes.
 */
@NoArgsConstructor
public class OperationFactory {

    /**
     * Enum that maps operation processor types to their associated classes.
     */
    @Getter
    @RequiredArgsConstructor
    private enum OperationProcessorType {
        CREATE_READER(CreateReaderOperationProcessor.class),
        CREATE_BOOK(CreateBookOperation.class),
        LOG_IN(LogInOperationProcessor.class);

        private final Class<? extends OperationProcessor<? extends Response, ? extends Request>> processorClass;
    }


    /**
     * Retrieves an operation processor instance based on the provided processor class.
     *
     * @param processorClass The class of the processor to retrieve.
     * @param RESPONSE       The type of response handled by the processor.
     * @param REQUEST        The type of request handled by the processor.
     * @param PROCESSOR      The processor type extending {@link OperationProcessor}.
     * @return An instance of   the specified operation processor.
     * @throws Exception If an error occurs during processor retrieval.
     */
    public static <RESPONSE extends Response, REQUEST extends Request, PROCESSOR extends OperationProcessor<RESPONSE, REQUEST>> PROCESSOR getOperationProcessor(Class<PROCESSOR> processorClass) throws Exception {

        PROCESSOR processor;

        switch (getOperationProcessorType(processorClass)) {
            case LOG_IN -> processor = processorClass.cast(new LogInOperationProcessor(new ReaderRepository(),new OperatorRepository(),new AdminRepository()));

            case CREATE_BOOK ->
                    processor = processorClass.cast(new CreateBookOperationProcessor(new BookRepository(), new GenreRepository(), new AuthorRepository(), new CreateBookResponseConverter()));

            case CREATE_READER ->
                    processor = processorClass.cast(new CreateReaderOperationProcessor(new ReaderRepository(), new CreateUserResponseConverter()));

            default -> throw new RuntimeException("There is no such enum");
        }

        return processor;
    }

    /**
     * Determines the operation processor type based on the provided processor class.
     *
     * @param RESPONSE       The type of response handled by the operation processor.
     * @param REQUEST        The type of request handled by the operation processor.
     * @param PROCESSOR      The type of operation processor, extending {@link OperationProcessor}.
     * @param processorClass The class of the operation processor to determine the type.
     * @return The corresponding {@link OperationProcessorType} of the provided class.
     * @throws RuntimeException If the enum for the provided processor class is not found.
     */
    private static <RESPONSE extends Response, REQUEST extends Request, PROCESSOR extends OperationProcessor<RESPONSE, REQUEST>> OperationProcessorType getOperationProcessorType(Class<PROCESSOR> processorClass) {

        OperationProcessorType processorType = null;

        for (OperationProcessorType type : OperationProcessorType.values()) {
            if (type.processorClass == processorClass) {
                processorType = type;
                break;
            }
        }

        return processorType;
    }




    /*public static <RESPONSE extends Response, REQUEST extends Request, PROCESSOR extends OperationProcessor<RESPONSE,REQUEST>> PROCESSOR getOperationProcessor(Class<PROCESSOR> tClass) throws Exception {
        if(tClass == CreateReaderOperationProcessor.class )
            return tClass.cast(new CreateReaderOperationProcessor(new ReaderRepository(),new CreateUserResponseConverter()));

        if(tClass == CreateBookOperationProcessor.class )
            return tClass.cast(new CreateBookOperationProcessor(new BookRepository(),new GenreRepository(),new AuthorRepository(),new CreateBookResponseConverter()));

        if(tClass == LogInOperationProcessor.class )
            return tClass.cast(new LogInOperationProcessor(new ReaderRepository()));

        throw new Exception("BIGGEST ERROR IN THE HISTORY OF ERRORS");
    }*/
}
