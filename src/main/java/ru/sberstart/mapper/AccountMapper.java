package ru.sberstart.mapper;

import org.mapstruct.Mapper;
import ru.sberstart.dto.CreateCardDto;
import ru.sberstart.entity.Account;

@Mapper
public interface AccountMapper {
    Account toAccount(CreateCardDto createCardDto);
}
