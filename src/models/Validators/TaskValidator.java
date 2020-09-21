package models.Validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import models.Account;
import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

public class TaskValidator {


    public static List<String> validate(Task t,String date,Group group,String leader_code,Boolean leader_check_flag) {
        List<String> errors = new ArrayList<String>();


        String title_error = _validateTitle(t.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }




        String date_error = _validateDate(t,date);

        if (!date_error.equals("")) {
            errors.add(date_error);
        }


        String leader_error = _validateLeader(group,leader_code,leader_check_flag);

        if (!leader_error.equals("")) {
            errors.add(leader_error);
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

            System.out.println(deadline);

            t.setDeadline(deadline);//締め切りを保存する。
        } catch (ParseException e) {
            return "カレンダーをクリックしてください。";
        }

        return "";
    }

    private static String _validateLeader(Group group,String leader_code,Boolean leader_check_flag) {
        if (leader_check_flag == true) {
            if (leader_code == null || leader_code.equals("")) {
                return "アカウント番号を入力してください";

            } else {
                EntityManager em = DBUtil.createEntityManager();
                long leader_count = em.createNamedQuery("checkRegisteredCode",Long.class).setParameter("code",leader_code).getSingleResult();

                if (leader_count != 1) {
                    //入力したコードのpersonが存在しない時
                    return "そのアカウント番号の情報は存在しません。";
                } else {
                    //新しいleaderの候補
                    Account a = em.createNamedQuery("getAccount",Account.class).setParameter("code", leader_code).getSingleResult();

                    if (a instanceof Group) {
                        //もし新しいleaderがgroupだったら
                        return "同じgroup内にそのアカウントの人物は存在しません";
                    } else {
                        //そのleaderがGroupに属しているかどうか
                        long belong_count = em.createNamedQuery("getGroupBelong",Long.class).setParameter("person",(Person)a).setParameter("group",group).getSingleResult();
                        if (belong_count == 0) {
                            return "同じgroup内にそのアカウントの人物は存在しません";
                        }
                    }
                }
            }
        }


        return "";
    }



}
