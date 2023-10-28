package com.practice.jobsearchproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.response.CurrencyResponseDto;
import com.practice.jobsearchproject.model.entity.Currency;
import com.practice.jobsearchproject.model.mapper.CurrencyMapper;
import com.practice.jobsearchproject.repository.CurrencyRepository;
import com.practice.jobsearchproject.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public void saveAllCurrenciesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("currencies.json");
            String[] currencies = objectMapper.readValue(file, String[].class);
            for (String name : currencies) {
                Currency currency = new Currency();
                currency.setName(name);
                currencyRepository.save(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CurrencyResponseDto> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::convertToCurrencyResponseDto)
                .collect(Collectors.toList());
    }
}