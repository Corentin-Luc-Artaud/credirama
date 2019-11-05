package fr.unice.polytech.si5.al.analystservice;

public class Database {

    private Database() {
    }

    /**
     * Instance unique pré-initialisée
     */
    private static Database INSTANCE = new Database();

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static Database getInstance() {
        return INSTANCE;
    }
}
