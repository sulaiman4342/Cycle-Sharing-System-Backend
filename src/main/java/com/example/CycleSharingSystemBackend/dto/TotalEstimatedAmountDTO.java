package com.example.CycleSharingSystemBackend.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TotalEstimatedAmountDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" )
    private LocalDate paymentDate;

    private Double totalEstimatedAmount;
    }
