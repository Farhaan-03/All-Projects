package com.dependencyinjection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

@SpringBootApplication
public class DependencyinjectionApplication {

	public static void main(String[] args) {


//		SpringApplication.run(DependencyinjectionApplication.class, args);

//		System.out.println("using ClassPathXmlApplicationContext");
//		ApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
//		Student student= (Student) context.getBean("student");
//		System.out.println(student.getId());
//		System.out.println(student.getName());
//		System.out.println(student);
//
//		Student student2= (Student) context.getBean("student2");
//		System.out.println(student2);
//
//		System.out.println("using FileSystemXmlApplicationContext");
////		String path="C:/Users/SyedFarhaan/Desktop/beanConfig.txt";
////		ApplicationContext context1=new FileSystemXmlApplicationContext(path);
//		System.out.println("Constructor injection ");
//		Address address=context.getBean(Address.class);
//		System.out.println(address);
//

		System.out.println("AnnotationConfigApplicationContext");
		ApplicationContext annotationContext=new AnnotationConfigApplicationContext(AddressConfig.class);
		Address address1= (Address) annotationContext.getBean("add2");
		System.out.println(address1);







	}

}
