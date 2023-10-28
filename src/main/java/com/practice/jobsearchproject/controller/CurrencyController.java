package com.practice.jobsearchproject.controller;

import com.practice.jobsearchproject.model.dto.response.CurrencyResponseDto;
import com.practice.jobsearchproject.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CurrencyResponseDto> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }
}
