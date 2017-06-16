package com.lll.app.db;

import java.util.ArrayList;
import java.util.List;

import com.app.lll.bean.Lesson;

public class LessonDAO {

	public static List<Lesson> getLessonsByYearAndMonth(int year, int month) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		Lesson lesson = new Lesson();
		lesson.setId(1);
		lesson.setName("语文");
		lesson.setNum(1);
		lesson.setStatus(1);
		lesson.setLessonDate("2016-01-30 12:00");
		lesson.setsTime("12:00");
		lesson.seteTime("13:00");
		lessons.add(lesson);
		
		Lesson lesson1 = new Lesson();
		lesson1.setId(2);
		lesson1.setName("数学");
		lesson1.setNum(2);
		lesson1.setStatus(0);
		lesson1.setLessonDate("2016-01-31 15:00");
		lesson1.setsTime("15:00");
		lesson1.seteTime("16:00");
		lessons.add(lesson1);
		
		Lesson lesson2 = new Lesson();
		lesson2.setId(2);
		lesson2.setName("英语");
		lesson2.setNum(3);
		lesson2.setStatus(1);
		lesson2.setLessonDate("2016-01-28 08:00");
		lesson2.setsTime("08:00");
		lesson2.seteTime("09:00");
		lessons.add(lesson2);
		return lessons;
	}

	public static List<Lesson> getLessonsByDate(String string, String nextDay) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		Lesson lesson = new Lesson();
		lesson.setId(1);
		lesson.setName("语文");
		lesson.setNum(1);
		lesson.setStatus(1);
		lesson.setLessonDate("2016-01-30 12:00");
		lesson.setsTime("12:00");
		lesson.seteTime("13:00");
		lessons.add(lesson);
		
		Lesson lesson1 = new Lesson();
		lesson1.setId(2);
		lesson1.setName("数学");
		lesson1.setNum(2);
		lesson1.setStatus(0);
		lesson1.setLessonDate("2016-01-31 15:00");
		lesson1.setsTime("15:00");
		lesson1.seteTime("16:00");
		lessons.add(lesson1);
		
		Lesson lesson2 = new Lesson();
		lesson2.setId(2);
		lesson2.setName("英语");
		lesson2.setNum(3);
		lesson2.setStatus(1);
		lesson2.setLessonDate("2016-01-28 08:00");
		lesson2.setsTime("08:00");
		lesson2.seteTime("09:00");
		lessons.add(lesson2);
		return lessons;
	}
	
}
