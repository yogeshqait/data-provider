package seleniumtestng;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

public class TestNGSimpleTest {
   @BeforeTest
   public void setDriverPath() {
      System.setProperty("webdriver.chrome.driver", "/home/yogesh/ooo/chromedriver");
   }

   @Test(dataProvider = "data-provider")
   public void checkList(List<String> data) {
      WebDriver driver = new ChromeDriver();
      driver.get("http://10.0.14.57:9292/");
      List<WebElement> menu = driver.findElements(By.cssSelector("#content > ul >li"));
      List<String> menuItems = new ArrayList<String>();
      System.out.println(menu.size());
      for (WebElement text : menu) {
    	  menuItems.add(text.getText());
      }
      
      for(int i=0;i<data.size();i++) {
    	  Assert.assertEquals(data.get(i).equals(menuItems.get(i)), true);
      }
   }

   @DataProvider(name = "data-provider")
   public ArrayList<String> readfile() throws IOException{
	   ArrayList<String> data = new ArrayList<String>();
	   File file = new File("/home/yogesh/ooo/stest/src/test/resources/testdata.xlsx");
	   FileInputStream inputStream = new FileInputStream(file);
	   XSSFWorkbook Workbook = new XSSFWorkbook(inputStream);
	   Sheet Sheet = Workbook.getSheet("testdata");
	       int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();
	       for (int i = 0; i < rowCount + 1; i++) {
	          Row row = Sheet.getRow(i);
	          for (int j = 0; j < row.getLastCellNum(); j++) {
	              //System.out.println(row.getCell(j).getStringCellValue());
	              data.add((row.getCell(j).getStringCellValue()));
	              }
	       }
	       Workbook.close();
	     for (int j = 0; j < data.size(); j++) {
	    	//System.out.println(data.get(j));  
	      }
	       return data;
	    }
}