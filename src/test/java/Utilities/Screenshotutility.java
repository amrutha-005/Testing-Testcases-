package Utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshotutility {
	static String projectpath=System.getProperty("user.dir");
	  public static String capturescreenshot(WebDriver driver,String testname) throws IOException
	  {
		  TakesScreenshot ts=(TakesScreenshot)driver;
		  File source=ts.getScreenshotAs(OutputType.FILE);
		  String spath=projectpath+"/Screenshots/"+testname+".png";
		  File destination=new File(spath);
		  FileUtils.copyFile(source, destination);
		  return spath;
		  
		  
	  }
	
}