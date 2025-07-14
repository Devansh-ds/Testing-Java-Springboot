package com.devansh.assertions;

import com.devansh.Student;
import com.devansh.StudentService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    public void getStudentsTest() {
        StudentService studentService = new StudentService();

//        Student s1 = new Student(1, "John");
//        studentService.addStudent(s1);

        List<Student> studentList = studentService.getStudents();
        boolean actualResult = studentList.isEmpty();

        assertTrue(actualResult, "List is empty");

    }

    @Test
    public void nullAndNotNullTest() {
        StudentService studentService = new StudentService();
        assertNotNull(studentService.getStudents());
    }

    @Test
    public void emptyListTest() {
        StudentService studentService = new StudentService();
        Student s1 = new Student(1, "John");
        studentService.addStudent(s1);

        assertEquals(1, studentService.getStudents().size(), () -> "List is empty");
    }

    @Test
    public void listTest() {
        StudentService studentService = new StudentService();

        Student s1 = new Student(1, "John");
        studentService.addStudent(s1);
        List<Student> studentList = studentService.getStudents();

        assertArrayEquals(new Student[]{s1}, studentList.toArray());
    }

    @Test
    public void iterableTest() {
        Set<Integer> set1 = new HashSet<>(); set1.add(3); set1.add(1); set1.add(2);
        List<Integer> list1 = Arrays.asList(1,2,3);

        assertIterableEquals(set1, list1);
    }

    @Test
    public void exceptionTest() {
        StudentService studentService = new StudentService();
        Student s1 = new Student(1, "John");
        studentService.addStudent(s1);

        assertThrows(Exception.class, () -> {
            studentService.getStudentById(100);
        }, "Expected StudentNotFoundException to be thrown");
    }

}












