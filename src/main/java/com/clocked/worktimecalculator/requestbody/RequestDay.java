package com.clocked.worktimecalculator.requestbody;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RequestDay {
  @NotNull(message = "Date must be informed")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @NotNull(message = "Registered records must be informed")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private List<LocalDateTime> registeredRecords;

  @NotNull(message = "Shift records must be informed")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private List<LocalDateTime> shiftRecords;

  public LocalDate getDate() {
    return date;
  }

  public List<LocalDateTime> getRegisteredRecords() {
    return registeredRecords;
  }

  public List<LocalDateTime> getShiftRecords() {
    return shiftRecords;
  }
}
