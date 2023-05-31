package com.ehealth.gov.controllers;

import com.ehealth.gov.models.PatientModel;
import com.ehealth.gov.repositories.PatientRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class PatientController {


    PatientModel patientGlobalModel=new PatientModel();
    List<PatientModel> patientLists=new ArrayList<>();


    @Autowired
    PatientRepository patientRepository;


    @GetMapping(path = "/")
    public String addPatients()
    {

        return "Patient added";
    }

    @RequestMapping(path = "view" ,method = RequestMethod.POST)
    @ResponseBody
    public String ViewPatients(@RequestBody String pName)
    {
        return "View Patient- "+pName;
    }

    @PostMapping(path="addPatient",consumes = "application/json" , produces = "application/json")
    @ResponseBody
    public HashMap patientEntry(@RequestBody PatientModel patientModel)
    {
        System.out.println("Name"+ patientModel.getPatientName());
        System.out.println("Address"+ patientModel.getPatientAddress());
        System.out.println("Phone"+ patientModel.getPatientPhone());
        patientGlobalModel=patientModel;
        /*List<PatientModel> list=new ArrayList<>();
        list.add(patientModel);
        */
        patientRepository.save(patientModel);
        patientLists.add(patientModel);
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status","success");
        hashMap.put("data",patientModel);
        // return patientLists;
        return hashMap;
    }


    @PostMapping(path="response",consumes = "application/json" , produces = "application/json")
    @ResponseBody
    public HashMap patientResponse(@RequestBody PatientModel patientModel)
    {
        System.out.println("Name"+ patientModel.getPatientName());
        System.out.println("Address"+ patientModel.getPatientAddress());
        System.out.println("Phone"+ patientModel.getPatientPhone());
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status","success");
        hashMap.put("data",patientModel);
        return hashMap;
    }


    //view patients
    @GetMapping(path="viewPatients")
    @ResponseBody
    public HashMap viewPatients()
    {
        //PatientModel patientModel=new PatientModel();
        System.out.println("Name"+ patientGlobalModel.getPatientName());
        System.out.println("Address"+ patientGlobalModel.getPatientAddress());
        System.out.println("Phone"+ patientGlobalModel.getPatientPhone());
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("status","success");
        hashMap.put("data",patientRepository.findAll());
        return hashMap;
    }

    @PostMapping(path = "/searchPhone",consumes = "application/json",produces = "application/json")
    public  HashMap searchPhone(@RequestBody PatientModel requestNumber)
    {
        int found = 0;
        System.out.println("the phone number"+requestNumber.getPatientPhone());
        PatientModel patientModel = new PatientModel();
        for (PatientModel model:patientLists)
        {
            System.out.println("model"+model.getPatientPhone());
            System.out.println("requested"+requestNumber.getPatientPhone());
            if (model.getPatientPhone().equals(requestNumber.getPatientPhone()))
            {
                found = 1;
                patientModel = model;
                break;
            }
            else
            {

            }
        }
        HashMap<String,Object>hashMap = new HashMap<>();
        if (found == 1)
        {
            hashMap.put("status","success");
            hashMap.put("Patient",patientModel);
        }
        else
        {
            hashMap.put("status","failed");

        }
        return  hashMap;


    }


    @PostMapping(path = "/deleteUser",consumes = "application/json",produces = "application/json")
    public  HashMap deletePhone(@RequestBody PatientModel requestNumber)
    {
        int found = 0;
        int index = 0;
        System.out.println("the phone number"+requestNumber.getPatientPhone());
        PatientModel patientModel = new PatientModel();
        for (PatientModel model:patientLists)
        {
            System.out.println("model"+model.getPatientPhone());
            System.out.println("requested"+requestNumber.getPatientPhone());
            if (model.getPatientPhone().equals(requestNumber.getPatientPhone()))
            {
                found = 1;
                index = patientLists.indexOf(model);
                patientModel = model;
                break;
            }
            else
            {

            }
        }
        HashMap<String,Object>hashMap = new HashMap<>();
        if (found == 1)
        {
            patientLists.remove(index);
            hashMap.put("status","Deleted Successfully");
        }
        else
        {
            hashMap.put("status","failed");
        }
        return  hashMap;

    }


    //Returning data as list: output look like [{values},{values}]

    @GetMapping(path = "/viewPatientLists")
    public List viewpatientList()
    {
        List<PatientModel>patientlist = (List<PatientModel>) patientRepository.findAll();
        return patientlist;
    }


    //Using Native Queries
    //select Query
    @GetMapping(path = "/viewPatientList")
    public List viewPatientListUsingNativeQuery()
    {
        List<PatientModel>patientlist = patientRepository.getPatientList();
        return patientlist;

    }
    //Select Query using ?
    @PostMapping(path = "/searchPatient",consumes = "application/json",produces = "application/json")
    public List searchPatientListByPhone(@RequestBody PatientModel model)
    {
        List<PatientModel>patientlist = patientRepository.searchPatientByPhone(model.getPatientPhone());
        return patientlist;


    }

    //Search Query using  @Param annotation
    @PostMapping(path =  "/multiSearch",consumes = "application/json",produces = "application/json")
    public  HashMap searchUsingNameAndPhone(@RequestBody PatientModel model)
    {
        List<PatientModel> list = patientRepository.searchPatientByNameAndPhone(model.getPatientPhone(),model.getPatientName());

        HashMap<String, Object> map = new HashMap<>();
        if(list.isEmpty())
        {
            map.put("Status","Not Found");
        }
        else
        {
            map.put("Status","Found");
            map.put("Data",list);
        }
        return  map;
    }



    //search patient with phone no
    @PostMapping(path="search",consumes = "application/json" , produces = "application/json")
    @ResponseBody
    public HashMap searchPatients(@RequestBody PatientModel patientModel)
    {
        HashMap<String,Object> hashMap=new HashMap<>();
        List<PatientModel>patientlist = (List<PatientModel>) patientRepository.findAll();
        for (PatientModel patientModel1:patientlist)
        {
            if(patientModel.getPatientPhone().equals(patientModel1.getPatientPhone()))
            {
                hashMap.put("status","Found!!!");
                hashMap.put("data",patientModel1);
                break;
            }
            else
            {
                hashMap.put("status","Not Found!!!");
            }

        }
        return hashMap;
    }

    //search & delete patient
    @PostMapping(path="delete",consumes = "application/json" , produces = "application/json")
    @ResponseBody
    public HashMap deletePatients(@RequestBody PatientModel patientModel)
    {
        String getPhone=patientModel.getPatientPhone();
        HashMap<String,Object> hashMap=new HashMap<>();
        for (PatientModel patientmodel:patientLists) {
            if(getPhone.equals(patientmodel.getPatientPhone()))
            {
                patientLists.remove(patientmodel);
                hashMap.put("status","Deleted successfully");
            }
            else
            {
                hashMap.put("status","Invalid user");
            }
            break;
        }

        return hashMap;
    }

    @PostMapping(path = "/deletePatient",consumes = "application/json",produces = "application/json")
    public  HashMap deletePatient(@RequestBody PatientModel model)
    {
        int value = patientRepository.deletePatient(model.getId());
        System.out.println(value);
        HashMap<String, String>map = new HashMap<>();

        if (value == 1)
        {
            map.put("Status","Deleted successfully");

        }
        else {
            map.put("Status","Deleting failed");

        }

        return map;

    }

    @PutMapping(path = "/updatePatient",consumes = "application/json",produces = "application/json")
    public  HashMap updatePatient(@RequestBody PatientModel model)
    {
        int value = patientRepository.updatePatientDetails(model.getId(), model.getPatientAddress(), model.getPatientName(),model.getPatientPhone());
        HashMap<String,String> map = new HashMap<>();
        if (value == 1)
        {
            map.put("Status","Updated Successfully");
        }
        else
        {
            map.put("Status","Update failed");

        }
        return  map;
    }


}
