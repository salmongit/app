package com.salmon.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "T_STUDENT")
public class Student implements Serializable {
	private static final long serialVersionUID = -7133560211323888692L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100)
	@NotNull(message = "这个字段必传")
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday;
	@ManyToMany(cascade={CascadeType.REFRESH})
	@JoinTable(name="Student_Course_Links",
			joinColumns={@JoinColumn(name="studentId", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="courseId", referencedColumnName="id")})
	private Set<Course> courses;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}


	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Student student = (Student) o;

		return id != null ? id.equals(student.id) : student.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
