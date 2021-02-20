package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // type query
            TypedQuery<Member> query = em.createQuery("select m from Member m ", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m ", String.class);

            List<Member> resultList = query.getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1.getUsername() = " + member1.getUsername());
            }


            // parameter
//            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
            // System.out.println("singleResult = " + singleResult.getUsername());

            // paging
            List<Member> resultList1 = em.createQuery("select m from Member m where m.username = :username order by m.age", Member.class)
                    .setParameter("username", "member1")
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for (Member member1 : resultList1) {
                System.out.println("member1 = " + member1);
            }


            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
            emf.close();
        }
    }
}
