package com.dio.apersonapi.service;

import com.dio.apersonapi.dto.MessageResponseDTO;
import com.dio.apersonapi.dto.PersonDTO;
import com.dio.apersonapi.entity.Person;
import com.dio.apersonapi.exception.PersonNotFoundException;
import com.dio.apersonapi.mapper.PersonMapper;
import com.dio.apersonapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }



    @PostMapping
    public MessageResponseDTO createPerson(@RequestBody PersonDTO personDTO) {
       Person personToSave = PersonMapper.INSTANCE.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return createdMessageResponse(savedPerson.getId(), "Saved Person with sucess");
    }

    public List<PersonDTO> listAll(){
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public  PersonDTO findById(Long id) throws PersonNotFoundException {
     Person person = verifyIfExists(id);
        // Optional<Person> optionalPerson = personRepository.findById(id);

//        if(optionalPerson.isEmpty()){
//            throw new PersonNotFoundException(id);
//        }

        return personMapper.toDTO(person);
    }

    public void deleteById(Long id ) throws PersonNotFoundException {
        verifyIfExists(id);

        personRepository.deleteById(id);
    }


    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfExists(id);
        Person personToSave = PersonMapper.INSTANCE.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToSave);
        return createdMessageResponse(updatedPerson.getId(), "Updated person with sucesss");
    }
    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createdMessageResponse(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }
}
