package com.salmon.jpa.core.repositories;

import com.salmon.jpa.core.page.QueryResultImpl;
import com.salmon.jpa.core.page.QueryResult;
import com.salmon.jpa.core.utils.CriterionHelper;
import com.salmon.jpa.core.utils.FilterKey;
import com.salmon.jpa.core.utils.FromPathHelper;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public class BaseRepositoryImpl<T,Key extends Serializable> extends SimpleJpaRepository<T,Key> implements BaseRepository<T, Key>{

    private static final String ORDER_ATTRIBUTE_SPLIT = "\\.";

    private EntityManager entityManager;
    private Class<T> domainClass;
    private Class<Key> keyClass;
    private String keyPropertyName;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl(RepositoryInformation information,EntityManager entityManager) {
        super((Class<T>) information.getDomainType(), entityManager);
        this.entityManager = entityManager;
        this.domainClass = (Class<T>) information.getDomainType();
        this.keyClass = (Class<Key>) information.getIdType();
        this.keyPropertyName = getKeyProperty(this.domainClass);//获取实体主键的名称
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Transactional
    public Integer deleteByCondition(Map<String,Object> mapParam){
        if(FilterKey.isContainsJoin(mapParam)){
            return deleteByComplexCondition(mapParam);
        }
        else{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaDelete<T> cd = cb.createCriteriaDelete(domainClass);
            Root<T> root = cd.from(domainClass);
            FromPathHelper<T> fromPathHelper = new FromPathHelper<>(root);
            Predicate predicate = getPredicate(cb, fromPathHelper, mapParam);
            cd.where(predicate);
            Query qDel = entityManager.createQuery(cd);
            return qDel.executeUpdate();
        }
    }

    @Override
    @Transactional
    public Integer updateByCondition(Map<String, Object> mapParam, Map<String, Object> mapUpdateField){
        if(FilterKey.isContainsJoin(mapParam)){
            return updateByComplexCondition(mapParam, mapUpdateField);
        }
        else{
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> cu = cb.createCriteriaUpdate(domainClass);
            Root<T> root = cu.from(domainClass);
            FromPathHelper<T> fromPathHelper = new FromPathHelper<>(root);
            Predicate predicate = getPredicate(cb, fromPathHelper, mapParam);
            cu.where(predicate);
            for(Map.Entry<String, Object> entry : mapUpdateField.entrySet()){
                cu.set(entry.getKey(), entry.getValue());
            }
            Query qUpdate = entityManager.createQuery(cu);
            return qUpdate.executeUpdate();
        }
    }

    @Override
    @Transactional
    public Integer deleteByComplexCondition(Map<String,Object> mapParam){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        List<Key> ids = this.searchAllIds(mapParam);
        if(ids != null && ids.size()>0){
            CriteriaDelete<T> cd = cb.createCriteriaDelete(domainClass);
            Root<T> root = cd.from(domainClass);
            Map<String, Object> mapDeleteParam = new HashMap<>();
            mapDeleteParam.put("include_" + this.keyPropertyName, ids);
            FromPathHelper<T> fromPathHelper = new FromPathHelper<>(root);
            Predicate predicate = getPredicate(cb, fromPathHelper, mapDeleteParam);
            cd.where(predicate);
            Query qDel = entityManager.createQuery(cd);
            return qDel.executeUpdate();
        }
        return 0;
    }

    @Override
    @Transactional
    public Integer updateByComplexCondition(Map<String, Object> mapParam, Map<String, Object> mapUpdateField){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        List<Key> ids = this.searchAllIds(mapParam);
        if(ids != null && ids.size()>0){
            CriteriaUpdate<T> cu = cb.createCriteriaUpdate(domainClass);
            Root<T> root = cu.from(domainClass);
            Map<String, Object> mapUpdateParam = new HashMap<>();
            mapUpdateParam.put("include_" + this.keyPropertyName, ids);
            FromPathHelper<T> fromPathHelper = new FromPathHelper<>(root);
            Predicate predicate = getPredicate(cb, fromPathHelper, mapUpdateParam);
            cu.where(predicate);
            for(Map.Entry<String, Object> entry : mapUpdateField.entrySet()){
                cu.set(entry.getKey(), entry.getValue());
            }
            Query qUpdate = entityManager.createQuery(cu);
            return qUpdate.executeUpdate();
        }
        return 0;
    }

    @Override
    public Long searchTotalCount(Map<String, Object> mapParam) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
        Root<T> rootCount = this.getRoot(cqCount);
        FromPathHelper<T> fromPathHelperForCount = new FromPathHelper<>(rootCount);
        Predicate predicateCount = getPredicate(cb, fromPathHelperForCount, getForCountMapParam(mapParam));
        cqCount = cqCount.select(cb.count(rootCount));
        if(predicateCount != null){
            cqCount.where(predicateCount);
        }
        return entityManager.createQuery(cqCount).getSingleResult();
    }

    @Override
    public QueryResult<T> searchPage(Map<String, Object> mapParam, Map<String, String> mapSort, Integer limit, Long start,QueryResult<T> pv) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Long totalRow = null;
        if(limit != null && limit>0 && start != null && start>-1){
            //获取行数
            totalRow = searchTotalCount(mapParam);
        }

        if(pv == null)
            pv = getInitQueryResult(totalRow, start, limit);
        //如果不分页(因为不分页则)或者分页并且总行数大于0
        if(!pv.pageable() || pv.getTotalRow()>0){
            CriteriaQuery<T> cq = this.getCriteriaQuery(cb);
            Root<T> root = this.getRoot(cq);

            FromPathHelper<T> fromPathHelper = new FromPathHelper<>(root);
            Predicate predicate = getPredicate(cb, fromPathHelper, mapParam);
            if(predicate != null){
                cq.where(predicate);
            }

            List<Order> orders = this.getOrders(root, mapSort);
            if(orders != null && orders.size()>0){
                cq.orderBy(orders);
            }
            cq.select(root);
            TypedQuery<T> tq = entityManager.createQuery(cq);
            if(pv.pageable()){
                tq.setFirstResult(pv.getBeginRow().intValue());
                tq.setMaxResults(pv.getPageRow());
            }
            List<T> content = tq.getResultList();
            pv.setContent(content);
        }
        else{
            pv.setContent(new ArrayList<>());
        }
        return pv;
    }


    @Override
    public QueryResult<T> searchPage(Map<String, Object> mapParam, Map<String, String> mapSort, Integer limit, Long start) {
        return searchPage(mapParam,mapSort,limit,start,null);
    }

    @Override
    public List<T> searchAll(Map<String, Object> mapParam, Map<String, String> mapSort){
        QueryResult<T> pvResult = searchPage(mapParam, mapSort, null, null);
        if(pvResult == null)return null;
        return pvResult.getContent();
    }

    private Map<String,Object> getForCountMapParam(Map<String, Object> mapParam){
        if(mapParam == null) return null;
        Map<String, Object> mapParamForCount = new LinkedHashMap<>();
        for(Map.Entry<String,Object> entry : mapParam.entrySet()){
            //keyForCount = key.replaceAll("FL@", "L@");
            String keyForCount = entry.getKey().replaceAll("FI@", "I@");
            mapParamForCount.put(keyForCount, entry.getValue());
        }
        return mapParamForCount;
    }

    @Override
    public Predicate getPredicate(CriteriaBuilder cb, final FromPathHelper<T> fromPathHelper, Map<String, Object> mapParam) {
        if(cb == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getPredicate方法中的CriteriaBuilder类型参数为null。");
        }
        if(fromPathHelper == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getPredicate方法中的FromPathHelper类型参数为null。");
        }
        if(mapParam == null || mapParam.size() < 1){
            return null;
        }
        List<Predicate> lsPredicate = new ArrayList<>();
        for(Map.Entry<String, Object> entry : mapParam.entrySet()){
            Predicate p = getPredicateByKeyValue(cb, fromPathHelper, entry.getKey(), entry.getValue());
            if(p != null){
                lsPredicate.add(p);
            }
        }
        if(lsPredicate.size()>0){
            return cb.and(lsPredicate.toArray(new Predicate[lsPredicate.size()]));
        }
        else{
            return null;
        }
    }

    /**
     *
     * @param cb  CriteriaBuilder
     * @param fromPathHelper  FromPathHelper
     * @param key key的格式为like_[L@people.R@classType_]name
     * 其中[]内为可选内容，表示为root实体的关联实体，如果没有[]内的内容，则表示最后的属性为root的直接属性。
     * 最前面的的操作符，最后面的是操作属性。
     * @param value
     * @return Predicate
     */
    public Predicate getPredicateByKeyValue(CriteriaBuilder cb, final FromPathHelper<T> fromPathHelper, String key, Object value){
        if(cb == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getPredicateByKeyValue方法中的CriteriaBuilder类型参数为null。");
        }
        if(fromPathHelper == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getPredicateByKeyValue方法中的fromPathHelper类型参数为null。");
        }
        if(key == null || key.trim().equals("")){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getPredicateByKeyValue方法中的过滤条件对应的key参数的为null或空字符串。");
        }
        FilterKey fk = new FilterKey(key);
        Path<?> from = fromPathHelper.getFromByListJoinInfo(fk.getJoinStr());
        if(fk.getPropertyName() == null){
            return null;
        }
        return getPredicate(cb, from, fk.getOperator(), fk.getPropertyName(), value);
    }

    private Predicate getPredicate(CriteriaBuilder cb, final Path<?> from, CriterionHelper.Operator operator, String attrName, Object value){
        CriterionHelper criterion = new CriterionHelper(attrName, operator, value);
        return criterion.getOperator().toPredicate(criterion, from, cb);
    }

    @Override
    public List<Order> getOrders(Root<T> root, Map<String, String> sortMap) {
        if(root == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getOrders方法中的Root<T>类型参数为null。");
        }
        if(sortMap == null || sortMap.size()<1){
            return null;
        }
        List<Order> listOrder = new ArrayList<>();
        for(Map.Entry<String,String> entry : sortMap.entrySet()){
            String[] keyInfo = entry.getKey().split(ORDER_ATTRIBUTE_SPLIT);
            Path<?> path = root;
            for(String p : keyInfo){
                path = path.get(p);
            }
            Order order = new OrderImpl(path, isAscending(entry.getValue()));
            listOrder.add(order);
        }
        return listOrder;
    }

    private boolean isAscending(String sortDirection){
        if(sortDirection == null) return true;
        if(sortDirection.toUpperCase().contains("ASC")){
            return true;
        }
        return false;
    }

    private Sort.Direction getDirection(String sortDirection){
        if(isAscending(sortDirection)){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    @Override
    public CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder cb) {
        if(cb == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getCriteriaQuery方法中的CriteriaBuilder类型参数为null。");
        }
        return cb.createQuery(this.domainClass);
    }

    @Override
    public Root<T> getRoot(CriteriaQuery<?> cq) {
        if(cq == null){
            throw new IllegalArgumentException("AbstractSearchableJpaRepository.getRoot方法中的CriteriaQuery类型参数为null。");
        }
        return cq.from(this.domainClass);
    }

    @Override
    public List<Sort> getSort(LinkedHashMap<String, String> sortMap) {
        if(sortMap == null || sortMap.size()<1) return null;
        List<Sort> listSort = new ArrayList<>();
        for(Map.Entry<String,String> entry : sortMap.entrySet()){
            Sort sort = new Sort(getDirection(entry.getValue()), entry.getKey());
            listSort.add(sort);
        }
        return listSort;
    }

    /**
     * 根据总行数，开始行数和每页限制最大行数，获得一个初始的PageVO对象，
     * 用来进行进一步的分页信息获取。
     * @param totalRow 总记录数，即一共有多少行
     * @param start 起始行 从0开始
     * @param limit 每页显示条数
     * @return
     */
    private QueryResult<T> getInitQueryResult(Long totalRow, Long start, Integer limit) {
        return new QueryResultImpl<>(totalRow, start, limit);
    }

    @Override
    public List<Key> searchAllIds(Map<String, Object> mapParam) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Key> cq = cb.createQuery(this.keyClass);
        Root<T> root = this.getRoot(cq);

        FromPathHelper<T> fromPathHelper = new FromPathHelper<>(root);
        Predicate predicate = getPredicate(cb, fromPathHelper, mapParam);
        if(predicate != null){
            cq.where(predicate);
        }
        cq.select(root.<Key>get(this.keyPropertyName).alias(this.keyPropertyName));
        TypedQuery<Key> tq = entityManager.createQuery(cq);

        return tq.getResultList();
    }

    private FullTextQuery getHibernateSearchTextQuery(String searchString,String... field){
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(this.domainClass).get();
        org.apache.lucene.search.Query query = qb.keyword().onFields(field).matching(searchString).createQuery();
        return fullTextEntityManager.createFullTextQuery(query, this.domainClass);
    }

    private String getKeyProperty(Class<?> clazz){
        boolean isIdExists = false;
        String keyName = null;
        for(Field f : clazz.getDeclaredFields()){
            if(!f.getType().equals(this.keyClass)) continue;
            if("id".equals(f.getName())){
                isIdExists = true;
            }
            try {
                Method method = clazz.getMethod(getGetterName(f.getName()));
                if(f.getAnnotation(Id.class) != null || (method != null && method.getAnnotation(Id.class) != null)){
                    keyName = f.getName();
                    break;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if(isIdExists && keyName == null){
            keyName = "id";
        }
        return keyName;
    }

    private String getGetterName(String fieldName){
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }


    /***************************  全文检索的 方法实现 ***************************************/
    @Override
    @SuppressWarnings("unchecked")
    public List<T> searchText(String searchString,String... field) {
        FullTextQuery persistenceQuery = getHibernateSearchTextQuery(searchString,field);
        return persistenceQuery.getResultList();
    }
    @Override
    @SuppressWarnings("unchecked")
    public QueryResult<T> searchTextPage(String searchString,Integer start, Integer limit,String... field){
        FullTextQuery persistenceQuery = getHibernateSearchTextQuery(searchString,field);
        Integer size = persistenceQuery.getResultSize();
        persistenceQuery.setFirstResult(start < 0 ? 0 : start);
        persistenceQuery.setMaxResults(limit);
        QueryResult<T> pv = getInitQueryResult(size.longValue(),start.longValue(),limit);
        if(pv.getTotalRow()>0){
            pv.setContent(persistenceQuery.getResultList());
        }else{
            pv.setContent(new ArrayList<>());
        }
        return pv;
    }
    /******************************************************************/
}
