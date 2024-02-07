package com.clocked.worktimecalculator.controller;

import com.clocked.worktimecalculator.builder.DayBuilder;
import com.clocked.worktimecalculator.entities.Calculation;
import com.clocked.worktimecalculator.entities.Day;
import com.clocked.worktimecalculator.requestbody.RequestDay;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculations")
public class CalculationsController {
  @PostMapping(value = "day")
  public ResponseEntity<List<Calculation>> calculateDay(@RequestBody RequestDay requestDay) {
    Day day =
        DayBuilder.of(
            requestDay.getDate(), requestDay.getRegisteredRecords(), requestDay.getShiftRecords());
    List<Calculation> calculations = day.calculate();
    return ResponseEntity.ok(calculations);
  }
}
