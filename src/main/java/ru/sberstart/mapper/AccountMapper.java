package ru.sberstart.mapper;

import org.mapstruct.Mapper;
import ru.sberstart.dto.AccountDto;
import ru.sberstart.dto.BalanceRefillDto;
import ru.sberstart.entity.Account;

@Mapper
public interface AccountMapper {
    BalanceRefillDto toBalanceRefillDto(Account account);

    AccountDto toAccountDto(Account account);
}
