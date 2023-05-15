package com.ehealth.gov.repositories;


import com.ehealth.gov.models.PatientModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PatientRepository extends CrudRepository<PatientModel,Integer>
{

    @Query(value = "select * from patients",nativeQuery = true)
    public List<PatientModel>getPatientList();

    @Query(value = "select * from patients where patient_phone=?",nativeQuery = true)
    public  List<PatientModel>searchPatientByPhone(String phone);

    @Query(value = "select * from patients where patient_phone= :num and patient_name= :name",nativeQuery = true)
    public List<PatientModel>searchPatientByNameAndPhone(@Param("num")String number,@Param("name")String name);

    @Transactional
    @Modifying
    @Query(value = "delete from patients where id = :patientId",nativeQuery = true)
    public  int deleteAPatient(@Param("patientId")int patientId);

    @Transactional
    @Modifying
    @Query(value = "update patients set patient_address = :patientAddress,patient_name= :patientName,patient_phone= :patientPhone where id= :patientId",nativeQuery = true)
    public int updatePatientDetails(@Param("patientId")int patientId,@Param("patientAddress")String patientAddress,@Param("patientName")String patientName,@Param("patientPhone")String patientPhone);
}

