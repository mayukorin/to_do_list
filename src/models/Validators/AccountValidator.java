package models.Validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Account;
import models.Group;
import models.Person;
import utils.DBUtil;

public class AccountValidator {



    public static List<String> validate(Account a, Group group,String leader_code,Boolean group_check, Boolean name_check,Boolean code_duplicate_check_flag, Boolean password_check_flag,Boolean leader_check) {

        List<String> errors = new ArrayList<String>();

        if (a != null) {
            String code_error = _validateCode(a.getCode(), code_duplicate_check_flag);
            if(!code_error.equals("")) {
                errors.add(code_error);
            }
        }

        if (a!= null) {
            String name_error = _validateName(a,name_check);
            if(!name_error.equals("")) {
                errors.add(name_error);
            }
        }

        if (a!= null) {
            String password_error = _validatePassword(a.getPassword(), password_check_flag);
            if(!password_error.equals("")) {
                errors.add(password_error);
            }
        }



            String group_error = _validateGroup(group,a,group_check);

            if (!group_error.equals("")) {
                errors.add(group_error);
            }




        String leader_error = _validateLeader(group,leader_code,leader_check);

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
            long accounts_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                    .setParameter("code", code)
                    .getSingleResult();
            em.close();
            if(accounts_count > 0) {
                return "入力されたアカウント番号の情報はすでに存在しています。";
            }
        }

        return "";
    }

    // アカウント名の必須入力チェック
    private static String _validateName(Account a,Boolean name_check) {

        if(name_check && (a.getName() == null || a.getName().equals(""))) {
            return "アカウント名を入力してください。";
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


    private static String _validateGroup(Group g,Account p,Boolean group_check) {
        //グループが存在するか、すでに所属しているか、調べる

        if (group_check == true) {

            EntityManager em = DBUtil.createEntityManager();
            long group_count = em.createNamedQuery("getGroup",Long.class).setParameter("code",g.getCode()).setParameter("pass",g.getPassword()).getSingleResult();


            if (group_count == 0) {
                return "入力したグループは存在しません。";
            } else {
                //そのグループにすでに所属しているか確認する。
                Group group = em.createNamedQuery("Group",Group.class).setParameter("code",g.getCode()).setParameter("pass",g.getPassword()).getSingleResult();

                long b_count = em.createNamedQuery("getGroupBelong",Long.class).setParameter("person",(Person)p).setParameter("group",group).getSingleResult();

                if (b_count != 0) {
                    return "そのグループにはすでに所属しています。";
                }
            }
        }


        return "";

    }

    private static String _validateLeader(Group g,String leader_code,Boolean leader_check) {

        if (leader_check == true) {
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
                        return "Groupをリーダーにすることはできません。";
                    } else {
                        //そのleaderがGroupに属しているかどうか
                        long belong_count = em.createNamedQuery("getGroupBelong",Long.class).setParameter("person",(Person)a).setParameter("group",g).getSingleResult();
                        if (belong_count == 0) {
                            return "そのアカウント番号の人物は、同じgroupに属していません。";
                        }
                    }
                }
            }
        }
        return "";
    }




}
