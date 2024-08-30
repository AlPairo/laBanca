package com.example.fibonacci.service;

import java.math.BigInteger;
import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import com.example.fibonacci.dto.FibonacciNumberDto;
import com.example.fibonacci.entity.FibonacciNumber;
import com.example.fibonacci.exception.NumberOutOfRangeException;
import com.example.fibonacci.repository.FibonacciRepository;

@Service
public class FibonacciService {


    private final FibonacciRepository fibonacciRepository;

    public FibonacciService(FibonacciRepository fibonacciRepository){
        this.fibonacciRepository = fibonacciRepository;
    }

    /**
     * Devuelve el valor de la serie fibonacci para la posición dada
     * @param n posición
     * @return valor de la serie fibonacci para la n-esima posición
     */
    public FibonacciNumberDto getFibonacci(BigInteger n) {
        FibonacciNumberDto ret = new FibonacciNumberDto();
        Optional<FibonacciNumber> cachedResult = fibonacciRepository.findByPosition(n.longValue());
        if (cachedResult.isPresent()) {
            ret.setN(cachedResult.get().getPosition().toString());
            ret.setValue(cachedResult.get().getFibValue());
        }else{
            BigInteger result = calculateFibonacci(n);
            ret.setN(n.toString());
            ret.setValue(result.toString());
        }
        return ret;
    }

    /**
     * Calcula el valor de la serie fibonacci para la posición dada
     * @param n posición
     * @return valor de la serie fibonacci para la n-esima posición
     */
    private BigInteger calculateFibonacci(BigInteger n) {
        List<FibonacciNumber> fibonacciNumbers = new ArrayList<>();
        if (n.compareTo(BigInteger.ZERO) < 0){
            throw new NumberOutOfRangeException("El número no puede ser menor a 0");
        }
        if (n.compareTo(BigInteger.ZERO) == 0) return BigInteger.ZERO;
        if (n.compareTo(BigInteger.ONE) == 0 || n.compareTo(BigInteger.TWO) == 0) return BigInteger.ONE;
        
        List<FibonacciNumber> cachedResult = fibonacciRepository.getLastTwo();
        BigInteger fibN = BigInteger.ONE;
        BigInteger fib1;
        BigInteger fib2;
        BigInteger inicioFibonacci;
        
        if (!cachedResult.isEmpty() && cachedResult.size() == 2) {
            fib2 = new BigInteger(cachedResult.get(0).getFibValue());
            fib1 = new BigInteger(cachedResult.get(1).getFibValue());
            inicioFibonacci = BigInteger.valueOf(cachedResult.get(0).getPosition()+1);
        }else{
            fib1 = BigInteger.ONE;
            fib2 = BigInteger.ONE;
            inicioFibonacci = BigInteger.valueOf(3l);
        }

        for (BigInteger i = inicioFibonacci; i.compareTo(n) < 1; i = i.add(BigInteger.ONE)) {
            fibN = fib1.add(fib2);
            fib1 = fib2;
            fib2 = fibN;

            Optional<FibonacciNumber> existingFib = fibonacciRepository.findByPosition(i.longValue());
            if (!existingFib.isPresent()) {
                FibonacciNumber fib = new FibonacciNumber();
                fib.setPosition(i.longValue());
                fib.setFibValue(fibN.toString());
                fibonacciNumbers.add(fib);
            }
            
        }
        if(!fibonacciNumbers.isEmpty()){
            fibonacciRepository.saveAllAndFlush(fibonacciNumbers);
        }
        return fibN;
    }
    
}