package com.antsiferov.testtask.services;

import com.antsiferov.testtask.entities.Reader;
import com.antsiferov.testtask.repositories.ReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class ReaderService {

    private ReaderRepository readerRepository;

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public void delete(Reader reader) {
        readerRepository.delete(reader);
    }

    public Reader findById(Long id) {
        return readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Reader update(Long id, Reader updatedReader) {
        Reader reader = readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        reader.setName(updatedReader.getName());
        reader.setEmail(updatedReader.getEmail());
        reader.setAddress(updatedReader.getAddress());
        reader.setPhone(updatedReader.getPhone());
        return readerRepository.save(reader);
    }

}
