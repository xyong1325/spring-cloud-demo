package com.neo;

import com.neo.common.POIUtil;
import com.neo.factory.XmlFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SpringPoiXml2ApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Test
    public void genXml() throws Exception{
         String excelPath = "C:/excel/xg.xls";
         String xmlDir = "C:/excel";
        XmlFactory.GenXml(excelPath,xmlDir);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
   class  Person{
       private String id;
       private String name;
       private String sex;
    }
    @Test
    public void unique(){
        /* List<Person>  peoples = new ArrayList<>();
            peoples.add(new Person("1","张三","男"));
            peoples.add(new Person("2","张三","女"));
            peoples.add(new Person("3","李四","男"));
            peoples.add(new Person("4","李四","男"));

        List<Person>  newPersons = peoples.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName()))),ArrayList::new));
        System.out.println(newPersons);

        int i = 0 ;
        int j =  i % 100 ;
          System.out.println(j);
        i = 100 ;
        j =  i % 100 ;
        System.out.println(j);
        i = 101 ;
        j =  i % 100 ;
        System.out.println(j);
        */
    }
    @Test
    public void split(){
         List<Integer> integers = new ArrayList<>();
         for(int i = 0 ; i< 101;i++){
             integers.add(i+1);
         }


         int MAX_SEND  = 10 ;
         int size =  integers.size();

       /* Stream.iterate(0,n->n+1).limit(integers.size()).parallel().map(a->{
            List<Integer> sendList = integers.stream().skip(a*MAX_SEND).limit(MAX_SEND).parallel().collect(Collectors.toList());
            System.out.println(sendList);
        }).collect(Collectors.toList());
*/

     List<List<Integer>> ls =    Stream.iterate(0, f -> f + 1)
                .limit(size)
                .parallel()
                .map(a -> integers.parallelStream().skip(a * MAX_SEND).limit(MAX_SEND).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());

       System.out.println(ls);
    }
}
