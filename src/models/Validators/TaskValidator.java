package models.Validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Task;

public class TaskValidator {


    public static List<String> validate(Task t,String date) {
        List<String> errors = new ArrayList<String>();

        String title_error = _validateTitle(t.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        String date_error = _validateDate(t,date);

        if (!date_error.equals("")) {
            errors.add(date_error);
        }

        return errors;
    }

    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "Task名を入力してください。";
            }

        return "";
    }

    private static String _validateDate(Task t,String date) {

        try {
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            Date deadline = sdFormat.parse(date);

            t.setDeadline(deadline);//締め切りを保存する。
        } catch (ParseException e) {
            return "カレンダーをクリックしてください。";
        }

        return "";
    }



}
