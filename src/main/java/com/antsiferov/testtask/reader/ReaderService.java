package com.antsiferov.testtask.reader;

import com.antsiferov.testtask.event.EventRepository;
import com.antsiferov.testtask.event.EventType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ReaderService {

    private ReaderRepository readerRepository;

    private EventRepository eventRepository;

    public Reader save(Reader reader) {
        String email = reader.getEmail();
        Optional<Reader> existingReader = readerRepository.findByEmail(email);
        if (existingReader.isPresent()) {
            throw new IllegalArgumentException("Читатель с email " + email + " уже существует");
        }
        return readerRepository.save(reader);
    }

    public void delete(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Читатель с id " + id + " не найден"));
        long takeCount = eventRepository.countByReaderAndEventType(reader, EventType.TAKE);
        long returnCount = eventRepository.countByReaderAndEventType(reader, EventType.RETURN);

        if (takeCount != returnCount) {
            throw new IllegalStateException("Нельзя удалить читателя с id " + id + ", так как он не вернул все книги в библиотеку");
        } else {
            eventRepository.deleteByReaderId(id);
        }
        readerRepository.deleteById(id);
    }

    public Reader findById(Long id) {
        return readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Читатель с id " + id + " не найден"));
    }

    public Reader update(Long id, Reader updatedReader) {
        Reader reader = readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Читатель с id " + id + " не найден"));
        reader.setName(updatedReader.getName());
        reader.setEmail(updatedReader.getEmail());
        reader.setAddress(updatedReader.getAddress());
        reader.setPhone(updatedReader.getPhone());
        return readerRepository.save(reader);
    }

    public void deleteAll() {
        readerRepository.deleteAll();
    }

}
