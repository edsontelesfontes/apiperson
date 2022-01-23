package com.dio.apersonapi.mapper;

import com.dio.apersonapi.dto.PersonDTO;
import com.dio.apersonapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
@Mapping(target = "birthDate", source = "birthDate",dateFormat = "dd-mm-yyyy")
Person toModel (PersonDTO personDTO);

PersonDTO toDTO(Person person);
}
