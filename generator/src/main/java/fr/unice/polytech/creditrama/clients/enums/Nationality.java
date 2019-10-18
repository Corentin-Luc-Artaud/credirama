package fr.unice.polytech.creditrama.clients.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum Nationality {
    AU("Australie", new HashMap<String, String>() {{
        put("Perth", "WA 6152");
        put("Melbourne", "VIC 3142");
        put("Sidney", "NSW 2000");
    }}),
    BE("Belgique", new HashMap<String, String>() {{
        put("Bruxelles", "1070");
        put("Bruges", "8000");
        put("Mons", "7000");
    }}),
    BR("Brésil", new HashMap<String, String>() {{
        put("São Paulo", "SP 01450");
        put("Brasília", "DF 70297");
        put("Rio de Janeiro", "RJ 20020");
    }}),
    CA("Canada", new HashMap<String, String>() {{
        put("Montréal", "QC H2X 3Y3");
        put("Québec", "QC G1R 3Z2");
        put("Vancouver", "BC V5V 2C2");
    }}),
    DE("Allemagne", new HashMap<String, String>() {{
        put("Munich", "81541");
        put("Berlin", "12053");
        put("Stuttgart", "70173");
    }}),
    FR("France", new HashMap<String, String>() {{
        put("Nice", "06200");
        put("Paris", "75008");
        put("Bordeaux", "33000");
    }}),
    GB("Grande Bretagne", new HashMap<String, String>() {{
        put("Londres", "SW1V 4BT");
        put("Liverpool", "L2 0RR");
        put("Bristol", "BS1 4SS");
    }}),
    IT("Italie", new HashMap<String, String>() {{
        put("Come", "22100");
        put("Milan", "20121");
        put("Rome", "00153");
    }});

    private String countryName;
    private Map<String, String> cities;
}
