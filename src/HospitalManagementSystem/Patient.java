package HospitalManagementSystem;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner=scanner;
    }

    // ADD PATIENT
    // ADD PATIENT
    public void addPatient(){
        scanner.nextLine();  // Consume the newline character left in the buffer

        System.out.print("Enter Patient Name : ");
        String name = scanner.nextLine();  // Use nextLine() to read the entire line

        System.out.print("Enter Patient Age : ");
        int age;

        // Handle the possibility of non-integer input for age
        while (true) {
            try {
                age = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for age. Please enter a valid integer.");
                System.out.print("Enter Patient Age : ");
            }
        }

        System.out.print("Enter Patient Gender : ");
        String gender = scanner.nextLine();

        try {
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Failed to add Patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // VIEW PATIENT
    public void viewPatient(){
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+-----------------------+---------+------------+");
            System.out.println("| Patient Id | Name                  | Age     | Gender     |");
            System.out.println("+------------+-----------------------+---------+------------+");

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name= resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("| %-10s | %-21s | %-7s | %-10s |\n",id,name,age,gender);
                System.out.println("+------------+-----------------------+---------+------------+");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // GET PATIENT BY ID
    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultset= preparedStatement.executeQuery();

            if(resultset.next()){
                return true;

            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }



}