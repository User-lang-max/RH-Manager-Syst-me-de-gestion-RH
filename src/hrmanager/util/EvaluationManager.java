package hrmanager.util;

import hrmanager.model.Evaluation;
import hrmanager.exceptions.EvaluationInvalideException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EvaluationManager {

    private static List<Evaluation> evaluations = new ArrayList<>();
    private static int nextId = 1;

   

    public static Evaluation ajouterEvaluation(int idEmploye, int note, String commentaire)
            throws EvaluationInvalideException {

        Evaluation e = new Evaluation(nextId++, idEmploye, LocalDate.now(), note, commentaire);
        e.valider();  
        evaluations.add(e);

        return e;
    }

   
    public static List<Evaluation> getEvaluationsEmploye(int idEmploye) {
        List<Evaluation> result = new ArrayList<>();

        for (Evaluation eval : evaluations) {
            if (eval.getIdEmploye() == idEmploye) {
                result.add(eval);
            }
        }
        return result;
    }
}
