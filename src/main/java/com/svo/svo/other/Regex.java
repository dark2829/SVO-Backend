package com.svo.svo.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public Regex() {
    }

    public boolean validarEmail(String input) {
        boolean respuesta;
        String regex = "[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]{2,}+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(input);
        respuesta = mat.find();
        return respuesta;
    }

    public boolean validarNoEmpleado(String input) {
        boolean respuesta;
        String regex = "^\\d{4}$";
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(input);
        respuesta = mat.find();
        return respuesta;
    }
}
