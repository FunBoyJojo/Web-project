package com.testplus.testplus;

import com.testplus.testplus.models.Answer;
import com.testplus.testplus.models.Query;
import com.testplus.testplus.models.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class Utils {


    public static String totalScore(Test realTest, List<List<Boolean>> queries) {

        double countOfTrueA=0;
        double allTrue = 0;
        double result = 0.0;

        List<Query> querys = realTest.getQuerys();

        List<List<Answer>> a = new ArrayList<>();

        for (Query q : querys) {
            a.add(q.getAnswers());
        }

        for (int i = 0; i < querys.size(); i++) {
            for (int j = 0; j < a.get(i).size(); j++) {
                if (a.get(i).get(j).getTrue() == true){
                    allTrue++;
                }
                if (a.get(i).get(j).getTrue() == true && queries.get(i).get(j) == true)
                {
                    countOfTrueA++;
                }
            }
            result += countOfTrueA / allTrue;
            countOfTrueA = 0;
        }

        if (result == 0){
            return "0";
        }else{
            String value = String.format("%.1f",result*100/((double)querys.size()));

            return value.replace(',','.');
        }

        //return result*100/querys.size();
    }

    public static long timeConvertor(String time_h, String time_m)
    {
        long hours = Long.parseLong(time_h)*3600000;
        long minutes = Long.parseLong(time_m)*60000;

        return hours+minutes;
    }

    public static void randomQueries(Test currentTest) {
        LinkedHashSet<Query> set = new LinkedHashSet<>(currentTest.getQuerys());
        List<Query> queries = new ArrayList<>();
        set.iterator().forEachRemaining(it->queries.add(it));

        Random random = new Random();
        int tmp = 0;
        List<Query> newlist = new ArrayList<>();
        int listSize = queries.size();
        for (int i = 0; i < listSize; i++) {
            tmp = random.nextInt(queries.size());
            newlist.add(queries.get(tmp));
            queries.remove(tmp);
        }
        currentTest.setQuerys(newlist);
    }
}
