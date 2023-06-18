package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        //모든 데이터 변경은 트랜잭션 안에서 이루어져야한다.
        tx.begin();
        try{

            for(int i = 0; i<100;i++){

                Member member = new Member();
                member.setUsername("Spring" + i);
                member.setAge(i);
                em.persist(member);
            }

            List<Member> list = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for(Member m : list){
                System.out.println(m.getUsername());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
