package models.Validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Account;
import models.Belong;
import models.Group;
import models.Person;
import utils.DBUtil;

public class AccountValidator {



    public static List<String> validate(Account a, String group_code, Boolean code_duplicate_check_flag, Boolean password_check_flag) {

        List<String> errors = new ArrayList<String>();

        String code_error = _validateCode(a.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = _validateName(a.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = _validatePassword(a.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        if (a instanceof Person) {



            //もしPersonクラスだったら、管理者フラッグが入力されているか、チェックする。
            String flag_error = _validateFlag(((Person)a).getAdmin_flag());

            if (!flag_error.equals("")) {
                errors.add(flag_error);
            }

            String group_error = _validateGroup(group_code,(Person)a);

            if (!group_error.equals("")) {
                errors.add(group_error);
            }


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
        private static String _validateName(String name) {
            if(name == null || name.equals("")) {
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

        // 管理者必須入力チェック
        private static String _validateFlag(Integer flag) {
            if (flag == null) {
                return "管理者かどうか選択してください。";
            }

            return "";
        }

        private static String _validateGroup(String code,Person p) {
            if (code != null && !code.equals("")) {
                //新しく所属するグループを入力した場合、そのグループが存在するかを調べる
                EntityManager em = DBUtil.createEntityManager();
                Group group = em.createNamedQuery("getGroup",Group.class).setParameter("code",code).getSingleResult();


                if (group == null) {
                    return "入力したグループは存在しません。";
                } else {
                    //そのグループがすでに所属しているか確認する。
                    Belong b = em.createNamedQuery("getGroupBelong",Belong.class).setParameter("person",p).setParameter("group",group).getSingleResult();
                    if (b != null) {
                        return "そのグループにはすでに所属しています。";
                    }
                }
            }

            return "";

        }




}
