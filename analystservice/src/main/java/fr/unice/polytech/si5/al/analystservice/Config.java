package fr.unice.polytech.si5.al.analystservice;

public class Config {
    /**
     * Constructeur privé
     */
    private Config() {
    }

    /**
     * Instance unique pré-initialisée
     */
    private static Config INSTANCE = new Config();

    /**
     * Point d'accès pour l'instance unique du singleton
     */
    public static Config getInstance() {
        return INSTANCE;
    }

}
