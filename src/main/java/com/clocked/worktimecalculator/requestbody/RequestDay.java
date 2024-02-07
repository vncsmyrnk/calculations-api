package com.clocked.worktimecalculator.requestbody;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RequestDay {
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private List<LocalDateTime> registeredRecords;

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
