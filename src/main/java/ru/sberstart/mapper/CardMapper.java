package ru.sberstart.mapper;

import org.mapstruct.Mapper;
import ru.sberstart.dto.CardDto;
import ru.sberstart.entity.Card;

import java.util.List;

@Mapper
public interface CardMapper {
    CardDto toCardDto(Card card);

    List<CardDto> toListCardDto(List<Card> cards);
}
