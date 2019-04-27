package br.com.augustolucianetti.microservicostbfinal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Statistic {

    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;
}
