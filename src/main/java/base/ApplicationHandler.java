package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class ApplicationHandler {

	private static String fileName = "Content/studentInfo.txt";
	private static List<StudentInfo> studentsList = new LinkedList<StudentInfo>();
	private static final int MIN_GRADE = 3;

	public static void main(String args[]) {
		try {
			ApplicationHandler handler = new ApplicationHandler();
			studentsList = handler.readObjectsFromFile();
			handler.formMenuChoices();
			do {
				handler.switchAction();
			} while (true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void formMenuChoices() {
		System.out.println("\n");
		System.out.println("1) Add new student information - 1 \n" + "2) Delete student information - 2 \n"
				+ "3) Update student information - 3 \n" + "4) Display all data information - 4 \n"
				+ "5) Display all passed students - 5 \n" + "6) Display all failed students - 6 \n"
				+ "7) Display highest of subject - 7 \n" + "8) Display lowest of subject - 8 \n"
				+ "9) Display subject with most failed student - 9 \n"
				+ "10) Display subject with most passed student - 10 \n"
				+ "11) Search student information (name/matrix id) - 11 \n" + "12) Exit - 12 \n");
		System.out.println("* Please, type the number of action");
		System.out.println("\n");
	}

	public void switchAction() throws IOException {
		int action;
		Scanner sc = new Scanner(System.in);
		action = sc.nextInt();
		switch (action) {

		case 1:
			if (addNewStudenInfo())
				System.out.println("Information has been added");
			break;

		case 2:
			System.out.println("Enter student matrix mumber");
			Scanner scDelete = new Scanner(System.in);
			long number = scDelete.nextLong();
			if (deleteStudentInfo(number))
				System.out.println("Information has been deleted");
			else
				System.out.println("No student with the matrix number " + number);
			break;

		case 3:
			System.out.println("Enter student matrix mumber");
			Scanner scUpdate = new Scanner(System.in);
			long numberUp = scUpdate.nextLong();
			System.out.println("Input new information");
			if (updateStudentInfo(numberUp))
				System.out.println("Information has been updated");
			else
				System.err.println("Student with matrix number " + numberUp + " was not found");
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
		default:
			System.exit(0);
		}

		formMenuChoices();
	}

	private void lookupStudent() throws IOException {
		List<StudentInfo> list = readObjectsFromFile();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter student matrix number :");
		long matrixNumber = sc.nextLong();

		for (StudentInfo app : list) {
			if (app.getMartixNumber() == matrixNumber) {
				System.out.println(app);
				break;
			}
		}
	}

	private boolean deleteStudentInfo(long number) throws IOException {
		for (int i = 0; i < studentsList.size(); i++) {
			if (studentsList.get(i).getMartixNumber() == number) {
				studentsList.remove(i);
				writeObjectsToFile(studentsList, false);
				return true;
			}
		}
		return false;
	}

	private boolean updateStudentInfo(long number) throws IOException {
		for (int i = 0; i < studentsList.size(); i++) {
			if (studentsList.get(i).getMartixNumber() == number) {
				StudentInfo student = readInfoFromConsole(true);
				if (student != null) {
					student.getName();
					studentsList.get(i).setName(student.getName());
					studentsList.get(i).setICNumber(student.getICNumber());
					studentsList.get(i).setMartixNumber(student.getMartixNumber());
					studentsList.get(i).setGroup(student.getGroup());
					studentsList.get(i).setMarks(student.getMarks());
					studentsList.get(i).setGrades(student.getGrades());
					studentsList.get(i).setNumberOfSubjects(student.getNumberOfSubjects());
					studentsList.get(i).setSubjectCode(student.getSubjectCode());
					writeObjectsToFile(studentsList, true);
					return true;
				}
				return false;
			}
		}

		return false;
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

	private StudentInfo readInfoFromConsole(boolean isUpdate) {
		String name;
		long icNumber;
		long martixNumber;
		int group;
		int numberOfSubjects;
		int[] subjectCode;
		int[] marks;
		int[] grades;

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter name: ");
		name = sc.nextLine();

		System.out.print("Enter IC number: ");
		icNumber = sc.nextLong();
		if (isUpdate == false) {
			for (int i = 0; i < studentsList.size(); i++) {
				if (studentsList.get(i).getICNumber() == icNumber) {
					System.err.println(
							"Student with IC Number " + icNumber + " already exists. IC Number should be unique");
					return null;
				}
			}
		}

		System.out.print("Enter martix number: ");
		martixNumber = sc.nextInt();
		if (isUpdate == false) {
			for (int i = 0; i < studentsList.size(); i++) {
				if (studentsList.get(i).getMartixNumber() == martixNumber) {
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
		if (isExist(fileName)) {
			StudentInfo student = readInfoFromConsole(false);
			System.out.print("Before " + studentsList.toString());
			if (student != null) {
				studentsList.add(student);
				System.out.print("After " + studentsList.toString());
				writeObjectsToFile(studentsList, false);
				return true;
			}
			return false;
		} else {
			System.err.println("File does not exists");
			return false;
		}

	}

	private void enterPathToFile() {
		System.out.print("Enter path to file: ");
		Scanner sc = new Scanner(System.in);
		fileName = sc.nextLine();
		sc.close();
	}

	public static boolean isExist(String filePath) {
		File file = new File(filePath);
		if (file.exists())
			return true;
		else {
			System.out.println("There is no file with absolute path " + filePath);
			return false;
		}
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
			ex.printStackTrace();
		} finally {
			if (oos != null) {
				oos.close();
			}
		}

	}

	public List<StudentInfo> readObjectsFromFile() throws IOException {

		ObjectInputStream objectinputstream = null;
		FileInputStream streamIn = null;
		List<StudentInfo> newStudents = new LinkedList<StudentInfo>();
		try {
			streamIn = new FileInputStream(fileName);

			while (streamIn.available() != 0) {
				objectinputstream = new ObjectInputStream(streamIn);
				StudentInfo a = (StudentInfo) objectinputstream.readObject();
				newStudents.add(a);
			}
			return newStudents;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (objectinputstream != null) {
				objectinputstream.close();
			}
		}

	}

}
