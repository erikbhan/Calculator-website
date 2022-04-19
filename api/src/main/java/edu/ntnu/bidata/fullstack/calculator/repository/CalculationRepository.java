package edu.ntnu.bidata.fullstack.calculator.repository;

import edu.ntnu.bidata.fullstack.calculator.model.Calculation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Long> {

}