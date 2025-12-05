package repository;

import hrmanager.model.Employe;

import java.util.HashMap;

public class EmployeRepository {

    private HashMap<Integer, Employe> employes = new HashMap<>();
    private int currentId = 1;

    public Employe ajouter(Employe e) {
        e.setId(currentId);
        employes.put(currentId, e);
        currentId++;
        return e;
    }

    public boolean supprimer(int id) {
        return employes.remove(id) != null;
    }

    public Employe rechercher(int id) {
        return employes.get(id);
    }

    public void afficher() {
        if (employes.isEmpty()) {
            System.out.println("Aucun employé.");
            return;
        }
        for (Employe e : employes.values()) {
            System.out.println(e);
        }
    }
}
