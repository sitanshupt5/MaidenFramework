package Utitlities;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelUtils {

    String filepath;
    File src;
    FileInputStream file;

    public void setFilePath(String path) throws IOException
    {
        try{
            filepath = path;
            src = new File(filepath);
            file = new FileInputStream(src);
        }
        catch(Exception e)
        {
            e.getLocalizedMessage();
        }
    }

    public void closeFile() throws IOException
    {
        file.close();
    }



    public String singleIteration(String sheetname, String parameter) throws IOException
    {
        String data = null;
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetname);
        Row firstrow = sheet.getRow(0);
        int column = 0;
        for(int i = 0; i<firstrow.getLastCellNum(); i++)
        {
            if(firstrow.getCell(i).getStringCellValue().equalsIgnoreCase(parameter))
            {
                column = i;
            }
        }
        data = sheet.getRow(1).getCell(column).getStringCellValue();
        closeFile();
        setFilePath(filepath);
        return data;

    }

    public String multiTest(String sheetname, String testcase, String parameter) throws IOException
    {
        String data = null;
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetname);
        Row firstrow = sheet.getRow(0);
        int tcnumber = 0;
        int column = 0;
        for (int i = 0; i<firstrow.getLastCellNum(); i++)
        {
            if(firstrow.getCell(i).getStringCellValue().equalsIgnoreCase(parameter))
            {
                column = i;
            }
            else if(firstrow.getCell(i).getStringCellValue().equalsIgnoreCase("TC_Name"))
            {
                tcnumber = i;
            }
        }
        for(int i = 1; i<sheet.getLastRowNum(); i++)
        {
            Row r = sheet.getRow(i);
            if(r.getCell(0).getStringCellValue().equalsIgnoreCase(testcase))
            {
                data = r.getCell(column).getStringCellValue();
            }
        }
        closeFile();
        setFilePath(filepath);
        return data;
    }

    public int rowCount(String sheetname) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetname);
        closeFile();
        setFilePath(filepath);
        return sheet.getLastRowNum();

    }

    public int columnCount(String sheetname) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetname);
        closeFile();
        setFilePath(filepath);
        return sheet.getRow(0).getLastCellNum();
    }

    public String[][] excelDataDriver(String sheetname) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet(sheetname);
        int r = sheet.getLastRowNum();
        int c = sheet.getRow(0).getLastCellNum();
        String[][] data = new String[r][c];
        for (int i =1; i<=r; i++)
        {
            for(int j=0; j<c; j++)
            {
                if(sheet.getRow(i).getCell(j).getCellType() == CellType.STRING)
                {
                    data[i-1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
                else
                {
                    data[i-1][j] = NumberToTextConverter.toText(sheet.getRow(i).getCell(j).getNumericCellValue());
                }
            }
        }
        closeFile();
        return data;
    }

}
