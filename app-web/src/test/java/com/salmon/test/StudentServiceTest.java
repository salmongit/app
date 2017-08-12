package com.salmon.test;

import com.salmon.entities.Student;
import com.salmon.entities.Teacher;
import com.salmon.jpa.core.config.ApplicationConfiguration;
import com.salmon.jpa.core.page.QueryResult;
import com.salmon.repositories.StudentRepositoy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,classes={ApplicationConfiguration.class})
public class StudentServiceTest {

    @Autowired
    StudentRepositoy studentRepository;

    @Test
    @Transactional
    public void testSearchStudent(){
        /*
            select
        count(student0_.id) as col_0_0_
    from
        T_STUDENT student0_
    inner join
        T_TEACHER teacher1_
            on student0_.teacher_id=teacher1_.id
    where
        (
            teacher1_.name like ?
        )
        and (
            teacher1_.name like ?
        )
        and (
            student0_.name in (
                ? , ?
            )
        )
         */

        Map<String, Object> mapParam = new LinkedHashMap<>();
        mapParam.put("like_FI@teacher_name", "wang");
        mapParam.put("BEGINLIKE_FI@teacher_name", "wang");
        mapParam.put("include_name", "王,李".split(","));
        Map<String, String> mapSort = new LinkedHashMap<String, String>();
        mapSort.put("name", "asc");
        mapSort.put("teacher.name", "desc");
        QueryResult<Student> ls = studentRepository.searchPage(mapParam, mapSort, 2, 0L);
        System.out.println(ls.toJsonString());
    }


    @Test
    @Transactional
    public void testCriteria(){
        CriteriaBuilder criteriaBuilder = studentRepository.getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Teacher> cq = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> teacher = cq.from(Teacher.class);

        Predicate pen = criteriaBuilder.equal(teacher.get("name"), "zhangsan");

        Predicate pen1 = criteriaBuilder.equal(teacher.get("name"), "1zhangsan");
        cq.where(criteriaBuilder.or(pen,pen1));
        TypedQuery<Teacher> qp = studentRepository.getEntityManager().createQuery(cq);
        List<Teacher> lp = qp.getResultList();
        System.out.println(lp);

    }

    @Test
    public void testlambda(){
        // 新方法：
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double bill = costBeforeTax.stream().map((cost) -> cost + 0.12 * cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }
}
