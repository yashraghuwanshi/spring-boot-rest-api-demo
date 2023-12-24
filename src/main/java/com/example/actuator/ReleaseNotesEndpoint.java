package com.example.actuator;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Endpoint(id = "releasenotes")
public class ReleaseNotesEndpoint {

    private final Map<String, List<String>> releaseNotes = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        releaseNotes.put("v1.0.0", Arrays.asList("Initial release.", "Added feature X and fixed bugs."));
        releaseNotes.put("v1.1.0", Arrays.asList("Added feature Y and improved performance.", "Added feature Z and fixed bugs."));
        releaseNotes.put("v2.0.0", Arrays.asList("Major update with breaking changes.", "See documentation for migration."));
    }

    @ReadOperation
    public Map<String, List<String>> getReleaseNotes() {
        return releaseNotes;
    }

    @ReadOperation
    public List<String> getReleaseNotesByVersion(@Selector String version){
        return releaseNotes.get(version);
    }

    @WriteOperation
    public void addReleaseNotes(@Selector String version, String releaseNote){
        releaseNotes.put(version, Arrays.stream(releaseNote.split(", ")).collect(Collectors.toList()));
    }

    @DeleteOperation
    public void deleteReleaseNote(@Selector String version){
       releaseNotes.remove(version);
    }
}
