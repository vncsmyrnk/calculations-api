package com.clocked.worktimecalculator.controller;

import com.clocked.worktimecalculator.builder.DayBuilder;
import com.clocked.worktimecalculator.entities.Calculation;
import com.clocked.worktimecalculator.entities.Day;
import com.clocked.worktimecalculator.requestbody.RequestDay;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculations")
public class CalculationsController {
  @PostMapping(value = "day")
  public ResponseEntity<List<Calculation>> calculateDay(@Valid @RequestBody RequestDay requestDay) {
    Day day =
        DayBuilder.of(
            requestDay.getDate(), requestDay.getRegisteredRecords(), requestDay.getShiftRecords());
    List<Calculation> calculations = day.calculate();
    return ResponseEntity.ok(calculations);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return errors;
  }
}
