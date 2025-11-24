package com.utec.gradecalculator.cli;


import com.utec.gradecalculator.domain.Evaluation;
import com.utec.gradecalculator.domain.GradeResult;
import com.utec.gradecalculator.domain.Student;
import com.utec.gradecalculator.policy.AllYearsTeachersPolicy;
import com.utec.gradecalculator.policy.DefaultAttendancePolicy;
import com.utec.gradecalculator.policy.AttendancePolicy;
import com.utec.gradecalculator.policy.ExtraPointsPolicy;
import com.utec.gradecalculator.service.GradeCalculator;

import java.util.Scanner;

/**
 * CLI mínimo: ejecución por terminal según el enunciado.
 */
public class ConsoleApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Student id: ");
            String id = sc.nextLine().trim();

            Student student = new Student(id);

            System.out.print("Number of evaluations (0-10): ");
            int n = Integer.parseInt(sc.nextLine().trim());

            for (int i = 0; i < n; i++) {
                System.out.printf("Evaluation %d - score (0..100): ", i+1);
                double score = Double.parseDouble(sc.nextLine().trim());
                System.out.printf("Evaluation %d - weight (0..100): ", i+1);
                double weight = Double.parseDouble(sc.nextLine().trim());
                student.addEvaluation(new Evaluation(score, weight));
            }

            System.out.print("Has reached minimum classes? (y/n): ");
            String att = sc.nextLine().trim().toLowerCase();
            student.setHasReachedMinClasses(att.equals("y") || att.equals("yes"));

            System.out.print("All years teachers agree on extra points? (y/n): ");
            String all = sc.nextLine().trim().toLowerCase();
            boolean allYearsTeachers = all.equals("y") || all.equals("yes");

            // Políticas configurables: puedes exponer por args o config; aquí se usan valores razonables.
            AttendancePolicy attendancePolicy = new DefaultAttendancePolicy(5.0); // ejemplo: 5 puntos si no cumple asistencia
            ExtraPointsPolicy extraPointsPolicy = new AllYearsTeachersPolicy(2.5); // ejemplo: +2.5 si consenso

            GradeCalculator calc = new GradeCalculator();
            GradeResult result = calc.calculateFinalGrade(student, attendancePolicy, extraPointsPolicy, allYearsTeachers);

            System.out.println();
            System.out.printf("Final grade: %.4f%n", result.getFinalGrade());
            System.out.println("Detail: " + result.getDetail());
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            // para examen, mostrar stack puede ayudar a depurar; en producción lo evitarías
            ex.printStackTrace(System.err);
        } finally {
            sc.close();
        }
    }
}
