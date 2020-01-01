package Utitlities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataFile {

    public ArrayList singleIteration(FileInputStream file, String sheetname, String columnname) throws IOException
    {
        ArrayList<String> a = new ArrayList<String>();
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheetcount = workbook.getNumberOfSheets();

        for(int i=0; i<sheetcount; i++)
        {
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetname))
            {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next();
                Iterator<Cell> cell = firstrow.cellIterator();
                int k = 0;
                int column = 0;
                while(cell.hasNext())
                {
                    Cell value = cell.next();
                    if(value.getStringCellValue().equalsIgnoreCase(columnname))
                    {
                        column = k;
                    }
                    k++;
                }

                while(rows.hasNext())
                {
                    Row r = rows.next();
                    Iterator<Cell> c = r.cellIterator();
                    while(c.hasNext())
                    {
                        Cell cv = c.next();
                        if(cv.getColumnIndex()== column)
                        {
                            if(cv.getCellType() == CellType.STRING)
                            {
                                a.add(cv.getStringCellValue());
                            }
                            else
                            {
                                a.add(NumberToTextConverter.toText(cv.getNumericCellValue()));
                            }
                        }
                    }
                }
            }
        }

        return a;

    }

    public ArrayList multiIteration(FileInputStream file, String sheetname, String tcname, String columnname) throws IOException
    {
        ArrayList<String> a = new ArrayList<String>();
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheetcount = workbook.getNumberOfSheets();

        for(int i=0; i<sheetcount; i++)
        {
            if(workbook.getSheetName(i).equalsIgnoreCase(sheetname))
            {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next();
                Iterator<Cell> cell = firstrow.cellIterator();
                int k = 0;
                int column = 0;
                int paracolumn = 0;
                while(cell.hasNext())
                {
                    Cell value = cell.next();
                    if(value.getStringCellValue().equalsIgnoreCase("TC_Name"))
                    {
                        column = k;
                    }
                    else if(value.getStringCellValue().equalsIgnoreCase(columnname))
                    {
                        paracolumn = k;
                    }
                    k++;
                }

                while (rows.hasNext())
                {
                    Row r = rows.next();
                    if (r.getCell(column).getStringCellValue().equalsIgnoreCase(tcname))
                    {
                        Iterator<Cell> c = r.cellIterator();
                        if (c.hasNext())
                        {
                            Cell cv = c.next();
                            if(cv.getColumnIndex() == paracolumn)
                            {
                                if(cv.getCellType() == CellType.STRING)
                                {
                                    a.add(cv.getStringCellValue());
                                }
                                else
                                {
                                    a.add(NumberToTextConverter.toText(cv.getNumericCellValue()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return a;
    }

    public FileInputStream setDataFilePath(String filepath) throws IOException
    {
        FileInputStream file = new FileInputStream(filepath);
        return file;
    }

    public ArrayList dataDriver(FileInputStream file, String sheetname, int rowindex, int columnindex) throws IOException
    {
        ArrayList<String> a =  new ArrayList<String>();
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheetcount = workbook.getNumberOfSheets();
        for (int i =0; i<sheetcount; i++)
        {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetname));
            {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next();
                Iterator<Cell> cell = firstrow.cellIterator();
                int k = 0;
                int column = 0;
                int paracolumn = 0;
                while(cell.hasNext())
                {
                    Cell value = cell.next();
                    if(value.getColumnIndex()== columnindex)
                    {
                        column = k;
                    }
                    k++;
                }
                int rowcount = rowCount(file, sheetname);
                int count = 1;
                while(count<rowcount)
                {
                    if(count == rowindex)
                    {
                        Row r = rows.next();
                        Iterator<Cell> c = r.cellIterator();
                        while(c.hasNext())
                        {
                            Cell cv = c.next();
                            if(cv.getColumnIndex()== column)
                            {
                                if(cv.getCellType() == CellType.STRING)
                                {
                                    a.add(cv.getStringCellValue());
                                }
                                else
                                {
                                    a.add(NumberToTextConverter.toText(cv.getNumericCellValue()));
                                }
                            }
                        }
                    }
                    count++;
                }
            }
        }
        return a;
    }

    public int rowCount(FileInputStream file, String sheetname) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheetcount = workbook.getNumberOfSheets();
        int rowcount = 0;
        for (int i =0; i<sheetcount; i++)
        {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetname)) ;
            {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                int k = 0;
                while (rows.hasNext()) {
                    k++;
                }
                rowcount = k;
            }
        }
        return rowcount;
    }

    public int columnCount(FileInputStream file, String sheetname) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheetcount = workbook.getNumberOfSheets();
        int columncount = 0;
        for (int i =0; i<sheetcount; i++)
        {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetname)) ;
            {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next();
                Iterator<Cell> cell = firstrow.cellIterator();
                int k = 0;
                while(cell.hasNext())
                {
                    k++;
                }
                columncount = k;
            }
        }
        return columncount;
    }
}
