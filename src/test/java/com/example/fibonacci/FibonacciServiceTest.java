package com.example.fibonacci;

import com.example.fibonacci.dto.FibonacciNumberDto;
import com.example.fibonacci.entity.FibonacciNumber;
import com.example.fibonacci.exception.NumberOutOfRangeException;
import com.example.fibonacci.repository.FibonacciRepository;
import com.example.fibonacci.repository.FibonacciStatisticsRepository;
import com.example.fibonacci.service.FibonacciService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.math.BigInteger;

@SpringBootTest
class FibonacciServiceTest {

    @InjectMocks
    private FibonacciService fibonacciService;

    @Mock
    private FibonacciRepository fibonacciRepository;

    @Mock
    private FibonacciStatisticsRepository statisticsRepository;

    static Stream<Object[]> fibonacciLongValues() {
        return Stream.of(
            new Object[]{new FibonacciNumberDto("0", "0"), BigInteger.ZERO},
            new Object[]{new FibonacciNumberDto("1", "1"), BigInteger.ONE},
            new Object[]{new FibonacciNumberDto("10", "55"), new BigInteger("10")}
        );
    }

    @ParameterizedTest
    @MethodSource("fibonacciLongValues")
    void testFibonacciCalculationLongValues(FibonacciNumberDto expected, BigInteger input) {
        assertEquals(expected.getN(), fibonacciService.getFibonacci(input).getN());
        assertEquals(expected.getValue(), fibonacciService.getFibonacci(input).getValue());
    }

    @Test
    void testFibonacciCalculationCachedValue() {
        final BigInteger input = new BigInteger("10");
        final String output = "55";

        FibonacciNumber mockedFibonacciNumber = new FibonacciNumber();
        mockedFibonacciNumber.setPosition(input.longValue());
        mockedFibonacciNumber.setFibValue(output);
        when(fibonacciRepository.findByPosition(input.longValue())).thenReturn(Optional.of(mockedFibonacciNumber));
        
        final FibonacciNumberDto result = fibonacciService.getFibonacci(input);
        
        //2???
        verify(fibonacciRepository, times(1)).findByPosition(input.longValue());
        verify(fibonacciRepository, never()).save(any());

        assertNotNull(result);
        assertEquals(mockedFibonacciNumber.getPosition().toString(), result.getN());
        assertEquals(mockedFibonacciNumber.getFibValue(), result.getValue());
    }

    @Test
    void testFibonacciCalculationNegativeValue() {
        NumberOutOfRangeException error = assertThrows(NumberOutOfRangeException.class, 
            () -> fibonacciService.getFibonacci(new BigInteger("-1")), 
            "No debe ser posible calcular la secuencia fibonacci para números menores a 0");

        assertEquals("El número no puede ser menor a 0", error.getMessage());
    }
}
