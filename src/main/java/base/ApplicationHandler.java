package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("resource")
public class ApplicationHandler {

	private static File fileName ;
	
	/**
	 * Minimal grade for passed exam
	 */
	private static final int MIN_GRADE = 3;

	public static void main(String args[]) {
		try {
			ApplicationHandler handler = new ApplicationHandler();
			
			fileName = new File(handler.getClass().getResource("/studentInfo.txt").getFile());

			handler.formMenuChoices();
			while (true) {
				handler.switchAction();
			}

		} catch (IOException e) {
			System.err.println("Failure to execute. See message: " + e.getMessage());
		}
	}

	public void formMenuChoices() {
		System.out.println("\n");
		System.out.println("*** Please, type the number of action ***");
		System.out.println("0 -> Display user dialog");
		System.out.println("1 -> Add new student information");
		System.out.println("2 -> Delete student information");
		System.out.println("3 -> Update student information");
		System.out.println("4 -> Display all data information");
		System.out.println("5 -> Display all passed students");
		System.out.println("6 -> Display all failed students");
		System.out.println("7 -> Display highest of subject");
		System.out.println("8 -> Display lowest of subject");
		System.out.println("9 -> Display subject with most failed student");
		System.out.println("10 -> Display subject with most passed student");
		System.out.println("11 -> Search student information (name/matrix id)");
		System.out.println("12 -> Exit");
		System.out.println("\n");
	}

	public void switchAction() throws IOException {
		int action;
		Scanner sc = new Scanner(System.in);
		action = sc.nextInt();
		
		switch (action) {
		
		case 0:
			formMenuChoices();
			break;

		case 1:
			if (addNewStudenInfo())
				System.out.println("Student has been added");
			else
				System.err.println("Failure to add new student");
			break;

		case 2:
			if (deleteStudentInfo() != -1)
				System.out.println("Student has been deleted");
			else
				System.err.println("No student with such matrix number");
			break;

		case 3:
			if (updateStudentInfo() != -1)
				System.out.println("Information has been updated");
			else
				System.err.println("Student with such matrix number was not found");
			break;

		case 4:
			displayAllStudentsData();
			break;

		case 5:
			displayAllPassedStudents();
			break;

		case 6:
			displayAllFailedStudents();
			break;

		case 7:
			displayHighestSubject();
			break;
			
		case 8:
			displayLowestSubject();
			break;
			
		case 9:
			displaySubjectWithMostFailedStudent();
			break;
			
		case 10:
			displaySubjectWithMostPassedStudent();
			break;
			
		case 11:
			lookupStudent();
			break;
			
		case 12:
			System.exit(0);
			break;
			
		default:
			System.exit(1);
		}
		
		System.out.print("\n>");
	}

	private void lookupStudent() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter student matrix number :");
		long matrixNumber = sc.nextLong();

		for (StudentInfo app : list) {
			if (app.getMartixNumber() == matrixNumber) {
				System.out.println(app);
				return;
			}
		}
		
		System.err.println("Student with " + matrixNumber + " does not exist");
	}

	private long deleteStudentInfo() throws IOException {
		System.out.println("Enter student matrix mumber");
		Scanner scDelete = new Scanner(System.in);
		long number = scDelete.nextLong();
		
		List<StudentInfo> list = readObjectsFromFile();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMartixNumber() == number) {
				list.remove(i);
				writeObjectsToFile(list, false);
				return number;
			}
		}
		return -1;
	}

	private long updateStudentInfo() throws IOException {
		System.out.println("Enter student matrix mumber");
		Scanner scUpdate = new Scanner(System.in);
		long number = scUpdate.nextLong();
		System.out.println("Input new information");
		
		List<StudentInfo> list = readObjectsFromFile();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMartixNumber() != number)
				continue;

			StudentInfo student = readInfoFromConsole(true);
			if (student == null) {
				System.err.println("Failure to update sudent");
				return -1;
			}

			student.getName();
			list.get(i).setName(student.getName());
			list.get(i).setICNumber(student.getICNumber());
			list.get(i).setMartixNumber(student.getMartixNumber());
			list.get(i).setGroup(student.getGroup());
			list.get(i).setMarks(student.getMarks());
			list.get(i).setGrades(student.getGrades());
			list.get(i).setNumberOfSubjects(student.getNumberOfSubjects());
			list.get(i).setSubjectCode(student.getSubjectCode());
			writeObjectsToFile(list, true);
			return number;
		}
		
		System.err.println("No student with matrix number " + number);
		return -1;
	}

	private void displayAllStudentsData() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
			for (StudentInfo app : list) {
				System.out.println(app);
			}
	}

	private void displayAllPassedStudents() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
			list = readObjectsFromFile();
			for (StudentInfo app : list) {
				if (isPassed(app.getGrades()))
					System.out.println(app);
			}
	}

	private void displayAllFailedStudents() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
			for (StudentInfo app : list) {
				if (!isPassed(app.getGrades()))
					System.out.println(app);
			}
	}

	private void displayHighestSubject() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
		Map <Integer, Integer> map = new HashMap<Integer, Integer>();
		for(StudentInfo stud: list) {
			for(int grad: stud.getSubjectCode()) {
				if (map.containsKey(grad)){
					map.replace(grad, map.get(grad) + 1);
				} else {
					map.put(grad, 1);
				}
			}
		}
		
		int highest = -1;
		int gradCode = -1;
		
		for(int grad: map.keySet()){
			if (highest < map.get(grad)){
				highest = map.get(grad);
				gradCode = grad;
			}
		}
		
		System.out.println("The highest subject is " + gradCode);
	}

	private void displayLowestSubject() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();

		Map <Integer, Integer> map = new HashMap<Integer, Integer>();
		for(StudentInfo stud: list) {
			for(int grad: stud.getSubjectCode()) {
				if (map.containsKey(grad)){
					map.replace(grad, map.get(grad) + 1);
				} else {
					map.put(grad, 1);
				}
			}
		}
		
		int highest = Integer.MAX_VALUE;
		int gradCode = -1;
		
		for(int grad: map.keySet()){
			if (highest > map.get(grad)){
				highest = map.get(grad);
				gradCode = grad;
			}
		}
		
		System.out.println("The lowest subject is " + gradCode);
	}

	private void displaySubjectWithMostFailedStudent() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
		Map <Integer, Integer> map = new HashMap<Integer, Integer>();
		for(StudentInfo stud: list) {
			for(int i = 0; i < stud.getSubjectCode().length; i++ ) {
				
				if (stud.getGrades()[i] < MIN_GRADE) {
					
					if (map.containsKey(stud.getSubjectCode()[i])) {
						map.replace(stud.getSubjectCode()[i], map.get(stud.getSubjectCode()[i]) + 1);
					} else {
						map.put(stud.getSubjectCode()[i], 1);
					}
				}
			}
		}
	
		int most = -1;
		int gradCode = -1;
		
		for(int grad: map.keySet()){
			if (most < map.get(grad)){
				most = map.get(grad);
				gradCode = grad;
			}
		}
		System.out.println("Subject with most failed student " + gradCode);
	}
	
	private void displaySubjectWithMostPassedStudent() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
		Map <Integer, Integer> map = new HashMap<Integer, Integer>();
		for(StudentInfo stud: list) {
			for(int i = 0; i < stud.getSubjectCode().length; i++ ) {
				
				if (stud.getGrades()[i] >= MIN_GRADE) {
					
					if (map.containsKey(stud.getSubjectCode()[i])) {
						map.replace(stud.getSubjectCode()[i], map.get(stud.getSubjectCode()[i]) + 1);
					} else {
						map.put(stud.getSubjectCode()[i], 1);
					}
				}
			}
		}
		
		int most = -1;
		int gradCode = -1;
		
		for(int grad: map.keySet()){
			if (most < map.get(grad)){
				most = map.get(grad);
				gradCode = grad;
			}
		}
		
		System.out.println("Subject with most passed student " + gradCode);
	}
	
	private boolean isPassed(int[] grades) {
		for (int grade : grades) {
			if (grade < MIN_GRADE)
				return false;
		}
		return true;
	}

	private StudentInfo readInfoFromConsole(boolean isUpdate) throws IOException {
		String name;
		long icNumber;
		long martixNumber;
		int group;
		int numberOfSubjects;
		int[] subjectCode;
		int[] marks;
		int[] grades;
		
		List<StudentInfo> list = readObjectsFromFile();

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter name: ");
		name = sc.nextLine();

		System.out.print("Enter IC number: ");
		icNumber = sc.nextLong();
		if (isUpdate == false) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getICNumber() == icNumber) {
					System.err.println(
							"Student with IC Number " + icNumber + " already exists. IC Number should be unique");
					return null;
				}
			}
		}

		System.out.print("Enter martix number: ");
		martixNumber = sc.nextInt();
		if (isUpdate == false) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getMartixNumber() == martixNumber) {
					System.err.println("Student with matrix number " + martixNumber
							+ " already exists. Matrix number should be unique");
					return null;
				}
			}
		}
		System.out.print("Enter group: ");
		group = sc.nextInt();

		System.out.print("Enter number of subjects: ");
		numberOfSubjects = sc.nextInt();

		System.out.print("Enter subjects code: ");
		subjectCode = new int[numberOfSubjects];
		for (int i = 0; i < numberOfSubjects; i++)
			subjectCode[i] = sc.nextInt();

		System.out.print("Enter the marks: ");
		marks = new int[numberOfSubjects];
		for (int i = 0; i < numberOfSubjects; i++)
			marks[i] = sc.nextInt();

		System.out.print("Enter the grades: ");
		grades = new int[numberOfSubjects];
		for (int i = 0; i < numberOfSubjects; i++)
			grades[i] = sc.nextInt();
		StudentInfo student = new StudentInfo(name, icNumber, martixNumber, group, numberOfSubjects, subjectCode, marks,
				grades);
		
		return student;
	}

	private boolean addNewStudenInfo() throws IOException {
		StudentInfo student = readInfoFromConsole(false);
		List<StudentInfo> list = readObjectsFromFile();

		if (null == student)
			return false;

		list.add(student);
		writeObjectsToFile(list, false);
		return true;
	}

	public boolean isExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	public void writeObjectsToFile(List<StudentInfo> studentsList, boolean isAppend) throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		
		try {
			fout = new FileOutputStream(fileName, isAppend);
			for (StudentInfo haj : studentsList) {
				oos = new ObjectOutputStream(fout);
				oos.writeObject(haj);

			}
			oos.flush();
		} catch (Exception ex) {
			System.err.println("Failure write to file: " + ex.getMessage());
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
	}

	public List<StudentInfo> readObjectsFromFile() throws IOException {
		ObjectInputStream objectinputstream = null;
		FileInputStream streamIn = null;
		List<StudentInfo> newStudents = new CustomLinkedList<StudentInfo>();
		
		try {
			streamIn = new FileInputStream(fileName);
			while (streamIn.available() != 0) {
				objectinputstream = new ObjectInputStream(streamIn);
				StudentInfo a = (StudentInfo) objectinputstream.readObject();
				newStudents.add(a);
			}
			return newStudents;
			
		} catch (Exception e) {
			System.err.println("Failure read to file: " + e.getMessage());
			return null;
			
		} finally {
			if (objectinputstream != null) {
				objectinputstream.close();
			}
			if (streamIn != null) {
				streamIn.close();
			}
		}

	}

}
