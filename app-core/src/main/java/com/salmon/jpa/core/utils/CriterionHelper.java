package com.salmon.jpa.core.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;

public class CriterionHelper {

	private final String propertyName;
	private final Operator operator;
	private final Object compareTo;
	
	public String getPropertyName() {
		return propertyName;
	}

	public Operator getOperator() {
		return operator;
	}

	public Object getCompareTo() {
		return compareTo;
	}

	public CriterionHelper(String propertyName, String operator, Object compareTo){
		this.propertyName = propertyName;
		this.operator = Operator.getOperatorByStrIgnoreCase(operator);
		this.compareTo = compareTo;
		if(operator == null){
			throw new IllegalArgumentException("Initial Criterion Exception, operator '" + operator + "' is not exists.");
		}
	}
	
	public CriterionHelper(String propertyName, Operator operator, Object compareTo){
		this.propertyName = propertyName;
		this.operator = operator;
		this.compareTo = compareTo;
		if(operator == null){
			throw new IllegalArgumentException("Initial Criterion Exception, operator '" + operator + "' is not exists.");
		}
	}
	
	public static enum Operator{
		EQ{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.equal(f.get(c.getPropertyName()), c.getCompareTo());
			}
		},NEQ{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.notEqual(f.get(c.getPropertyName()), c.getCompareTo());
			}
		},LT{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.lessThan(f.get(c.getPropertyName()),(Comparable)getComparable(c));
			}
		},GT{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.greaterThan(f.get(c.getPropertyName()), (Comparable)getComparable(c));
			}
		},LE{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.lessThanOrEqualTo(f.get(c.getPropertyName()), (Comparable)getComparable(c));
			}
		},GE{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.greaterThanOrEqualTo(f.get(c.getPropertyName()), (Comparable)getComparable(c));
			}
		},TIMEEND{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.lessThanOrEqualTo(f.get(c.getPropertyName()), (Comparable)getComparable(c));
			}
		},TIMEBEGIN{
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.greaterThanOrEqualTo(f.get(c.getPropertyName()), (Comparable)getComparable(c));
			}
		},INCLUDE{
			@SuppressWarnings({ "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				Object o = c.getCompareTo();
				if(o == null){
					return f.get(c.getPropertyName()).in();
				}
				if(o instanceof Collection){
					return f.get(c.getPropertyName()).in((Collection)o);
				}
				if(o.getClass().isArray()){
					return f.get(c.getPropertyName()).in((Object[])o);
				}
				if(o instanceof String){
					return f.get(c.getPropertyName()).in((Object[])((String)o).split(","));
				}
				throw new IllegalArgumentException(c.getPropertyName()+" is not Collection type or it's SubType");
			}
		},EXCLUDE{
			@SuppressWarnings({ "rawtypes" })
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				Object o = c.getCompareTo();
				if(o == null){
					return b.not(f.get(c.getPropertyName()).in());
				}
				if(o instanceof Collection){
					return b.not(f.get(c.getPropertyName()).in((Collection)o));
				}
				if(o.getClass().isArray()){
					return b.not(f.get(c.getPropertyName()).in((Object[])o));
				}
				if(o instanceof String){
					return b.not(f.get(c.getPropertyName()).in((Object[])((String)o).split(",")));
				}
				throw new IllegalArgumentException(c.getPropertyName()+" is not Collection type or it's SubType");
			}
		},LIKE{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.like(f.<String>get(c.getPropertyName()), "%"+getString(c)+"%");
			}
		},BEGINLIKE{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.like(f.<String>get(c.getPropertyName()), getString(c)+"%");
			}
		},ENDLIKE{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.like(f.<String>get(c.getPropertyName()), "%"+getString(c));
			}
		},DIRECTLIKE{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return b.like(f.<String>get(c.getPropertyName()), getString(c));
			}
		},NULL{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return f.get(c.getPropertyName()).isNull();
			}
		},NOTNULL{
			public Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b){
				return f.get(c.getPropertyName()).isNotNull();
			}
		};
		
		public abstract Predicate toPredicate(CriterionHelper c, Path<?> f, CriteriaBuilder b);
		private static Comparable<?> getComparable(CriterionHelper c){
			Object o = c.getCompareTo();
			if(o != null && !(o instanceof Comparable))
				throw new IllegalArgumentException(c.getPropertyName());
			return (Comparable<?>)o;
		}
		
		private static String getString(CriterionHelper c){
			if(!(c.getCompareTo() instanceof String)){
				throw new IllegalArgumentException(c.getPropertyName());
			}
			return (String)c.getCompareTo();
		}
		
		public static Operator getOperatorByStrIgnoreCase(String operator){
			if(operator == null || operator.trim().equals(""))return null;
			for(Operator op : Operator.values()){
				if(operator.equalsIgnoreCase(op.toString())){
					return op;
				}
			}
			return null;
		}
	}
}
