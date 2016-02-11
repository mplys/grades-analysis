package base;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Student entity
 */
public class StudentInfo implements Serializable {
	private static final long serialVersionUID = -310842754445106856L;
	/**
	 * Applicant's name
	 */
	private String name;

	/**
	 * Applicant's IC number
	 */
	private long icNumber;

	/**
	 * Applicant's matrix number
	 */
	private long martixNumber;

	/**
	 * Applicant's group
	 */
	private int group;

	/**
	 * Amount of subjects
	 */
	private int numberOfSubjects;

	/**
	 * Array of subject codes
	 */
	private int[] subjectCode;

	/**
	 * Applicant's marks
	 */
	private int[] marks;

	/**
	 * Applicant's grades
	 */
	private int[] grades;

	/**
	 * Constructor with all parameters
	 */
	public StudentInfo(String name, long icNumber, long martixNumber, int group, int numberOfSubjects,
			int[] subjectCode, int[] marks, int[] grades) {
		this.name = name;
		this.icNumber = icNumber;
		this.group = group;
		this.numberOfSubjects = numberOfSubjects;
		this.marks = marks;
		this.grades = grades;
		this.martixNumber = martixNumber;
		this.subjectCode = subjectCode;
	}

	// Getters and setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getICNumber() {
		return icNumber;
	}

	public void setICNumber(long icNumber) {
		this.icNumber = icNumber;
	}
	
	public long getMartixNumber() {
		return martixNumber;
	}
	
	public void setMartixNumber(long martixNumber) {
		this.martixNumber = martixNumber;
	}

	public int getGroup() {
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
	
	public int getNumberOfSubjects() {
		return numberOfSubjects;
	}
	
	public void setNumberOfSubjects(int numberOfSubjects) {
		this.numberOfSubjects = numberOfSubjects;
	}
	
	public int[] getSubjectCode() {
		return subjectCode;
	}
	
	public void setSubjectCode(int [] subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	public int[] getMarks() {
		return marks;
	}
	
	public void setMarks(int [] marks) {
		this.marks = marks;
	}
	
	public int[] getGrades() {
		return grades;
	}
	
	public void setGrades(int [] grades) {
		this.grades = grades;
	}
	
	@Override
	public String toString() {
		StringBuilder userInfoAsString = new StringBuilder();
		
		userInfoAsString
		.append("Name: ").append(getName()).append('\n')
		.append("IC Number: ").append(getICNumber()).append('\n')
		.append("Martix Number: ").append(getMartixNumber()).append('\n')
		.append("Group: ").append(getGroup()).append('\n')
		.append("Number of subjects: ").append(getNumberOfSubjects()).append('\n')
		.append("Subject Code: ").append(Arrays.toString(getSubjectCode())).append('\n')
		.append("Marks: ").append(Arrays.toString(getMarks())).append('\n')
		.append("Grades: ").append( Arrays.toString(getGrades())).append('\n');

		return userInfoAsString.toString();
	}
}
