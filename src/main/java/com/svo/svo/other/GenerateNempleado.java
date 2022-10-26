package com.svo.svo.other;

public class GenerateNempleado {
    public String agregarCeros(String string) {
        String ceros = "";

        int cantidad = 4 - string.length();

        if (cantidad >= 1) {
            for (int i = 0; i < cantidad; i++) {
                ceros += "0";
            }

            return (ceros + string);
        } else
            return string;
    }

}
