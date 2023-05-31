package com.ehealth.gov;

public class Patient {

 //private static Patient obj=new Patient();   //Early instance

    private static Patient obj;   //Lazy instance


    private Patient(){}

 public static Patient getPatient()
 {
     //return obj;

     if(obj==null) {
         synchronized (Patient.class) {
             if (obj == null) {
                 obj = new Patient();
             }
         }
     }
     return obj;
 }

 public void doSomething()
 {
     System.out.println("Welcome Patient");
 }


}

class User
{
    public static void main(String[] args)
    {
        Patient.getPatient().doSomething();
    }

}


