package models.Validators;

import javax.persistence.EntityManager;

import models.Group;
import models.Person;
import utils.DBUtil;

public class BelongValidator {

    public static String validate(Group g, Person p) {

        String errors;

        EntityManager em = DBUtil.createEntityManager();

        long b_count = em.createNamedQuery("getGroupBelong",Long.class).setParameter("person",(Person)p).setParameter("group",g).getSingleResult();

        if (b_count != 0) {
            errors ="入力したgroupにはすでに所属しています";
        } else {
            errors = "";
        }

        return errors;
    }

}
