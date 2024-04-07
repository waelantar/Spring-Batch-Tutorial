package com.courses.springbatchtutorial;

import com.courses.springbatchtutorial.Config.BankTransactionWriter;
import com.courses.springbatchtutorial.Repository.BankTransactionRepository;
import com.courses.springbatchtutorial.entity.BankTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.Chunk;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class BankTransactionWriterTest {

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @InjectMocks
    private BankTransactionWriter writer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWrite() {
        // Given
        BankTransaction transaction = new BankTransaction();
        Chunk<BankTransaction> chunk = new Chunk<>(Collections.singletonList(transaction));

        // When
        writer.write(chunk);

        // Then
        verify(bankTransactionRepository, times(1)).saveAll(chunk);
    }
}

