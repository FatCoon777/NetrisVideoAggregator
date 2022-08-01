package ru.agregator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.agregator.components.AggregateData;
import ru.agregator.exception.EmptyAggregateException;

@Service
public class AggregateDataService {
    private byte[] casheForRead;
    private AggregateData[] casheForWrite;

    private final ObjectMapper objectMapper;

    public AggregateDataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void add(int index, AggregateData aggregateData) {
        casheForWrite[index] = aggregateData;
    }

    public byte[] read() throws EmptyAggregateException {
        if (casheForRead == null) {
            throw new EmptyAggregateException();
        }
        return casheForRead;
    }

    public void startBuildingCashe(int size) {
        casheForWrite = new AggregateData[size];
    }

    public void stopBuildingCashe() throws JsonProcessingException {
        casheForRead = objectMapper.writeValueAsBytes(casheForWrite);
    }
}
