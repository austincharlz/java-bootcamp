package com.northstar.crm.event;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ProcessedEventStore {

    private final Set<String> seen = ConcurrentHashMap.newKeySet();

    /** @return true if this is the first time seeing eventId */
    public boolean markIfNew(String eventId) {
        return seen.add(Objects.requireNonNull(eventId, "eventId is required"));
    }
}