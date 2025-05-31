package com.aib.walletmanager.business.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validator {

    private String message;
    private Pattern pattern;

    private boolean generalValidator(Pattern compare, String value, int max, Boolean isnull) {
        if (isnull && value == null) return true;
        if (value.isEmpty()) return isnull;
        if (value.length() >= max) {
            message = "The text length is not allowed";
            return false;
        }
        final Matcher matcher = compare.matcher(value);
        final boolean finds = matcher.find();
        message = finds ? "Valid" : "The text doesn't match";
        return finds;
    }

    public boolean validateAlphabetic(String value, int max) {
        pattern = Pattern.compile("^[a-zA-ZÀ-ÖØ-öø-ÿ]+( [a-zA-ZÀ-ÖØ-öø-ÿ]+)*$");
        return generalValidator(pattern, value, max, false);
    }

    public boolean validateAlphanumeric(String value, int max) {
        pattern = Pattern.compile("^(?:[\\p{L}\\p{N}]+[\\p{P}\\p{M}]*\\s*)+$");
        return generalValidator(pattern, value, max, false);
    }

    public boolean validateAlphanumericAndCharacters(String value, int max) {
        pattern = Pattern.compile("^[A-Za-z0-9 !@#$%^&()_+\\-={},.;:'\"/?`~|\\[\\]{}Ññ]+$");//^[A-Za-z0-9 !@#$%^&()_+-=,.;:'"/?`~|\[\]{}]+$
        return generalValidator(pattern, value, max, false);
    }

    public boolean validateAlphanumericAndCharactersNull(String value, int max) {
        pattern = Pattern.compile("^[A-Za-z0-9 !@#$%^&()_+\\-={},.;:'\"/?`~|\\[\\]{}Ññ]+$");
        return generalValidator(pattern, value, max, true);
    }

    public boolean validateStringNull(String value, int max) {
        pattern = Pattern.compile("^[a-zA-ZÀ-ÖØ-öø-ÿ]+( [a-zA-ZÀ-ÖØ-öø-ÿ]+)*$");
        return generalValidator(pattern, value, max, true);
    }

    public boolean validateAlphanumericNull(String value, int max) {
        pattern = Pattern.compile("^(?:[\\p{L}\\p{N}]+[\\p{P}\\p{M}]*\\s*)+$");
        return generalValidator(pattern, value, max, true);
    }

    public boolean validateEmails(String value, int max) {
        pattern = Pattern.compile("^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$");
        return generalValidator(pattern, value, max, true);
    }


    public boolean validatePass(String value, int max) {
        pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$!%*?&/#])[A-Za-z\\d$!%*?&/#]{8,15}$");
        return generalValidator(pattern, value, max, false);
    }

    public boolean validateMoney(String value, int max) {
        pattern = Pattern.compile("^(?!0+(\\.0{1,2})?$)(1000000(\\.00?)?|\\d{1,6}(\\.\\d{1,2})?)$");
        return generalValidator(pattern, value, max, false);
    }

}
