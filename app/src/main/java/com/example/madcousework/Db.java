package com.example.madcousework;

import java.util.Random;

public class Db {
    private static final int NO_OF_COUNTRIES_MINUS_ONE = 28;
    private static int lastRandomIndex;


    private static Integer[] brands = {R.drawable.acura_0, R.drawable.audi_0, R.drawable.bentley_0, R.drawable.bmw_0,R.drawable.buick_0,
            R.drawable.cadillac_0,R.drawable.chevrolet_0,R.drawable.citroen_0,R.drawable.dodge_0,R.drawable.ferrari_0,R.drawable.fiat_0,R.drawable.ford_0,R.drawable.geeky_0,
    R.drawable.genesis_0,R.drawable.gmc_0,R.drawable.honda_0,R.drawable.jeep_0,R.drawable.kia_0,R.drawable.lamborghini_0,R.drawable.lotus_0,R.drawable.mazda_0,
    R.drawable.nissan_0,R.drawable.pontiac_0,R.drawable.renault_0,R.drawable.subaru_0,R.drawable.suzuki_0,R.drawable.tesla_0,R.drawable.toyota_0,R.drawable.volvo_0};


    private String[] answers = {"acura", "audi", "bentley", "bmw","buick", "cadillac", "chevrolet",
            "citroen", "dodge", "ferrari", "fiat", "ford", "geeky", "genesis", "gmc", "honda", "jeep", "kia", "lamborghini", "lotus", "mazda", "nissan",
            "pontiac", "renault", "subaru", "suzuki", "tesla", "toyota", "volvo"};


    public Db(){

    }


    // takes index to return associated car name
    public String getCarName(int indexInArray){
        //Preventing array index out of bounds error
        if ((indexInArray <= NO_OF_COUNTRIES_MINUS_ONE) && (indexInArray >= 0)){
            return answers[indexInArray];
        }
        else {
            System.out.println("Index out of bounds <-- Database.class");
            return "Index out of bounds <-- Database.class";
        }
    }

    // takes car name to return index
    public int getIndex(String car){
        for(int i = 0; i < NO_OF_COUNTRIES_MINUS_ONE; i++){
            if(car.equals(answers[i])) return i;
        }
        System.out.println("Car not found, return -1 <-- Database.class");
        return -1;
    }

    // returns random brand
    public Integer getRandomBrand(){

        Random rand = new Random();
        int randomNumber = rand.nextInt((NO_OF_COUNTRIES_MINUS_ONE) + 1);

        lastRandomIndex = randomNumber;
        return brands[randomNumber];
    }

    public String[] getAnswersArray(){
        return answers;
    }

    public static int getLastRandomIndex(){
        return lastRandomIndex;
    }



}