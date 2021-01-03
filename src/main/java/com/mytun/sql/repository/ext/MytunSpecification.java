package com.mytun.sql.repository.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;


public class MytunSpecification<T> implements Specification<T> {

    private static final Logger log = LoggerFactory.getLogger(MytunSpecification.class);
    String sort;
    String query;
    public MytunSpecification(String sort, String query) {
        this.sort = sort;
        this.query = query;
    }

    private boolean isInteger(String str) {
        if (!StringUtils.hasText(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private boolean isDouble(String str) {
        if (!StringUtils.hasText(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
    private int isDate(String str) {
        String a1 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
        String a2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//yyyy-MM-dd
        String a3= "[0-9]{2}:[0-9]{2}:[0-9]{2}";//HH:mm:ss
        if (!StringUtils.hasText(str)) {
            return -1;
        }
        if(Pattern.compile(a2).matcher(str).matches()){
           return 1;
        }else if(Pattern.compile(a3).matcher(str).matches()){
            return 3;
        }else if(Pattern.compile(a1).matcher(str).matches()){
            return 2;
        }else {
            return -1;
        }
    }

    private List<Order> descAsc(Root<T> root, CriteriaBuilder builder){
        List<Order> lo = new ArrayList<>();
        String[] s = sort.split(",");
        for(String i : s){
            OracleCriteria oc = new OracleCriteria(i);
            String[] sa = oc.getKey().split("\\.");
            Path<?> p = root.get(sa[0]);
            if(sa.length>=2){
                for (int i_=1;i_<sa.length;i_++){
                    p = p.get(sa[i_]);
                }
            }
            if(oc.getOperation().equals("desc")){
                lo.add(builder.desc(p));
            }else if(oc.getOperation().equals("asc")){
                lo.add(builder.asc(p));
            }
        }
        return lo;
    }
    private Predicate predicate (String operation, Path ppv, Path valueObject, CriteriaBuilder builder){
        switch (operation){
            case "=":
                return builder.equal(ppv, valueObject);
            case ">=":
                return builder.greaterThanOrEqualTo(ppv, valueObject);
            case "<=":
                return builder.lessThanOrEqualTo(ppv, valueObject);
            case ">":
                return builder.greaterThan(ppv,valueObject);
            case "<":
                return builder.lessThan(ppv, valueObject);
            default:
                throw new RuntimeException("not Mothe");
        }
    }

    private Predicate predicate (String operation, Path ppv, Comparable valueObject, CriteriaBuilder builder){
        switch (operation){
            case ">":
                return builder.greaterThan(ppv,valueObject);
            case "<":
                return builder.lessThan(ppv, valueObject);
            case "=":
                return builder.equal(ppv, valueObject);
            case ">=":
                return builder.greaterThanOrEqualTo(ppv, valueObject);
            case "<=":
                return builder.lessThanOrEqualTo(ppv, valueObject);
            default:
                throw new RuntimeException("not Mothe");
        }
    }

    private Predicate predicate (String operation, Path ppv, Number valueObject, CriteriaBuilder builder){
        switch (operation) {
            case ">":
                return builder.gt(ppv,valueObject);
            case "<":
                return builder.lt(ppv,valueObject);
            case "=":
                return builder.equal(ppv, valueObject);
            case ">=":
                return builder.ge(ppv,valueObject);
            case "<=":
                return builder.le(ppv,valueObject);
            default:
                throw new RuntimeException("not Mothe");
        }
    }


    private Predicate where(String i, Root<T> root, CriteriaBuilder builder){
        SearchCriteria criteria = new SearchCriteria(i);
        String[] sa = criteria.getKey().split("\\.");
        Path pp;
        if(sa.length==0){
            pp = root.get(criteria.getKey());
        }else {
            pp = root.get(sa[0]);
            if(sa.length>=2){
                for (int i_=1;i_<sa.length;i_++){
                    pp= pp.get(sa[i_]);
                }
            }
        }

        switch (criteria.getOperation()){
            case "notNull":
                return builder.isNotNull(pp);
            case "null":
                return builder.isNull(pp);
            case "like":
                return builder.like( pp, "%" + criteria.getValue() + "%");
            case "notLike":
                return builder.notLike( pp, "%" + criteria.getValue() + "%");
        }
        String value = criteria.getValue();
        boolean isInteger = isInteger(value);
        boolean isDouble = isDouble(value);
        if(isInteger||isDouble){
            Number ne;
            if(isInteger){
                ne = Integer.valueOf(criteria.getValue());
            }else {
                ne = Double.valueOf(criteria.getValue());
            }
            return predicate(criteria.getOperation(),pp,ne,builder);
        }else{
            Comparable<?> valueComparable = value;
            int isDate = isDate(criteria.getValue());
            if(isDate!=-1){
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm:ss");
                try {
                    if(isDate==1){
                        valueComparable = sdf1.parse(criteria.getValue());
                    }else if(isDate==2){
                        valueComparable = sdf2.parse(criteria.getValue());
                    } else if (isDate==3){
                        valueComparable = sdf3.parse(criteria.getValue());
                    }else {
                        throw new RuntimeException();
                    }
                }catch (Exception e){
                    throw new RuntimeException("not find model");
                }
            }else {
                String[] sav =value.split("\\.");
                Path<?> ppv;
                if(sav.length==0){
                    try {
                        ppv = root.get(value);
                    }catch (Exception e){
                        ppv = null;
                    }
                }else{
                    try {
                        ppv = root.get(sav[0]);
                        if(ppv!=null&&sav.length>=2){
                            for (int i_=1;i_<sa.length;i_++){
                                ppv= ppv.get(sa[i_]);
                                if(ppv==null){
                                    break;
                                }
                            }
                        }
                    }catch (Exception e){
                        try {
                            ppv = root.get(value);
                        }catch (Exception ee){
                            ppv = null;
                        }
                    }
                }
                if(ppv!=null){
                    return predicate(criteria.getOperation(),pp,ppv,builder);
                }
            }
            return predicate(criteria.getOperation(),pp,valueComparable,builder);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if(StringUtils.hasText(sort)){
            criteriaQuery.orderBy(descAsc(root,builder));
        }
        if(!StringUtils.hasText(query)){
            return builder.and();
        }
        log.info(query);
        String[] q = query.split(",");
        if(q.length%2==0){
            throw new RuntimeException("格式错误");
        }
        Stack<Predicate> sp = new Stack<>();
        for(String i: q){
            if(i.equals("-")){
                Predicate a = sp.pop();
                Predicate b = sp.pop();
                sp.push(builder.or(a,b));
            }else if(i.equals("+")){
                Predicate a = sp.pop();
                Predicate b = sp.pop();
                sp.push(builder.and(a,b));
            }else{
                sp.push(where(i,root,builder));
            }
        }
        return sp.pop();
    }


}
