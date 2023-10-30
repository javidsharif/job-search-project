package com.practice.jobsearchproject.service;

import com.practice.jobsearchproject.model.dto.response.CurrencyResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurrencyService {
    List<CurrencyResponseDto> getAllCurrencies();
}
