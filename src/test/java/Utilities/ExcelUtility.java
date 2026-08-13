package Utilities;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	 public static Object[][] getexceldata(String filepath, String sheetname) throws InvalidFormatException, IOException {
		  
		  String[][] data=new String[3][2];
		  File f1=new File(filepath);
		  
		  XSSFWorkbook workbook=new XSSFWorkbook(f1);
		  XSSFSheet sheet=workbook.getSheetAt(0);
		  int rowcount=sheet.getPhysicalNumberOfRows();
		  System.out.println("no of rows:"+rowcount);
		  
		  for (int i=0;i<rowcount;i++)
		  {
			  data[i][0]=sheet.getRow(i).getCell(0).getStringCellValue();
			  data[i][1]=sheet.getRow(i).getCell(1).getStringCellValue();
					  
		  }
		  
	    return data;
	   
	    
	  }

}