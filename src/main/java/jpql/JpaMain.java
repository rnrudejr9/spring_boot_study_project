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
            //flush 됨 영속성 컨텍스트에 반영되어 있음 DB 가 아님

            em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            //벌크 연산 DB에 바로 반영이됨

            em.clear(); // 영속성컨텍스트 초기화 이후에 다시 사용해야 age 가 반영되어 사용할 수 있다.


            List<Member> list = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
            for(Member m : list){
                System.out.println(m.getAge() + " " + m.getUsername());
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
