package models.Validators;

import java.util.ArrayList;
import java.util.List;

import models.Task;

public class TaskValidator {


    public static List<String> validate(Task t) {
        List<String> errors = new ArrayList<String>();

        String title_error = _validateTitle(t.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        return errors;
    }

    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "タイトルを入力してください。";
            }

        return "";
    }



}
