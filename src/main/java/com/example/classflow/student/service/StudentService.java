package com.example.classflow.student.service;

import com.example.classflow.exception.ResourceNotFoundException; // ResourceNotFoundException 임포트
import com.example.classflow.student.entity.Student;
import com.example.classflow.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id)); // 예외 변경
        student.setName(studentDetails.getName());
        student.setPhone(studentDetails.getPhone());
        student.setMemo(studentDetails.getMemo());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id)); // 예외 변경
        studentRepository.delete(student);
    }
}
