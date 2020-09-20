package models.Validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Account;
import models.Group;
import models.Person;
import utils.DBUtil;

public class GroupValidator {
    public static List<String> validate(Group g, String leader_code,Boolean code_duplicate_check_flag, Boolean password_check_flag,Boolean leader_check_flag) {
        List<String> errors = new ArrayList<String>();

        String code_error = _validateCode(g.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _validateName(g.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(g.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        String leader_error = _validateLeader(g,leader_code,leader_check_flag);
        if (!leader_error.equals("")) {
            errors.add(leader_error);
        }

        return errors;
    }

    // アカウント番号
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        // 必須入力チェック
        if(code == null || code.equals("")) {
            return "アカウント番号を入力してください。";
        }

     // すでに登録されているアカウント番号との重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                           .setParameter("code", code)
                                             .getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "入力されたアカウント番号の情報はすでに存在しています。";
            }
        }

        return "";
    }

    // アカウント名の必須入力チェック
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "名前を入力してください。";
        }

        return "";
    }

 // パスワードの必須入力チェック
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }

    //groupのleaderのチェック
    private static String _validateLeader(Group g,String leader_code,Boolean leader_check_flag) {
        if (leader_check_flag == true) {
            if (leader_code.equals("") || leader_code == null) {
                //leaderのcodeが入力されていない場合
                return "リーダーのアカウント番号を入力してください";
            } else {
                //codeは入力されていた場合
                EntityManager em = DBUtil.createEntityManager();
                long leader_count = em.createNamedQuery("checkRegisteredCode",Long.class).setParameter("code",leader_code).getSingleResult();

                if (leader_count == 0) {
                    //そのcodeのアカウントが存在しない場合
                    return "そのアカウント情報は存在しません";
                } else {
                    //そのcodeのアカウントは存在した場合
                    Account a = em.createNamedQuery("getAccount",Account.class).setParameter("code", leader_code).getSingleResult();

                    if (a instanceof Group) {
                        return "同じgroup内にそのアカウントの人物は存在しません";
                    } else {
                        //そのcodeのアカウントが存在し、Personクラスだった場合
                        long belong_count = em.createNamedQuery("getGroupBelong",Long.class).setParameter("person",(Person)a).setParameter("group",g).getSingleResult();

                        if (belong_count == 0) {
                            //そのcodeの人物がそのgroupに属していない場合
                            return "同じgroup内にそのアカウントの人物は存在しません";
                        }
                    }
                }
            }
        }
        return "";
    }




}
