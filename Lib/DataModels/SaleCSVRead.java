package DataModels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SaleCSVRead {
    File f=new File("./FileCSV/daily_report.csv");
    FileReader fr=null;
    BufferedReader br=null;
    List <String[]> record;
    public List<String[]> readSaleFromCSV(){
    record=new ArrayList<>();
    boolean isHeader =true;
       try {
        fr=new FileReader(f);
        br=new BufferedReader(fr);
        String s;
        while((s=br.readLine())!=null){
        if (isHeader) {
                    isHeader = false;
                    continue;
                }
        
        String[] data=s.split(",");
        // String orderid = data[0].trim();
        // double price = Double.parseDouble(data[1].trim());
        // LocalDate date = LocalDate.parse(data[2].trim());
                record.add(data);
            }
        }catch (Exception e) {
        System.out.println(e);
       } finally{
        try {
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
       return record;
    }
    public static void main(String[] args) {
        SaleCSVRead a=new SaleCSVRead();
        List <String[]> b=a.readSaleFromCSV();
        for(String[] s: b)
        System.out.println(Arrays.toString(s));
    }
}