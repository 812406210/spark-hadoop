package com.yangwj.spark.study.scala.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yangwj
 * @version 1.0
 * @date 2020/8/8 15:26
 * @desc Scala中，排序使用的是Ordered
 */
public class Student  implements Comparable<Student>{
    int score;
    String name;

    public Student(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Student o) {
        return -(this.getScore() -o.getScore());
    }

    public static void main(String[] args) {
        Student yang = new Student(12, "yang");
        Student wen = new Student(50, "wen");
        Student jie = new Student(20, "jie");
        List<Student> list = new ArrayList<>();
        list.add(yang);
        list.add(wen);
        list.add(jie);
        //方式一:new Comparator，默认为升序
//        Collections.sort(list, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                return -(o1.score -o2.score);
//            }
//        });

        //方式二:实现Comparable,默认为升序
        Collections.sort(list);
        list.forEach(student -> System.out.println(student.getName() +"----" +student.getScore()));
    }
}
