package com.practice.jobsearchproject.model.mapper;

import com.practice.jobsearchproject.model.dto.response.CurrencyResponseDto;
import com.practice.jobsearchproject.model.entity.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyResponseDto convertToCurrencyResponseDto(Currency currency);

    Currency convertToCurrency(CurrencyResponseDto currencyResponseDto);
}
