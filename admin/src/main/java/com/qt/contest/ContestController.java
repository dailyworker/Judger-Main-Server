package com.qt.contest;

import com.qt.contest.dto.ContestInfo;
import com.qt.domain.Contest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/contests")
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @PostMapping
    public ResponseEntity createContest(@RequestBody ContestInfo contestInfo) {
        Long id = contestService.save(contestInfo);
        return ResponseEntity.created(URI.create("/contests/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContestInfo> showContest(@PathVariable Long id) {
        ContestInfo contestInfo = contestService.findById(id);
        return ResponseEntity.ok(contestInfo);
    }
}
